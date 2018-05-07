package co.edu.escuelaing.icesi;

import static spark.Spark.*;

import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

import co.edu.escuelaing.icesi.model.ISessionService;
import co.edu.escuelaing.icesi.model.Session;
import co.edu.escuelaing.icesi.model.SessionService;
import co.edu.escuelaing.icesi.util.JsonUtil;
import co.edu.escuelaing.icesi.util.ResponseError;

public class SecurityServer {

	public static void main(String[] args) {

		ISessionService service = new SessionService();

		/*
		 * testing with curl curl http://localhost:4567/users
		 */
		get("/sessions", (req, res) -> service.getAllSessions(), JsonUtil.json());

		/*
		 * testing with curl curl http://localhost:4567/hello
		 */
		get("/hello", (req, res) -> "Hello World");

		/*
		 * testing with curl curl http://localhost:4567/user/:id
		 */
		get("/session/:id", (req, res) -> {
			String id = req.params(":id");
			Session session = service.getSession(Integer.parseInt(id));
			if (session != null) {
				return session;
			}
			res.status(400);
			return new ResponseError("No session with id '%s' found", id);
		}, JsonUtil.json());

		/*
		 * Con el estandar application/x-www-form-urlencoded curl -i -X POST
		 * http://localhost:4567/user/add -d 'name=Carlos&age=21&city=Florida'
		 * 
		 * powershell Invoke-WebRequest -Uri http://localhost:4567/sessions/add
		 * -Method Post -Body $formFields -ContentType
		 * "application/x-www-form-urlencoded"
		 */
		post("/sessions/add", (req, res) -> {
			System.out.println(req.body());
			Map<String, String> params = asParams(req.body());
			System.out.println(params.size());
			if (params.get("id") != null) {
				try {
					Integer.parseInt(params.get("id"));
				} catch (NumberFormatException e) {
					System.out.println("The id cannot be converted into an id");
					return new ResponseError("Id must be a number");
				}
			}
			Session user = service.createSession(Integer.parseInt(params.get("id")), params.get("description"));
			if (user == null) {
				return new ResponseError("The session with id '%s' alredy exists", req.queryParams("name"));
			}
			res.status(201);
			return user;
		}, JsonUtil.json());
		
		/*
		 * curl -i -X PUT http://localhost:4567/sessions/2 -d 'id=2&description=concatenar'
		 */
		put("/sessions/:id", (req, res) -> {
			Map<String, String> params = asParams(req.body());
			return service.writeOnSession(Integer.parseInt(req.params(":id")), params.get("description"));
		}, JsonUtil.json());

		/*
		 * testing with curl curl -X DELETE http://localhost:4567/user/Jhon
		 * Invoke-WebRequest -Uri http://localhost:4567/sessions/1 -Method DELETE
		 */
		delete("/session/:id", (request, response) -> {
			String id = request.params(":id");
			Session user = service.destroySession(Integer.parseInt(id));

			if (user != null) {
				return user;
			} else {
				response.status(404);
				return new ResponseError("The session with id '%s' does not exist", id);
			}
		});

		exception(IllegalArgumentException.class, (e, req, res) -> {
			res.status(400);
			res.body(JsonUtil.toJson(new ResponseError(e)));
		});

		after((req, res) -> {
			res.type("application/json");
		});
	}

	private static Map<String, String> asParams(String body) {
		TreeMap<String, String> tree = new TreeMap<>();
		String[] params = body.split(Pattern.quote("&"));
		for (String string : params) {
			String[] values = string.split(Pattern.quote("="));
			tree.put(values[0], values[1]);
		}
		return tree;
	}

}
