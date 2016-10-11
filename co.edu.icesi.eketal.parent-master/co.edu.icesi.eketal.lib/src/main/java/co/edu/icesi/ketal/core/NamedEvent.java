package co.edu.icesi.ketal.core;

import java.io.Serializable;
import java.net.URL;
import java.util.List;

import org.jgroups.Address;
import org.jgroups.protocols.TransportedVectorTime;

public class NamedEvent implements Event, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	private TransportedVectorTime tvt;
	
	private Address local;
	
	private List<Address> target;
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
	public Address getLocalization() {
		//No need this method
		return null;
	}

	@Override
	public boolean setLocalization(Address url) {
		//No need this method
		return false;
	}

	@Override
	public List<Address> getTargetLocalization() {
		//No need this method
		return null;
	}

	@Override
	public boolean setTargetLocalization(List<Address> url) {
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
