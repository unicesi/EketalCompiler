package co.edu.icesi.ketal.distribution.transports.jgroups;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jgroups.Address;
import org.jgroups.Channel;
import org.jgroups.Message;
import org.jgroups.PhysicalAddress;
import org.jgroups.stack.IpAddress;
import org.jgroups.util.NotifyingFuture;
import org.jgroups.util.RspList;

import co.edu.icesi.ketal.core.Event;
import co.edu.icesi.ketal.distribution.BrokerMessage;
import co.edu.icesi.ketal.distribution.BrokerMessageHandler;
import co.edu.icesi.ketal.distribution.DefaultMessageHandler;
import co.edu.icesi.ketal.distribution.EventBroker;

/**
 * This class manages the communication of messages using JGroups. It implements
 * the Interfaces EvenBroker so it can be used in framework using the class
 * EventBroker.
 * 
 * @author Luis Daniel Benavides Navarro
 * @version 0.1
 */
public class JGroupsEventBroker implements EventBroker {

	private JGroupsAsyncFacade asyncMonitor;
	private JGroupsSyncFacade syncMonitor;
	private String groupName;
	private BrokerMessageHandler messageHandler;
	
	protected static final Log logger = LogFactory
			.getLog(JGroupsAbstractFacade.class);
	
	public JGroupsEventBroker(String groupName, BrokerMessageHandler bmh) {
		System.setProperty("java.net.preferIPv4Stack" , "true");
		this.groupName = groupName;
		this.messageHandler = bmh;
		this.asyncMonitor = new JGroupsAsyncFacade(groupName, this);
		this.syncMonitor = new JGroupsSyncFacade(groupName);
	}
	
	public JGroupsEventBroker(String groupName, BrokerMessageHandler bmh, boolean asynOn) {
		System.setProperty("java.net.preferIPv4Stack" , "true");
		this.groupName = groupName;
		this.messageHandler = bmh;
		if(asynOn){			
			this.asyncMonitor = new JGroupsAsyncFacade(groupName, this);
		}else{
			this.syncMonitor = new JGroupsSyncFacade(groupName);			
		}
	}

	public JGroupsEventBroker(String groupName) {
		this(groupName, new DefaultMessageHandler());
	}

	@Override
	public void multicast(Event e, Map metadata) {
		BrokerMessage m = new BrokerMessage(e, metadata);
		asyncMonitor.broadcastMessageAsync(m);
	}

	/*
	 * @Override public Object handle(Event e, Map metadata) { return
	 * messageHandler.handle(e, metadata); }
	 */

	@Override
	public URL getAsyncAddress() {	    
		return asyncMonitor.getIpAddress();
	}

	@Override
	public URL getSyncAddress() {
	    return syncMonitor.getIpAddress();
	}

	
	// Modified by David Dur�n
	// Method that calls the broadcastMessageSync(m) method for synchronous
	// messages
	@Override
	public void multicastSync(Event e, Map metadata) {
		BrokerMessage m = new BrokerMessage(e, metadata);
//		syncMonitor.broadcastMessageSync(m);
	}

	// Modified by David Dur�n
	// The msg and typeOfMsgSent parameters are needed to manipulate the
	// synchronous messages
	@Override
	public Object handle(Event e, Map metadata, Message msg, int typeOfMsgSent) {
		return messageHandler.handle(e, metadata, msg, typeOfMsgSent);
	}

	//Created by David Dur�n
	@Override
	public RspList<Object> multicastSync(String class_name, String method_name, Object... parameters) {
		return syncMonitor.broadcastMessageSync(class_name, method_name, parameters);
	}
	
	//Created by David Dur�n	
	@Override
	public NotifyingFuture<RspList<Object>> multicastWithFutures(String class_name,
			String method_name, Object... parameters) {
		return syncMonitor.broadcastMessageWithFuture(class_name, method_name, parameters);
	}

}
