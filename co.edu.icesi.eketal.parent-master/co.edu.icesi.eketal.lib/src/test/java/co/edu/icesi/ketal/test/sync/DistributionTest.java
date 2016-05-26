package co.edu.icesi.ketal.test.sync;


import java.util.ArrayList;
import java.util.Vector;

import org.jgroups.stack.NakReceiverWindow;
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
public class DistributionTest {

	private BrokerMessageHandler brokerMessageHandler;
	private BrokerMessageHandler brokerMessageHandler2;
	EventBroker eventBroker1;
	EventBroker eventBroker2;
	private Automaton automaton;
	
	
	@Before
	public void setUp() throws Exception {
	
		//Setup the properties for message handlers and the event brokers
		brokerMessageHandler = new ReceiverMessageHandler();
		brokerMessageHandler2 = new KetalMessageHandler();
		eventBroker1 = new JGroupsEventBroker("UNO", brokerMessageHandler);
		eventBroker2 = new JGroupsEventBroker("UNO", brokerMessageHandler2);
			
		//Set up the automaton and create the events to be recognized.
		automaton= new Automaton();
		automaton.mapExpressionToAlphabet(new DefaultEqualsExpression(new TestEvent1Distributed('a')), new Character('A'));
		automaton.mapExpressionToAlphabet(new DefaultEqualsExpression(new TestEvent1Distributed('b')), new Character('B'));
		automaton.mapExpressionToAlphabet(new DefaultEqualsExpression(new TestEvent1Distributed('c')), new Character('C'));
		automaton.setRegularExpression("ABB|CAAB");
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
		Event event2 = new TestEvent1Distributed('2');

		Thread.sleep(5000);
		
		eventBroker1.multicastSync(event1, null);
		Thread.sleep(2000);
		eventBroker1.multicastSync(event1, null);
		Thread.sleep(2000);
//		eventBroker2.multicast(event2, null);
//		eventBroker2.multicast(event1, null);
// 		eventBroker2.multicast(event2, null);
 		
 		Thread.sleep(5000);
 		
 		Vector<Event> myVector = new Vector<Event>();
 		myVector.add(event1);
 		myVector.add(event1);
 		myVector.add(event2);
 		myVector.add(event1);
 		myVector.add(event2);
 		
// 		Class<? extends BrokerMessageHandler> c = brokerMessageHandler.getClass();
// 		System.out.println("--------------------------------------");
// 		System.out.println(c.getName());
// 		if (brokerMessageHandler instanceof BrokerMessageHandler) {
//			System.out.println("Es por esto el error!");
//		}
 		
 	    Vector<Event> anVector = ((((KetalMessageHandler) brokerMessageHandler2).getVectorEvents()));
 	    
 	    for(int index=0; index<anVector.size(); index++)
 	    {
 	    	Event myEvent = myVector.get(index);
 	    	Event anEvent = anVector.get(index);
 	    	if(!myEvent.equals(anEvent))
 	    	{
 	    		Assert.assertTrue(false);
 	    	}
 	    }
 	    Assert.assertTrue(true);
 	    
	}

	/**
	 * This test must check if the patron of events are recognized by the automaton created before.
	 * Creates three differents events and then the broker send events, generating a patron
	 * this patron must be recognized by the automaton.
	 */
//	@Test
//	public void testAutomatonRecognizer() throws Exception
//	{
//		Event event1 = new TestEvent1Distributed('a');
//		Event event2 = new TestEvent1Distributed('b');
//		Event event3 = new TestEvent1Distributed('c');
//
//		
//		
//		Thread.sleep(5000);
//		
//		eventBroker1.multicast(event3, null);
//		eventBroker1.multicast(event1, null);
//		Thread.sleep(2000);
//		eventBroker2.multicast(event1, null);
//		eventBroker2.multicast(event2, null);
//		
//		Thread.sleep(2000);
//		Assert.assertTrue(automaton.isAWordAutomaton(((KetalMessageHandler)brokerMessageHandler2).getVectorEvents()));
//		
//	}
}
