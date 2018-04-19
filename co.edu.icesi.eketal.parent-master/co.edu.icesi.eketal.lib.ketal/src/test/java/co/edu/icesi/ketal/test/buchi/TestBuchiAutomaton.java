package co.edu.icesi.ketal.test.buchi;

import static org.junit.Assert.*;

import org.junit.Test;

import co.edu.icesi.ketal.core.Automaton;
import co.edu.icesi.ketal.core.NamedEvent;

public class TestBuchiAutomaton {

	@Test
	public void test() {
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

}