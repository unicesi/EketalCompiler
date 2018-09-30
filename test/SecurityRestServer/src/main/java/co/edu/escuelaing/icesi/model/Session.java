package co.edu.escuelaing.icesi.model;

public class Session implements Comparable<Session>{
	
	int id;
	String description;
	
	public Session(int idSession, String resume) {
		id = idSession;
		description = resume;
	}

	@Override
	public int compareTo(Session o) {
		return id-o.id;
	}
	
	@Override
	public String toString() {
		return id+" "+description;
	}
}
