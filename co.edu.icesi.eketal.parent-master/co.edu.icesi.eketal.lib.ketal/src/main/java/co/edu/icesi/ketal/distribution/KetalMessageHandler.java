package co.edu.icesi.ketal.distribution;

import java.util.Map;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jgroups.Message;

import co.edu.icesi.ketal.core.Event;

public class KetalMessageHandler implements BrokerMessageHandler {
	
	private static final Log logger = LogFactory
			.getLog(KetalMessageHandler.class);
	
	private Vector<Event> vector;

	public KetalMessageHandler() {
		vector = new Vector<Event>();
	}

	// The way this handle the message is adding in the events vector.
	@Override
	public Event handle(Event event, Map metadata, Message msg,
			int typeOfMsgSent) {
		if (typeOfMsgSent == 0) {
			logger.debug(this.hashCode() + "- KetalHandler! Event: "
					+ event + " Metadata: " + metadata);
//			System.out.println(this.hashCode() + "- KetalHandler! Event: "
//					+ event + " Metadata: " + metadata);
			
			logger.error(this.hashCode() + "- Processing: "
			+ event.toString());
//			System.err.println(this.hashCode() + "- Processing: "
//					+ event.toString());
			vector.add(event);
		}
		return event;

	}

	// New methog implemented of interface.
	public Vector<Event> getVectorEvents() {
		return vector;
	}

	/*@Override
	public Object handle(Event event, Map metadata, Message msg) {
		return null;
	}*/

}
