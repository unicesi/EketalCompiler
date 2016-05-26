package co.edu.icesi.ketal.distribution;

import java.util.Map;
import java.util.Vector;

import org.jgroups.Message;

import co.edu.icesi.ketal.core.Event;


public interface BrokerMessageHandler {
	
	//public Object handle(Event event, Map metadata);
	
	//Modified by David Durán
	//The msg and typeOfMsgSent parameters are needed to manipulate the synchronous messages
	public Object handle(Event event, Map metadata, Message msg, int typeOfMsgSent);

	//Test
	//public Object handle(Event event, Map metadata, Message msg);
}
