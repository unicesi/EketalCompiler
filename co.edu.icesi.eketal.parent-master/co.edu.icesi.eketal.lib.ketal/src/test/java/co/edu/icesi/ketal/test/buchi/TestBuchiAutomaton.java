package co.edu.icesi.ketal.test.buchi;

import static org.junit.Assert.*;

import org.junit.Test;

import co.edu.icesi.ketal.core.Automaton;
import co.edu.icesi.ketal.core.NamedEvent;

public class TestBuchiAutomaton {

	//@Test
	public void testBasicAutomaton() {
		Automaton automaton = TestBuchiCase.getInstance();
		
		NamedEvent anyEvent = new NamedEvent("Tales");
		boolean evaluar = automaton.evaluate(anyEvent);
		assertTrue(evaluar);
		evaluar = automaton.evaluate(anyEvent);
		assertTrue(evaluar);
		NamedEvent event = new NamedEvent("eventHello");
		evaluar = automaton.evaluate(event);		
		assertTrue(evaluar);
		evaluar = automaton.evaluate(event);
		assertTrue(evaluar);		
		evaluar = automaton.evaluate(anyEvent);
		assertFalse(evaluar);		
	}
	
	//@Test
	public void testComplex() {
		TestBuchiComposed automaton = (TestBuchiComposed)TestBuchiComposed.getInstance();
		NamedEvent eventHello = new NamedEvent("eventHello");
		NamedEvent anotherEvent	 = new NamedEvent("anotherEvent");
		NamedEvent otherEvent = new NamedEvent("otherEvent");
		
		assertFalse(automaton.evaluate(eventHello));
		assertTrue(automaton.evaluate(otherEvent));
		assertTrue(automaton.evaluate(otherEvent));
		assertTrue(automaton.evaluate(otherEvent));
		assertFalse(automaton.evaluate(eventHello));
		
		automaton.destroy();
		automaton = (TestBuchiComposed)TestBuchiComposed.getInstance();
		assertTrue(automaton.evaluate(anotherEvent));
		assertTrue(automaton.evaluate(otherEvent));
		assertTrue(automaton.evaluate(eventHello));
		
		automaton.destroy();
		automaton = (TestBuchiComposed)TestBuchiComposed.getInstance();
		assertTrue(automaton.evaluate(otherEvent));
		assertFalse(automaton.evaluate(eventHello));
		
		automaton.destroy();
		automaton = (TestBuchiComposed)TestBuchiComposed.getInstance();
		assertTrue(automaton.evaluate(otherEvent));
		assertTrue(automaton.evaluate(otherEvent));
		assertTrue(automaton.evaluate(anotherEvent));
		assertTrue(automaton.evaluate(eventHello));
		assertTrue(automaton.evaluate(otherEvent));
		
	}
	
	@Test
	public void test() {
		UntilExample automaton = (UntilExample)UntilExample.getInstance();
		NamedEvent initServerHello = new NamedEvent("initServer");
		NamedEvent initClientEvent	 = new NamedEvent("initClient");
		NamedEvent outEvent = new NamedEvent("out");
		
		assertTrue(automaton.evaluate(initServerHello));
		assertTrue(automaton.evaluate(initClientEvent));
		assertTrue(automaton.evaluate(initServerHello));
		assertTrue(automaton.evaluate(initClientEvent));
		assertTrue(automaton.evaluate(initClientEvent));
		assertTrue(automaton.evaluate(initServerHello));
		assertTrue(automaton.evaluate(outEvent));
		assertTrue(automaton.evaluate(initClientEvent));
		assertTrue(automaton.evaluate(outEvent));
		assertFalse(automaton.evaluate(initServerHello));
		
		automaton.destroy();
		automaton = (UntilExample)UntilExample.getInstance();
		assertTrue(automaton.evaluate(outEvent));
		assertTrue(automaton.evaluate(outEvent));
		assertTrue(automaton.evaluate(outEvent));
		assertFalse(automaton.evaluate(initServerHello));
	}

}