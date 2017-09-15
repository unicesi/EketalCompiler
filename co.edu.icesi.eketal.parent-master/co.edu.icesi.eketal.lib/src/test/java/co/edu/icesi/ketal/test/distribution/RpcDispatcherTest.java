package co.edu.icesi.ketal.test.distribution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import org.jgroups.util.RspList;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import co.edu.icesi.ketal.distribution.BrokerMessageHandler;
import co.edu.icesi.ketal.distribution.EventBroker;
import co.edu.icesi.ketal.distribution.ReceiverMessageHandler;
import co.edu.icesi.ketal.distribution.transports.jgroups.JGroupsEventBroker;

//Created by David Dur�n
/**
 * 1. Creates a brokerMessageHandler and a EventBroker for each node (3 of
 * them).
 * 
 * @author dduran
 * 
 */
public class RpcDispatcherTest {

	private BrokerMessageHandler brokerMessageHandler;
	private BrokerMessageHandler brokerMessageHandler2;
	private BrokerMessageHandler brokerMessageHandler3;
	EventBroker eventBroker1;
	EventBroker eventBroker2;
	EventBroker eventBroker3;

	@Before
	public void setUp() throws Exception {

		// Setup the properties for message handlers and the event brokers
		brokerMessageHandler = new ReceiverMessageHandler();
		brokerMessageHandler2 = new ReceiverMessageHandler();
		brokerMessageHandler3 = new ReceiverMessageHandler();
		eventBroker1 = new JGroupsEventBroker("UNO", brokerMessageHandler);
		eventBroker2 = new JGroupsEventBroker("UNO", brokerMessageHandler2);
		eventBroker3 = new JGroupsEventBroker("UNO", brokerMessageHandler3);
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * This test just checks that messages are being send from the first broker
	 * to the others in a synchronous way by using a RCPDispatcher that confirms
	 * the success of this operation. The eventBroker1 sends one event (ask for
	 * a remote call of the method int print(int,int)) to the group using
	 * multicast. This remote call will stop the main Thread execution until
	 * every node process the method and returns the result of that operation.
	 * This responses will be saved in a Response List to be shown later. The
	 * logic of this method works as follows: 
	 * 1. The sync addresses of the brokers involved are saved in a list (l) 
	 * 2. The Response list will contain all the nodes an its responses, each one
	 * indicating if the messaged was received successfully
	 * 3. A new list (k) will contain the addresses of the nodes that have 
	 * Succeeded in receiving and processing the message sent (method call)
	 * 4. both lists (l) and (k) are sorted by the brokers addresses
	 * 5. The test will Assert true if both lists contain the same number
	 * of nodes with the same address, indicating that every original node in the
	 * group have successfully processed the message sent. The test will
	 * Assert false otherwise.
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@Test
	public void testMessageSending() throws Exception {

		// Saves all the brokers involved in a list
		ArrayList brokersList = new ArrayList();
		brokersList.add(eventBroker1.getSyncAddress().toString());
		brokersList.add(eventBroker2.getSyncAddress().toString());
		brokersList.add(eventBroker3.getSyncAddress().toString());

		// Prints the sender address
		System.out.println();
		System.out.println("---------------------------------");
		System.err.println("Sender: " + eventBroker1.getSyncAddress());
		System.out.println("---------------------------------");
		System.out.println();

		// Waits until every node process the method indicated and send a
		// response that confirms that the process succeeded
		RspList l = eventBroker1.multicastSync(getClass().getName(), "print",
				4, 5);

		// Prints the recieved results from the nodes including the sender
		System.err.println("--- Recieved Results ---");
		System.out.println(l);
		System.out.println("---------------------------------");

		// Saves the addresses of the nodes that processed the synchronous
		// method and sorts them by its hash code
		ArrayList resultsList = new ArrayList();
		Iterator iterator = l.entrySet().iterator();
		Object next;
		while (iterator.hasNext()) {
			next = iterator.next();
			if (next.toString().split("[=, ]")[8].equals("true"))
				resultsList.add(next.toString().split("=")[0]);
		}

		Collections.sort(resultsList, new Comparator() {

			@Override
			public int compare(Object o1, Object o2) {
				return o1.hashCode() - o2.hashCode();
			}
		});

		// Sorts the addresses from the brokers involved by its hash code
		Collections.sort(brokersList, new Comparator() {

			@Override
			public int compare(Object o1, Object o2) {
				return o1.hashCode() - o2.hashCode();
			}
		});

		// Compares the original brokers with the nodes that sent a response. If
		// there is not one missing then the synchronous method was executed in
		// every host, otherwise it failed
		for (int i = 0; i < resultsList.size(); i++) {
			if (!resultsList.get(i).equals(brokersList.get(i))) {
				Assert.assertTrue(false);
			}
		}
		Assert.assertTrue(true);

	}

	/**
	 * This is the method that will be executed remotely by ever node in the
	 * group
	 * 
	 * @param numberA
	 * @param numberB
	 * @return multiplication of numberA and numberB
	 */
	public static int print(Integer numberA, Integer numberB) {
		return numberA * numberB;
	}

	public static void test() {
		// Does nothing...
	}
}
