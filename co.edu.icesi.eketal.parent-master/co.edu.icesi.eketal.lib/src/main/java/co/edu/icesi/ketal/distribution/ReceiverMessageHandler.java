package co.edu.icesi.ketal.distribution;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jgroups.Message;

import co.edu.icesi.ketal.core.Event;

//Created by David Dur�n
public class ReceiverMessageHandler implements BrokerMessageHandler {
	
	private static final Log logger = LogFactory
			.getLog(ReceiverMessageHandler.class);
	
	public static Log getLogger() {
		return logger;
	}

	// The msg and typeOfMsgSent parameters are needed to manipulate the
	// synchronous messages
	@Override
	public Object handle(Event event, Map metadata, Message msg,
			int typeOfMsgSent) {
		// This is how the message should be handled if it is asynchronous
		if (typeOfMsgSent == 0) {
			logger.debug(this.hashCode() + "- KetalHandler! Event: "
					+ event + " Metadata: " + metadata);
//			System.out.println(this.hashCode() + "- KetalHandler! Event: "
//					+ event + " Metadata: " + metadata);
			logger.error(this.hashCode() + "- Processing: "
			+ event.toString());
//			System.err.println(this.hashCode() + "- Processing: "
//					+ event.toString());
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
