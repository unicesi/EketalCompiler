package co.edu.icesi.ketal.test.automatonNoRegEx;

import static org.junit.Assert.*;

import java.net.URL;
import java.util.Map;

import org.jgroups.Message;
import org.junit.Test;

import co.edu.icesi.ketal.core.Automaton;
import co.edu.icesi.ketal.core.Event;
import co.edu.icesi.ketal.distribution.BrokerMessageHandler;
import co.edu.icesi.ketal.distribution.EventBroker;
import co.edu.icesi.ketal.distribution.ReceiverMessageHandler;
import co.edu.icesi.ketal.distribution.transports.jgroups.JGroupsEventBroker;

public class TestURL {

	@Test
	public void test() {
//		System.setProperty("java.net.preferIPv4Stack" , "true");
		
		BrokerMessageHandler brokerMessageHandler = new ReceiverMessageHandler(){
	    	@Override
	    	public Object handle(Event event, Map metadata, Message msg,
	    	    			int typeOfMsgSent){
	    	    
	    		return event;
	    	}
	    };
	    EventBroker eventBroker = new JGroupsEventBroker("Eketal", brokerMessageHandler);
		
		
		URL url = eventBroker.getAsyncAddress();
//		System.out.println(url.getHost());
	}

}
