package co.edu.escuelaing.icesi;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;


public class SecurityClient {
	
	private String ip;
	private int id;
	
	public SecurityClient(String pIp){
		id = 2;
		ip = pIp;
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void createSession(){
		URL u = null;
		
		// estos serian los eventos siendo las peticiones de los clientes al
		// servidor
		try {
			String header = "http://"+ip+":4567/session/"+id;
			u = new URL(header);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(u.openStream()));
			String inputLine = null;
			while ((inputLine = reader.readLine()) != null) {
				System.out.println("Session started "+inputLine);
			}
			reader.close();
		} catch(UnknownHostException ex){
			System.out.println("No existe el usuario");
			return;
			//ex.printStackTrace();
		}catch (MalformedURLException ex) {
			System.out.println("pailaExp");
			ex.printStackTrace();
		} catch (IOException x) {
			x.printStackTrace();
		}
	}
	
	public void printSessions(){
		URL u = null;
		
		// estos serian los eventos siendo las peticiones de los clientes al
		// servidor
		try {
			String header = "http://"+ip+":4567/sessions";
			u = new URL(header);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(u.openStream()));
			String inputLine = null;
			while ((inputLine = reader.readLine()) != null) {
				System.out.println("Sessions "+inputLine);
			}
			reader.close();
		} catch(UnknownHostException ex){
			System.out.println("No existe el host");
			return;
			//ex.printStackTrace();
		}catch (MalformedURLException ex) {
			System.out.println("pailaExp");
			ex.printStackTrace();
		} catch (IOException x) {
			x.printStackTrace();
		}
		
	}
	
	public void write(String append){
		
		try {
			String urlParameters  = "id="+id+"&description="+append;
			byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
			int    postDataLength = postData.length;
			String request        = "http://"+ip+":4567/sessions/"+id;
			URL    url            = new URL( request );
			HttpURLConnection conn= (HttpURLConnection) url.openConnection();           
			conn.setDoOutput( true );
			conn.setInstanceFollowRedirects( false );
			conn.setRequestMethod("PUT");
			conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded"); 
			conn.setRequestProperty( "charset", "utf-8");
			conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
			conn.setUseCaches( false );
			
			DataOutputStream wr = new DataOutputStream( conn.getOutputStream());
			wr.write( postData );
			
			int temp = conn.getResponseCode();
			if(temp!=HttpURLConnection.HTTP_OK){
				System.out.println("did not worked");
				conn.disconnect();
				return;
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
			
			
			String output;
			System.out.println("Output from Server ....");
			while ((output = br.readLine()) != null) {
				System.out.println("Changes saved on "+output+"\n");
			}

			conn.disconnect();
						
		}catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public void destroySession(){
		try {
			String header = "http://"+ip+":4567/session/+"+id;
			URL url = new URL(header);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("DELETE");
			
			if (conn.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND) {
				System.out.println("Ya no existe el cliente existe");
				return;
			}else if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
			
			
			String output;
			System.out.println("Output from Server ....");
			System.out.println("Destroyed session");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			conn.disconnect();
			
		} catch (MalformedURLException  e) {
			e.printStackTrace();
		}catch (IOException  e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		String ip="localhost";
		boolean destroy = false;
		
		if(args.length>1){
			for (int i = 0; i < args.length; i++) {
				String string = args[i];
				switch (string) {
				case "-ip":
					if(i+1<args.length){
						++i;
						ip=args[i];
						try {
							new URL("http://"+ip);
						} catch (MalformedURLException e) {
							printUrlError(ip);
							printUsage();
							return;
						}
					}else{
						printUsage();
						return;
					}
					break;
				case "-destroy":
					destroy = true;
					break;
				default:
					printUsage();
					return;
				}
			}
		}	
		
		SecurityClient client = new SecurityClient(ip);		
		client.printSessions();
		
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(destroy){
			client.createSession();
		}
		
		for (int i = 0; i < 5; i++) {
			client.write("try: "+i);
			try {
				Thread.sleep(2000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(destroy){
			client.destroySession();	
		}else{
			client.printSessions();
		}
		
		
		try {
			Thread.sleep(4000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.exit(0);
	}
	
	private static void printUrlError(String ip) {
		System.out.println("\nError: invalid ip: ");
		
	}

	private static void printUsage() {
		System.out.println("\n*******  USAGE *******");
		System.out.println("co.edu.escuelaing.icesi.SecurityClient <options>");
		System.out.println("\toptions (may be written in any order):");
		System.out
				.println("\t\t \"-ip <ip_server>\" (Captures the ip where the rest server is located)");
		System.out
				.println("\t\t \"-destroy\" (Ends interaction)");
		return;
	}
	
}
