package co.edu.escuelaing.icesi.model;

import java.util.Set;

public interface ISessionService {
	
	public Set<Session> getAllSessions();
	
	public Session getSession(int id);
	
	public Session destroySession(int id);
	
	public Session createSession(int id, String write);
	
	public Session writeOnSession(int id, String write);
	
}
