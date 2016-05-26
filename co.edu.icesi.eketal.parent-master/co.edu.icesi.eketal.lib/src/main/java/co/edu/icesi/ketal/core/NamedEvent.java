package co.edu.icesi.ketal.core;

import java.net.URL;

import org.jgroups.protocols.TransportedVectorTime;

public class NamedEvent implements Event{
	
	private String name;

	private TransportedVectorTime tvt;
	/*
	 * Constructor.
	 */
	public NamedEvent(String event)
	{
		name=event;
		tvt = null;
	}
	
	@Override
	public boolean equals(Event e) {
		
		if(e instanceof NamedEvent)
		{
			return (((NamedEvent)e).name).equals(this.name);
		}
		return false;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public URL getLocalization() {
		//No need this method
		return null;
	}

	@Override
	public boolean setLocalization(URL url) {
		//No need this method
		return false;
	}

	@Override
	public URL getTargetLocalization() {
		//No need this method
		return null;
	}

	@Override
	public boolean setTargetLocalization(URL url) {
		//No need this method
		return false;
	}

	@Override
	public TransportedVectorTime getTransportedVectorTime() {
		if(tvt != null){
		if(tvt.size()>0){
		return tvt;}}
		return null;
	}

	@Override
	public boolean setTransportedVectorTime(TransportedVectorTime tvt) {
		this.tvt = tvt;
		return true;
	}
	
	public String toString()
	{
		return name;
	}
	
}
