package test;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Test;

import co.edu.icesi.eketal.automaton.AutomatonConstructor;
import co.edu.icesi.eketal.handlercontrol.EventHandler;
import co.edu.icesi.ketal.core.Automaton;
import co.edu.icesi.ketal.core.Event;
import co.edu.icesi.ketal.core.NamedEvent;
import core.HelloWorld;

public class TestAutomaton {

	Automaton instance = AutomatonConstructor.getInstance();

    @Test
    public void testCase(){
    	System.setProperty("java.net.preferIPv4Stack" , "true");
    	
        Event eventHello = new NamedEvent("eventoHello");
        Event eventWorld = new NamedEvent("eventoWorld");
        
        HelloWorld hw = new HelloWorld();
        
        hw.helloMethod();
		hw.worldMethod();
        
		System.out.println(instance.getCurrentState().toString());
		String state = instance.getCurrentState().toString();
		assertFalse(instance.evaluate(eventWorld));
		
		System.out.println(instance.getCurrentState().toString());
		assertEquals(state, instance.getCurrentState().toString());
		assertTrue(instance.evaluate(eventHello));
		
		System.out.println(instance.getCurrentState().toString());
		assertNotEquals(state, instance.getCurrentState().toString());
		assertTrue(instance.evaluate(eventWorld));
		
		System.out.println(instance.getCurrentState().toString());
		assertFalse(instance.getCurrentState().getAccept());
    }

}
