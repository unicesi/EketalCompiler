package co.edu.icesi.ketal.distribution;

import java.util.ArrayList;
import java.util.Map;

import org.jgroups.Address;
import org.jgroups.Message;
import org.jgroups.util.NotifyingFuture;
import org.jgroups.util.RspList;

import co.edu.icesi.ketal.core.Event;

/**
 * This Interface is the main element to decouple the transport layer from the
 * operator-framework. Concrete brokers must implement this interface and use
 * specific transport libraries e.g., JGroups.
 * 
 * The framework creates an EventBroker with an specialized message handler.
 * Such Handler has the semantics of message reception that are different for
 * each platform.
 * 
 * Current implementation only provides asynchronous dispatching of messages (no
 * answer is expected). However this may change to support new functionalities.
 * 
 * @author Luis Daniel Benavides Navarro
 * @version 0.1, 27-10-2010
 */
public interface EventBroker {

	// Brodcast a message and does not care about any answer
	public void multicast(Event e, Map metadata);

	//
	// public Object handle(Event e, Map metadata);

	// Modified by David Dur�n
	// The msg and typeOfMsgSent parameters are needed to manipulate the
	// synchronous messages
	public Object handle(Event e, Map metadata, Message msg, int typeOfMsgSent);

	// Modified by David Dur�n
	// This is the method that will send synchronous messages
	public void multicastSync(Event e, Map metadata);

	// Created by David Dur�n
	// This method returns a list that contains the results of the method called
	// (method_name) executed in every node
	public RspList<Object> multicastSync(String class_name, String method_name,
			Object... parameters);

	// Created by David Dur�n
	// This method returns a list that is filled asynchronously and contains the
	// results of the method called (method_name) executed in every node
	public NotifyingFuture<RspList<Object>> multicastWithFutures(
			String class_name, String method_name, Object... parameters);

	// Written by Andr�s Barrera
	public String getAsyncAddress();
	
	public String getSyncAddress();
	
	//Written by Camilo Pimienta: This method allows to close the channel where the communication is stored
	public void closeComunication();

}
