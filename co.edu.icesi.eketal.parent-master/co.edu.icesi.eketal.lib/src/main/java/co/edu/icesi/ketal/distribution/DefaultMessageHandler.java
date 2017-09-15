package co.edu.icesi.ketal.distribution;

import java.util.Map;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jgroups.Message;

import co.edu.icesi.ketal.core.Event;
import co.edu.icesi.ketal.distribution.transports.jgroups.JGroupsAbstractFacade;

public class DefaultMessageHandler implements BrokerMessageHandler {
	
	private static final Log logger = LogFactory
			.getLog(DefaultMessageHandler.class);
	
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
			logger.debug("DefaultHandler! Event: " + event
			+ " Metadata: " + metadata);
//			System.out.println("DefaultHandler! Event: " + event
//					+ " Metadata: " + metadata);
			logger.error("Processing: " + event.toString());
//			System.err.println("Processing: " + event.toString());
		}
		return event;
	}

}
