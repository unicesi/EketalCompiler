package test;


import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Test;
import org.powermock.core.classloader.annotations.PrepareForTest;

import co.edu.icesi.eketal.automaton.DeadLockDetector;
import co.edu.icesi.eketal.handlercontrol._EventHandler;
import co.edu.icesi.ketal.core.Automaton;
import co.edu.icesi.ketal.core.Event;
import co.edu.icesi.ketal.core.NamedEvent;
import core.TreeCache;

public class TestAutomaton {

	Automaton instance = DeadLockDetector.getInstance();

    @Test
    @PrepareForTest({DeadLockDetector.class})
    public void testCase1(){
    	System.setProperty("java.net.preferIPv4Stack" , "true");
    	
        TreeCache hw = new TreeCache();
        
        hw.prepare();
		hw.commit();
    }
    
    @Test
    @PrepareForTest({DeadLockDetector.class})
    public void testCase2(){
    	System.setProperty("java.net.preferIPv4Stack" , "true");
    	
    	Event eventPrepare = new NamedEvent("prepareEvent");
        Event eventCommit = new NamedEvent("commitEvent");
    	
		String state = instance.getCurrentState().toString();
		assertFalse(instance.evaluate(eventCommit));
		
		assertEquals(state, instance.getCurrentState().toString());
		assertTrue(instance.evaluate(eventPrepare));
		
		assertNotEquals(state, instance.getCurrentState().toString());
		assertTrue(instance.evaluate(eventCommit));
		
		assertTrue(instance.getCurrentState().getAccept());
    }

}
