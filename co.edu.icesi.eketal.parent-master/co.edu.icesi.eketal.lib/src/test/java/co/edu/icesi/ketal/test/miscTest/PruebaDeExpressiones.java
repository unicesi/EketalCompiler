package co.edu.icesi.ketal.test.miscTest;
import java.util.Vector;

import co.edu.icesi.ketal.core.Automaton;
import co.edu.icesi.ketal.core.CausalEqualsExpression;
import co.edu.icesi.ketal.core.Event;
import co.edu.icesi.ketal.distribution.BrokerMessageHandler;
import co.edu.icesi.ketal.distribution.EventBroker;
import co.edu.icesi.ketal.distribution.KetalMessageHandler;
import co.edu.icesi.ketal.distribution.transports.jgroups.JGroupsEventBroker;
import co.edu.icesi.ketal.test.distribution.TestEvent1Distributed;


public class PruebaDeExpressiones {

	private static BrokerMessageHandler brokerMessageHandler;
	private static BrokerMessageHandler brokerMessageHandler2;
	static EventBroker eventBroker1;
	static EventBroker eventBroker2;
	static Automaton automaton;
	
	public static void main(String[] args) throws InterruptedException {
		
		
 		automaton= new Automaton("AAB");
 		Event event1 = new TestEvent1Distributed('1');
		Event event2 = new TestEvent1Distributed('2');
 		automaton.mapExpressionToAlphabet(new CausalEqualsExpression(event1), 'A');
 		automaton.mapExpressionToAlphabet(new CausalEqualsExpression(event2), 'B');
 		
 		automaton.evaluate(event1);
 		

	}
	
	public void pruebaDeCausalEqualsExpression() throws InterruptedException
	{

		
		brokerMessageHandler = new KetalMessageHandler();
		brokerMessageHandler2 = new KetalMessageHandler();
		eventBroker1 = new JGroupsEventBroker("UNO", brokerMessageHandler);
		eventBroker2 = new JGroupsEventBroker("UNO", brokerMessageHandler2);
		
		Event event1 = new TestEvent1Distributed('1');
		Event event2 = new TestEvent1Distributed('2');

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		eventBroker1.multicast(event1, null);
		eventBroker1.multicast(event1, null);
		Thread.sleep(10000);
		eventBroker2.multicast(event2, null);
		eventBroker2.multicast(event1, null);
 		eventBroker2.multicast(event2, null);
 		
 		Vector<Event> vector=((KetalMessageHandler)brokerMessageHandler).getVectorEvents();
 		
 		
 			Thread.sleep(5000);
 			vector=((KetalMessageHandler)brokerMessageHandler).getVectorEvents();

 		
 		System.out.println(vector.get(1));
 		System.out.println(vector.get(2));
 		
 		CausalEqualsExpression cau= new CausalEqualsExpression(vector.get(1));
 		
 		System.out.println("vect 2 causes vect 1?");
 		System.out.println(vector.get(2).getTransportedVectorTime().getValues());
 		System.out.println(cau.evaluate((vector.get(2))));
 		
 		System.out.println("vect 1 causes vect 2?");
 		System.out.println(vector.get(1).getTransportedVectorTime().getValues());
 		cau.setEvent(vector.get(1));
 		System.out.println(cau.evaluate((vector.get(2))));
	}

}
