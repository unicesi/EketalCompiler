package co.edu.icesi.ketal.distribution;

import java.io.Serializable;
import java.util.Map;

import co.edu.icesi.ketal.core.Event;



public class BrokerMessage implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3412486185506526207L;
	
	//Modified by Jhonny Ocampo
	private short typeOfMsg; //0 Async, 1 Sync, 2 Response of Sync Request
	
	private Map metadata;
	private Event event;

	public Map getMetadata() {
		return metadata;
	}

	public void setMetadata(Map m) {
		this.metadata = m;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event e) {
		this.event = e;
	}
	
	
	//Modified by Jhonny Ocampo
	public short getTypeOfMsg() {
		return typeOfMsg;
	}

	//Modified by Jhonny Ocampo
	public void setTypeOfMsg(short typeOfMsg) {
		this.typeOfMsg = typeOfMsg;
	}

	public BrokerMessage(Event e, Map m) {
		this.event = e;
		this.metadata = m;
		this.typeOfMsg = 0; //Modified by Jhonny Ocampo
	}
}
