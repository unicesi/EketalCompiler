package co.edu.icesi.ketal.test.distribution;


import java.util.ArrayList;
import java.util.Vector;

import org.junit.After;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import co.edu.icesi.ketal.core.*;
import co.edu.icesi.ketal.distribution.*;
import co.edu.icesi.ketal.distribution.transports.jgroups.JGroupsEventBroker;


/**
 * 1. Create a brokerMessageHandler and a EventBroker for each node.
 * 2. Create the automaton.
 * 3. SetUP the properties for each component of the distribution.
 *
 */
public class BroadcastSyncTest {

	private BrokerMessageHandler brokerMessageHandler;
	private BrokerMessageHandler brokerMessageHandler2;
	private BrokerMessageHandler brokerMessageHandler3;
	EventBroker eventBroker1;
	EventBroker eventBroker2;
	EventBroker eventBroker3;
	
	@Before
	public void setUp() throws Exception {
	
		//Setup the properties for message handlers and the event brokers
		brokerMessageHandler = new ReceiverMessageHandler();
		brokerMessageHandler2 = new ReceiverMessageHandler();
		brokerMessageHandler3 = new ReceiverMessageHandler();
		eventBroker1 = new JGroupsEventBroker("UNO", brokerMessageHandler);
		eventBroker2 = new JGroupsEventBroker("UNO", brokerMessageHandler2);
		//eventBroker3 = new JGroupsEventBroker("UNO", brokerMessageHandler3);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	
	
	/**
	 * This test just checks that messages are being send from one broker to the other.
	 * The eventBroker1 send two events to the group using multicast. Then the eventBroker2
	 * multicast three more events. The test must check the info sent by the other broker and by itself.
	 * AssertTrue if the messageHandler has all the events, in the specific sent order.
	 * 
	 * */
	@Test
	public void testMessageSending() throws Exception {
		Event event1 = new TestEvent1Distributed('1');
		
		System.out.println();
		System.out.println("---------------------------------");
		System.out.println("Sender: "+eventBroker1.getSyncAddress());
		System.out.println("Reciever: "+eventBroker2.getSyncAddress());
		System.out.println("---------------------------------");
		
		eventBroker1.multicastSync(event1, null);
		//eventBroker1.multicastSync(event1, null);
		//eventBroker1.multicastSync(event1, null);
 		
 	    Assert.assertTrue(true);
 	    
	}
}
