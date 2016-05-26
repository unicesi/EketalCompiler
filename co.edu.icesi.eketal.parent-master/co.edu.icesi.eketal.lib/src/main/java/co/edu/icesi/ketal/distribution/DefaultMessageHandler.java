package co.edu.icesi.ketal.distribution;

import java.util.Map;
import java.util.Vector;

import org.jgroups.Message;

import co.edu.icesi.ketal.core.Event;

public class DefaultMessageHandler implements BrokerMessageHandler {

	public DefaultMessageHandler() {

	}

	/*
	 * @Override public Object handle(Event event, Map metadata) {
	 * System.out.println("DefaultHandler! Event: " + event + " Metadata: " +
	 * metadata); System.err.println("Processing: " + event.toString()); return
	 * event; }
	 */

	@Override
	public Object handle(Event event, Map metadata, Message msg,
			int typeOfMsgSent) {
		if (typeOfMsgSent == 0) {
			System.out.println("DefaultHandler! Event: " + event
					+ " Metadata: " + metadata);
			System.err.println("Processing: " + event.toString());
		}
		return event;
	}

}
