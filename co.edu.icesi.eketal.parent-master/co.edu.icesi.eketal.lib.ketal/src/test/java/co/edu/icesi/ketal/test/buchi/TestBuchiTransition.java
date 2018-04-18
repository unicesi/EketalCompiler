package co.edu.icesi.ketal.test.buchi;

import static org.junit.Assert.*;

import org.junit.Test;

import co.edu.icesi.ketal.core.BuchiTransition;
import co.edu.icesi.ketal.core.NamedEvent;
import co.edu.icesi.ketal.core.DefaultEqualsExpression;
import co.edu.icesi.ketal.core.Expression;
import co.edu.icesi.ketal.core.NotExpression;
import co.edu.icesi.ketal.core.Or;
import co.edu.icesi.ketal.core.State;
import co.edu.icesi.ketal.core.Transition;

public class TestBuchiTransition {

	@Test
	public void testEvent() {
		//Evento a
		NamedEvent event = new NamedEvent("a");
		Expression expression = new DefaultEqualsExpression(event);
		Transition transition = new BuchiTransition(new State(), new State(), 'A', expression);
		
		NamedEvent eventFalse = new NamedEvent("b");
		assertFalse(transition.evaluateExpression(eventFalse));
		NamedEvent eventTrue = new NamedEvent("a");
		assertTrue(transition.evaluateExpression(eventTrue));
		
	}
	
	@Test
	public void testEventNot() {
		//Evento !a
		NamedEvent event = new NamedEvent("a");
		Expression expression = new NotExpression(new DefaultEqualsExpression(event));
		Transition transition = new BuchiTransition(new State(), new State(), 'A', expression);
		
		NamedEvent eventFalse = new NamedEvent("a");
		assertFalse(transition.evaluateExpression(eventFalse));
		NamedEvent eventTrue = new NamedEvent("b");
		assertTrue(transition.evaluateExpression(eventTrue));
	}
	
	@Test
	public void testEventOr() {
		//Evento a||b
		NamedEvent eventA = new NamedEvent("a");
		NamedEvent eventB = new NamedEvent("b");
		Expression expression = new Or(new DefaultEqualsExpression(eventA), new DefaultEqualsExpression(eventB));
		Transition transition = new BuchiTransition(new State(), new State(), 'A', expression);
		
		NamedEvent eventTrue = new NamedEvent("a");
		assertTrue(transition.evaluateExpression(eventTrue));
		eventTrue = new NamedEvent("b");
		assertTrue(transition.evaluateExpression(eventTrue));
		NamedEvent eventFalse = new NamedEvent("c");
		assertFalse(transition.evaluateExpression(eventFalse));
	}
	
	@Test
	public void testEventOrNot() {
		//Evento a||!b
		NamedEvent eventA = new NamedEvent("a");
		NamedEvent eventB = new NamedEvent("b");
		Expression expression = new Or(new DefaultEqualsExpression(eventA), new NotExpression(new DefaultEqualsExpression(eventB)));
		Transition transition = new BuchiTransition(new State(), new State(), 'A', expression);
		
		NamedEvent eventTrue = new NamedEvent("a");
		assertTrue(transition.evaluateExpression(eventTrue));
		eventTrue = new NamedEvent("c");
		assertTrue(transition.evaluateExpression(eventTrue));
		NamedEvent eventFalse = new NamedEvent("b");
		assertFalse(transition.evaluateExpression(eventFalse));
	}
	
	@Test
	public void testEventAndNot() {
		//Evento a&&!b
		NamedEvent eventA = new NamedEvent("a");
		NamedEvent eventB = new NamedEvent("b");
		Expression expression = new Or(new DefaultEqualsExpression(eventA), new DefaultEqualsExpression(eventB));
		Transition transition = new BuchiTransition(new State(), new State(), 'A', expression);
		
		NamedEvent eventTrue = new NamedEvent("a");
		assertTrue(transition.evaluateExpression(eventTrue));
		NamedEvent eventFalse = new NamedEvent("c");
		assertFalse(transition.evaluateExpression(eventFalse));
	}

}
