package co.edu.icesi.ketal.test.miscTest;
import java.net.URL;
import java.util.List;

import org.jgroups.Address;
import org.jgroups.protocols.TransportedVectorTime;

import co.edu.icesi.ketal.core.Event;


public class StackEvent implements Event {

	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private TransportedVectorTime tvt;
	/*
	 * Constructor.
	 */
	public StackEvent(String event)
	{
		name=event;
		tvt = null;
	}
	
	@Override
	public boolean equals(Event e) {
		
		if(e instanceof StackEvent)
		{
			return (((StackEvent)e).name).equals(this.name);
		}
		return false;
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
