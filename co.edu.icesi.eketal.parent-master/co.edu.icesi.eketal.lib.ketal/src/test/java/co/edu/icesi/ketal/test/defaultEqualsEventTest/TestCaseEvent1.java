package co.edu.icesi.ketal.test.defaultEqualsEventTest;

import java.util.Vector;

import org.junit.Assert;
import org.junit.Test;

import co.edu.icesi.ketal.core.Automaton;
import co.edu.icesi.ketal.core.CausalEqualsExpression;
import co.edu.icesi.ketal.core.DefaultEqualsExpression;
import co.edu.icesi.ketal.core.Event;


/**
 * DefaultEqualsExpression Test. 
 * 1. Create the automaton.
 * 2. Create a Match, expression with a character.
 * 3. Set the Regular Expression, to set specific language to recognize.
 * 4. Create the vector of events.
 * 5. Determine if the vector of events is recognized by automaton.
 */
public class TestCaseEvent1 {
	
	@Test
	public void testCase() {
	
		//First of All, we must create an automaton, there are many ways to do that.
		// One of this is using the empty constructor.
		Automaton my= new Automaton();
		
		//We map each expression to an specific character.
		my.mapExpressionToAlphabet(new DefaultEqualsExpression(new TestEvent1('a')), new Character('A'));
		my.mapExpressionToAlphabet(new DefaultEqualsExpression(new TestEvent1('b')), new Character('B'));
		my.mapExpressionToAlphabet(new DefaultEqualsExpression(new TestEvent1('c')), new Character('C'));
		
		//Set the Regular Expression, to set specific language to recognize.
		my.setRegularExpression("ABB|CAAB");
		
		//Create the vector of events.
		Vector<Event> vector = new Vector<Event>();
		vector.add(new TestEvent1('a'));
		vector.add(new TestEvent1('b'));
		vector.add(new TestEvent1('b'));
		
		//In this test, the automaton must recognize the sequence of events.
		Assert.assertTrue(my.isAWordAutomaton(vector));
		
	}
	
	@Test
	public void testCaseFalse()
	{
		Automaton my= new Automaton();
		my.mapExpressionToAlphabet(new DefaultEqualsExpression(new TestEvent1('a')), new Character('A'));
		my.mapExpressionToAlphabet(new DefaultEqualsExpression(new TestEvent1('b')), new Character('B'));
		my.mapExpressionToAlphabet(new DefaultEqualsExpression(new TestEvent1('c')), new Character('C'));
		
		my.setRegularExpression("ABB|CAAB");
		
		
		Vector<Event> vector = new Vector<Event>();
		vector.add(new TestEvent1('b'));
		vector.add(new TestEvent1('b'));
		vector.add(new TestEvent1('b'));
		
		//In this Test, the automaton do not recognize the sequence of events
		Assert.assertFalse(my.isAWordAutomaton(vector));
	}
}
