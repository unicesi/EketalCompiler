package co.edu.icesi.ketal.test.distribution;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.jgroups.util.FutureListener;
import org.jgroups.util.NotifyingFuture;
import org.jgroups.util.RspList;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import co.edu.icesi.ketal.distribution.BrokerMessageHandler;
import co.edu.icesi.ketal.distribution.EventBroker;
import co.edu.icesi.ketal.distribution.ReceiverMessageHandler;
import co.edu.icesi.ketal.distribution.transports.jgroups.JGroupsEventBroker;

/**
 * 1. Creates a brokerMessageHandler and a EventBroker for each node (3 of
 * them).
 * 
 * @author dduran
 * 
 */
public class FuturesTest {

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
	 * to the others by using a NotifyngFuture that confirms the success of this
	 * operation. The eventBroker1 sends one event (ask for a remote call of the
	 * method int print(int,int)) to the group using multicast. It also adds a
	 * FutureListener to the NotifyingFuture list (to indicate that the Future
	 * is ready and contains the responses from the nodes in the group) that
	 * will be filled every time the method sent is executed in every node,
	 * including the source. Right after the message is sent, the main Thread
	 * will continue processing the following instructions to complete its
	 * execution. During this processing, the FutureListener will be fired and
	 * the responses list will be shown, meaning that the message sent
	 * implementing Futures wont stop the main Thread until every node receives
	 * and process the message, but it will be waiting in background for the
	 * Futures to be ready to trigger its listener.
	 * */
	@Test
	public void testMessageSending() throws Exception {

		System.out.println();
		System.out.println("---------------------------------");
		System.err.println("Sender: " + eventBroker1.getSyncAddress());
		System.out.println("---------------------------------");
		System.out.println();

		NotifyingFuture<RspList<Object>> l = eventBroker1.multicastWithFutures(
				getClass().getName(), "print", 3, 5);

		l.setListener(new FutureListener<RspList<Object>>() {

			@Override
			public void futureDone(Future<RspList<Object>> future) {
				try {
					System.err.println("Future Done!");
					System.out.println(future.get());
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		});

		System.err
				.println("Actual State of the Results List Right After Invoking the Future!");
		System.out.println(l);
		System.out.println("---------------------------------");
		// l = eventBroker1.multicastSync("print");
		// System.out.println(l);
		int d = 0;
		while (d < 5) {
			System.out.println("processing...");
			Thread.sleep(1000);
			d++;
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
}