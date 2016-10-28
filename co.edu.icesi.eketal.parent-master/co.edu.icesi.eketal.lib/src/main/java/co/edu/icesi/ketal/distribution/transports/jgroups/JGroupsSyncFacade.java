package co.edu.icesi.ketal.distribution.transports.jgroups;

import org.jgroups.Message;
import org.jgroups.blocks.MethodCall;
//import org.jgroups.blocks.RequestOptions;
//import org.jgroups.blocks.ResponseMode;
import org.jgroups.blocks.RpcDispatcher;
//import org.jgroups.util.NotifyingFuture;
import org.jgroups.util.RspList;
import co.edu.icesi.ketal.distribution.BrokerMessage;

//Created By David Dur�n
/**
 * Extends from JGroupsAbstractFacade class and works as sender and receiver to
 * handle synchronous messages
 * 
 * @author dduran
 */
public class JGroupsSyncFacade extends JGroupsAbstractFacade {

	// // Default logger
	// static org.apache.log4j.Logger logger = org.apache.log4j.Logger
	// .getLogger(JGroupsSyncFacade.class);
	//
	// // This is a list of responses that is fill when a multicast message is
	// sent
	// // inside a
	// // group of hosts
	// RspList rsp_list;
	// RequestOptions opts;
	//
	// // Channel object, this is part of Jgroups API
	// Channel channel;
	//
	// // Message Dispatcher, Jgroups API
	// RpcDispatcher disp;
	// private String groupName;
	// private EventBroker jeb;
	//
	// // Modified by David Dur�n
	// // Contains the NAKACK recieved message
	// private String msg = null;
	// // Validates if the sender should wait until theres a NACK recieved
	// private boolean go = true;
	// // Indicates the type of message that has been sent (0-Async, 1-Sync)
	// private static int typeOfMsgSent = 0;
	//
	// private Object signal = new Object();
	//
	/**
	 * Takes the base group name and concatenates the label SYNC to it,
	 * indicating that this channel is going to connect to a group that works
	 * with synchronous messages. This method invokes the initializeFacade()
	 * method
	 * 
	 * @param groupName
	 * @param jeb
	 */
	public JGroupsSyncFacade(String groupName) {
		super("SYNC-" + groupName);
		initializeFacade();
	}

	/**
	 * Sets the RpcDispatcher as the messages receiver, Initializes the
	 * receiving policies (Get_All, Get_First) setting a time out and connects
	 * the channel to the SYNC group
	 */
	public void initializeFacade() {
		try {
			// This is needed for the RpcDispatcher. The channel cannot set the
			// reciever to this
			disp = new RpcDispatcher(channel, null, null, this);
//			opts = new RequestOptions(ResponseMode.GET_ALL, 5000);

			channel.connect(groupName);

			// logger.debug(channel.getView().toString());
		} catch (Exception e) {
			logger.debug(e.toString());
		}
	}

	// Created by David Dur�n
	/**
	 * Prepares a MethodCall with the given method (method_name) from the Class
	 * (class_name) that has the parameters (method_parameters) to be executed
	 * in every node in the group. This method waits until every node (including
	 * the sender) gives a response (process the method) or until the time out
	 * expires and returns this responses into a list
	 * 
	 * @param class_name
	 * @param method_name
	 * @param method_parameters
	 * @return response list
	 */
	public RspList broadcastMessageSync(String class_name, String method_name,
			Object... method_parameters) {
		MethodCall call;
		try {
			// Saves the class type of the method parameters into an array thats
			// needed to invoke the method call
			Class<?>[] array = new Class<?>[method_parameters.length];
			for (int i = 0; i < array.length; i++) {
				array[i] = method_parameters[i].getClass();
			}
			// Configures the method with its parameters types
			call = new MethodCall(Class.forName(class_name).getMethod(
					method_name, array));
			// Set the method parameters
			call.setArgs(method_parameters);
			// Waits for all nodes (including the sender) to execute the method
			// configured (call) and saves the results in a list
			
			
			rsp_list = (RspList) disp.callRemoteMethod(null, call, 0, (long)5000);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}

		return rsp_list;
	}

	// Created by David Dur�n
	/**
	 * Prepares a MethodCall with the given method (method_name) from the Class
	 * (class_name) that has the parameters (method_parameters) to be executed
	 * in every node in the group. This is not a synchronous method, which means
	 * that the result list will be empty (and the application wont wait for
	 * this method to be executed to continue with its logic) until each node
	 * process the method and return a NotifyingFuture indicating that the
	 * operation has been completed
	 * 
	 * @param class_name
	 * @param method_name
	 * @param method_parameters
	 * @return result list
	 */
//	public NotifyingFuture<RspList<Object>> broadcastMessageWithFuture(
//			String class_name, String method_name, Object... method_parameters) {
//		MethodCall call;
//		NotifyingFuture<RspList<Object>> futures = null;
//		try {
//			// Saves the class type of the method parameters into an array thats
//			// needed to invoke the method call
//			Class<?>[] array = new Class<?>[method_parameters.length];
//			for (int i = 0; i < array.length; i++) {
//				array[i] = method_parameters[i].getClass();
//			}
//			// Configures the method with its parameters types
//			call = new MethodCall(Class.forName(class_name).getMethod(
//					method_name, array));
//			// Set the method parameters
//			call.setArgs(method_parameters);
//
//			futures = disp.callRemoteMethodsWithFuture(null, call, opts);
//
//		} catch (NoSuchMethodException e) {
//			e.printStackTrace();
//		} catch (SecurityException e) {
//
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return futures;
//	}

	// Created by David Dur�n
	/*
	 * public RspList broadcastMessageSync(String method_name) { MethodCall
	 * call; try {
	 * 
	 * // this.channel.setReceiver(null); // Configures the method with its
	 * parameters types call = new MethodCall(getClass().getMethod(method_name,
	 * int.class)); // Set the method parameters call.setArgs(4); // Waits for
	 * all nodes (including the sender) to execute the method // configured
	 * (call) and saves the results in a list super.rsp_list =
	 * super.disp.callRemoteMethods(null, call, opts);
	 * 
	 * } catch (NoSuchMethodException e) { 
	 * e.printStackTrace(); } catch (SecurityException e) {
	 *  e.printStackTrace(); } catch (Exception e) {
	 *  e.printStackTrace(); }
	 * 
	 * return super.rsp_list; }
	 */

	// Modified by David Dur�n
	// public void broadcastMessageSync1(BrokerMessage m) {
	// try {
	// // Validates if this is the first time that a message is about to be
	// // sent
	// if (msg == null && go) {
	// channel.send(null, m);
	// typeOfMsgSent = 1;
	// go = false;
	// } else {
	// // Loop that holds the sender until theres a NACK recieved
	// System.out.print("Waiting");
	//
	// do {
	// System.out.print(".");
	// Thread.sleep(200);
	// if (msg != null) {
	// channel.send(null, m);
	// System.out.println();
	// }
	// } while (msg == null);
	//
	// msg = null;
	// }
	// } catch (Exception ex) {
	// logger.error("Error no message delivery: ", ex);
	// }
	// }

	// Created by Jhonny Ocampo
	// public void broadcastMessageSync(BrokerMessage m) {
	// try {
	// m.setTypeOfMsg(new Short("1"));
	// channel.send(null, m);
	//
	// synchronized (signal) {
	// signal.wait();
	// }
	//
	// System.out.println("broadcastMessageSync Successful");
	//
	// } catch (Exception ex) {
	// logger.error("Error no message delivery: ", ex);
	// }
	// }

	// Modified by David Dur�n
	// public void receive1(Message msg) {
	// BrokerMessage bm = (BrokerMessage) msg.getObject();
	// if (bm != null) {
	// if (typeOfMsgSent == 0)
	// jeb.handle(bm.getEvent(), bm.getMetadata(), msg, typeOfMsgSent);
	// else {
	// System.out.println();
	// // Prints the Host Address that has recieved the message
	// System.out.println("Host: " + jeb.getSyncAddress());
	// // Prints the message that has been recieved
	// System.out.println("Message Recieved: " + msg);
	//
	// // Saves the message id recieved into the attribute msg
	// String src = (String) jeb.handle(bm.getEvent(),
	// bm.getMetadata(), msg, typeOfMsgSent);
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
	// }
	//
	// } else {
	// logger.warn("null BrokerMessage received on method receive");
	// }
	//
	// }
}
