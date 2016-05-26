package co.edu.icesi.ketal.distribution;

import java.util.Map;

import org.jgroups.Message;

import co.edu.icesi.ketal.core.Event;

//Created by David Durán
public class ReceiverMessageHandler implements BrokerMessageHandler {

	
	// The msg and typeOfMsgSent parameters are needed to manipulate the
	// synchronous messages
	@Override
	public Object handle(Event event, Map metadata, Message msg,
			int typeOfMsgSent) {
		// This is how the message should be handled if it is asynchronous
		if (typeOfMsgSent == 0) {
			System.out.println(this.hashCode() + "- KetalHandler! Event: "
					+ event + " Metadata: " + metadata);
			System.err.println(this.hashCode() + "- Processing: "
					+ event.toString());
			return event;
		}
		// This is how the message should be handled if it is synchronous
		else {
			/*
			 * System.out.println(msg.getSrc()+" "+msg.getDest() +
			 * " :::::::::::::::::::: " +
			 * "["+msg.getHeader(Short.parseShort("15")).toString()
			 * .split(" ")[1]);
			 */
			return "["
					+ msg.getHeader(Short.parseShort("15")).toString()
							.split(" ")[1];
			
		}

	}

	/*
	 * @Override public Object handle(Event event, Map metadata, Message msg) {
	 * System.out .println(msg.getSrc() + " :::::::::::::::::::: " +
	 * msg.getHeader(Short.parseShort("15")).toString() .split(" ")[1]); return
	 * msg.getHeader(Short.parseShort("15")).toString().split(" ")[1]; }
	 */

}
