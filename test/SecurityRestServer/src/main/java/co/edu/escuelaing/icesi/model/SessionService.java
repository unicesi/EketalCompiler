package co.edu.escuelaing.icesi.model;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class SessionService implements ISessionService {
	
	private Set<Session> sessions;
	
	public SessionService() {
		sessions = new TreeSet<Session>(new Comparator<Session>() {
			@Override
			public int compare(Session o1, Session o2) {
				return o1.id-o2.id;
			}
		});
		initializeSessions();
	}
	
	private void initializeSessions() {
		sessions.add(new Session(2, "Nueva session"));
		sessions.add(new Session(3, "Nueva session 2"));
		sessions.add(new Session(1, "Nueva session 3"));
		sessions.add(new Session(4, "Nueva session 4"));
		
	}

	@Override
	public Set<Session> getAllSessions() {
		return sessions;
	}

	@Override
	public Session getSession(int id) {
		if(contains(id)){
			return findSession(id);
		}
		return null;
	}

	private Session findSession(int id) {
		for (Session session : sessions) {
			if(session.id==id){
				return session;
			}
		}
		return null;
	}

	@Override
	public Session destroySession(int id) {
		if(contains(id)){
			Session temp = findSession(id);
			sessions.remove(temp);
			return temp;
		}
		return null;
	}

	@Override
	public Session createSession(int id, String write) {
		if(!contains(id)){
			Session nueva = new Session(id, write);
			sessions.add(nueva);
			return nueva;
		}
		return null;
	}

	@Override
	public Session writeOnSession(int id, String write) {
		if(contains(id)){
			Session temp = findSession(id);
			temp.description = temp.description.concat("\n"+write);
			return temp;
		}
		return null;
	}

	private boolean contains(int id) {
		return sessions.contains(new Session(id, ""));
	}
	
}
