package co.edu.icesi.ketal.distribution.transports.jgroups;

// JGroups imports
import org.jgroups.Message;
import org.jgroups.View;
import org.jgroups.stack.ProtocolStack;

import co.edu.icesi.ketal.distribution.BrokerMessage;

/**
 * Extends from JGroupsAbstractFacade class and works as sender and receiver to
 * handle asynchronous messages
 * 
 * @author dduran
 */
public class JGroupsAsyncFacade extends JGroupsAbstractFacade {

	// // Default logger
	// static org.apache.log4j.Logger logger = org.apache.log4j.Logger
	// .getLogger(JGroupsFacade.class);
	//
	// // Channel object, this is part of Jgroups API
	// Channel channel;
	// RpcDispatcher disp;
	// RspList rsp_list;
	// RequestOptions opts;
	// // RequestHandler disp;
	//
	// // The properties configuring the Jgroups communication stack
	// // Properties are managed by class DistributionProperties
	// String props = DistributionProperties.getInstance()
	// .getStack("AspectsGroup");
	//
	// private String groupName;
	// private EventBroker jeb;
	// private static int typeOfMsgSent = 0;
	//
	// private Object signal = new Object();

	/**
	 * Takes the base group name and concatenates the label ASYNC to it,
	 * indicating that this channel is going to connect to a group that works
	 * with asynchronous messages. The jeb is the class that handles the
	 * received messages. This method invokes the initializeFacade() method
	 * 
	 * @param groupName
	 * @param jeb
	 */
	public JGroupsAsyncFacade(String groupName, JGroupsEventBroker jeb) {
		super("ASYNC-" + groupName, jeb);
		initializeFacade();
	}

	/**
	 * Sets this class as the messages receiver and connects the channel to the
	 * ASYNC group
	 */
	@Override
	public void initializeFacade() {
		try {
			// channel = new JChannel(props);
			// channel = new JChannel();

			// With setOpet we set the Not self delivery property
			// channel.setOpt(Channel.LOCAL, false);
			// this.disp = new MessageDispatcher(channel, null, null, this);
			// channel.connect(DistributionProperties.getInstance().getGroupName(
			// groupName));
			// channel.connect("group");

			channel.setReceiver(this);
			// This is needed for the RpcDispatcher. The channel cannot set the
			// reciever to this
			// disp=new RpcDispatcher(channel,null,null,this);
			// opts = new RequestOptions(ResponseMode.GET_ALL, 5000);

			channel.connect(groupName);

			// logger.debug(channel.getView().toString());
		} catch (Exception e) {
			logger.debug(e);
		}
	}

	// public Channel getChannel() {
	// return channel;
	// }

	// private void broadcastMessage(BrokerMessage m, int x ) {
	//
	// }

	/**
	 * Sends the message m through the channel to all the nodes in the group
	 * 
	 * @param m
	 */
	public void broadcastMessageAsync(BrokerMessage m) {
		try {
			// channel.send(null, null, m);
			// Modified by Oscar Kiyoshige Garc�s.
			channel.send(null, m);
			typeOfMsgSent = 0;
		} catch (Exception ex) {
			logger.error("Error no message delivery: ", ex);
		}
	}

	public void viewAccepted(View new_view) {
		logger.debug("** view: " + new_view);
//		System.out.println("** view: " + new_view);
	}

	// Modified by Jhonny
	/**
	 * Receives the messages sent through the channel and handles them with the
	 * jeb defined in the constructor
	 * 
	 * @param msg
	 */
	public void receive(Message msg) {
		
		if (msg.getObject() instanceof BrokerMessage) {
			BrokerMessage bm = (BrokerMessage) msg.getObject();
			if (bm != null) {
				jeb.handle(bm.getEvent(), bm.getMetadata(), msg, 0);
				// switch (bm.getTypeOfMsg()) {
				// case 0:
				// jeb.handle(bm.getEvent(), bm.getMetadata(), msg, 0);
				// break;
				// case 1:
				// if (msg.getSrc().equals(channel.getAddress())) {
				// break;
				// }
				// System.out.println();
				// // Prints the Host Address that has recieved the message
				// System.out.println("Host Output: " + channel.getAddress());
				// System.out.println("Message Sync");
				// // Prints the Host Address that has sent the message
				// System.out.println("Source Host: " + msg.getSrc());
				// // Prints the message that has been recieved
				// System.out.println("Message Recieved: " + msg);
				//
				// // Saves the message id recieved into the attribute msg
				// String src = (String) jeb.handle(bm.getEvent(),
				// bm.getMetadata(), msg, 1);
				// // Prints the message id assigned by the NAKACK protocol
				// System.out.println("Message ID: " + src);
				//
				// // Validates if the message id sent equals to the message id
				// // recieved
				// // This is not necessary because the NAKACK protocol ensures
				// // that the message sent will send an ACK
				// /*
				// * if (src != null && ("[" +
				// * msg.getHeader(Short.parseShort("15"))
				// * .toString().split(" ")[1]).equals(src)) { this.msg =
				// * src.toString(); System.out.println("Message Recieved!"); }
				// */
				// System.out.println("Message Recieved!");
				//
				// BrokerMessage m = new BrokerMessage(null, null);
				// m.setTypeOfMsg(new Short("2"));
				// try {
				// channel.send(msg.getSrc(), m);
				// } catch (Exception e) {
				// 
				// e.printStackTrace();
				// }
				// break;
				// case 2:
				// System.out.println();
				// // Prints the Host Address that has recieved the message
				// System.out.println("Host Output: " + channel.getAddress());
				// System.out.println("Message Sync Response");
				// // Prints the Host Address that has sent the message
				// System.out.println("Source Host: " + msg.getSrc());
				// // Prints the message that has been recieved
				// System.out.println("Message Recieved: " + msg);
				//
				// // Validates if the message id sent equals to the message id
				// // recieved
				// // This is not necessary because the NAKACK protocol ensures
				// // that the message sent will send an ACK
				// /*
				// * if (src != null && ("[" +
				// * msg.getHeader(Short.parseShort("15"))
				// * .toString().split(" ")[1]).equals(src)) { this.msg =
				// * src.toString(); System.out.println("Message Recieved!"); }
				// */
				// System.out.println("Message Recieved!");
				// synchronized (signal) {
				// signal.notify();
				// }
				// break;
				// default:
				// break;
				//
				// }

			} else {
				logger.warn("null BrokerMessage received on method receive");
			}
		}
	}

}
