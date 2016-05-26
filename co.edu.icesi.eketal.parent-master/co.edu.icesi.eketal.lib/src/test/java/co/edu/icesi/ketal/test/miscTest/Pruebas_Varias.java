package co.edu.icesi.ketal.test.miscTest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Set;
import co.edu.icesi.ketal.*;
import co.edu.icesi.ketal.core.And;
import co.edu.icesi.ketal.core.Automaton;
import co.edu.icesi.ketal.core.CausalEqualsExpression;
import co.edu.icesi.ketal.core.DefaultEqualsExpression;
import co.edu.icesi.ketal.core.Expression;
import co.edu.icesi.ketal.core.OriginatingHost;
import co.edu.icesi.ketal.core.State;
import co.edu.icesi.ketal.core.Transition;

public class Pruebas_Varias {

	public static void main(String[] args)
	{
		/*Character[] chars = {new Character('a'),new Character('b'),new Character('c')};
		String regexp = "abc";
		for(int cont=0; cont<chars.length; cont++)
		{
			Character result = (Character) chars[cont];
			System.out.println(Character.getNumericValue(result.charValue()));
			System.out.println(regexp.indexOf(result.charValue()));
			if (regexp.indexOf(result.charValue())==-1)
			{
				try
				{
				throw new KetalException("Elements of the Regular Expression doesnt match with the Mapped Expressions");
				}
				catch(Exception e)
				{e.getMessage();}
			}
	}*/
	
/*	
	//Se crean los Estados del aut—mata 
	dk.brics.automaton.State q0 = new dk.brics.automaton.State();
	dk.brics.automaton.State q1 = new dk.brics.automaton.State();
	dk.brics.automaton.State q2 = new dk.brics.automaton.State();
	
	//A -> On, B -> Push, C -> Pop, D -> Off
	q0.addTransition(new dk.brics.automaton.Transition('A',q1));
	q1.addTransition(new dk.brics.automaton.Transition('B',q2));
	q2.addTransition(new dk.brics.automaton.Transition('C',q1));
	q1.addTransition(new dk.brics.automaton.Transition('D',q0));

	//Mapea las Expresiones a los caracteres, usados para las transiciones	
	Hashtable<Expression,Character> expressions = new Hashtable<Expression, Character>();
	expressions.put(new CausalEqualsExpression(new StackEvent("On")),'A');
	expressions.put(new CausalEqualsExpression(new StackEvent("Push")),'B');
	expressions.put(new CausalEqualsExpression(new StackEvent("Pop")),'C');
	expressions.put(new CausalEqualsExpression(new StackEvent("Off")),'D');
	
	State q0K = new State(q0);
	State q1K = new State(q1);
	State q2K = new State(q2);
	
	Set<State> states = new HashSet<State>();
	states.add(q0K);
	states.add(q1K);
	states.add(q2K);
	

	Automaton auto = new Automaton(states,q0K,null, expressions);

	auto.evaluate(new StackEvent("On"));
*/
		/*
	
	//Se crean los Estados del aut—mata 
		dk.brics.automaton.State q0 = new dk.brics.automaton.State();
		dk.brics.automaton.State q1 = new dk.brics.automaton.State();
		dk.brics.automaton.State q2 = new dk.brics.automaton.State();
		dk.brics.automaton.State q3 = new dk.brics.automaton.State();
		dk.brics.automaton.State q4 = new dk.brics.automaton.State();
		dk.brics.automaton.State q5 = new dk.brics.automaton.State();
		
		//A -> sendPrepare, B -> sendCommit, C -> aproveCommit, D -> abortCommit
		//E -> receivePrepare F -> receiveCommit&&originatingHost G -> CancelChanges 
		q0.addTransition(new dk.brics.automaton.Transition('A',q1));
		q1.addTransition(new dk.brics.automaton.Transition('B',q2));
		q2.addTransition(new dk.brics.automaton.Transition('C',q0));
		q2.addTransition(new dk.brics.automaton.Transition('D',q5));
		q0.addTransition(new dk.brics.automaton.Transition('E',q3));
		q3.addTransition(new dk.brics.automaton.Transition('F',q4));
		q5.addTransition(new dk.brics.automaton.Transition('G',q0));

		//Mapea las Expresiones a los caracteres, usados para las transiciones	
		Hashtable<Expression,Character> expressions = new Hashtable<Expression, Character>();
		expressions.put(new DefaultEqualsExpression(
				new TransactionEvent("sendPrepare")),'A');
		expressions.put(new CausalEqualsExpression(
				new TransactionEvent("sendCommit")),'B');
		expressions.put(new CausalEqualsExpression(
				new TransactionEvent("aproveCommit")),'C');
		expressions.put(new CausalEqualsExpression(
				new TransactionEvent("abortCommit")),'D');
		expressions.put(new DefaultEqualsExpression(
				new TransactionEvent("receivePrepare")),'E');
		expressions.put(new CausalEqualsExpression(
				new TransactionEvent("receiveCommit")),'F');
		expressions.put(new And(new CausalEqualsExpression(
				new TransactionEvent("receiveCommit")), 
				new OriginatingHost(new DefaultEqualsExpression(new TransactionEvent("receiveCommit")))),'F');
		expressions.put(new CausalEqualsExpression(
				new TransactionEvent("cancelChanges")),'G');
		
		State q0K = new State(q0);
		State q1K = new State(q1);
		State q2K = new State(q2);
		State q3K = new State(q3);
		State q4K = new State(q4);
		State q5K = new State(q5);
		
		
		Set<State> states = new HashSet<State>();
		states.add(q0K);
		states.add(q1K);
		states.add(q2K);
		states.add(q3K);
		states.add(q4K);
		states.add(q5K);

		Automaton transactionAutomaton = new Automaton(states,q0K,null, expressions);

	
	*/
/*
		dk.brics.automaton.Automaton automaton = new dk.brics.automaton.Automaton();
		
		//Se crean los Estados del aut—mata 
		dk.brics.automaton.State q0 = new dk.brics.automaton.State();
		dk.brics.automaton.State q1 = new dk.brics.automaton.State();
		dk.brics.automaton.State q2 = new dk.brics.automaton.State();
		
		//A -> On, B -> Push, C -> Pop, D -> Off
		q0.addTransition(new dk.brics.automaton.Transition('A',q1));
		q1.addTransition(new dk.brics.automaton.Transition('B',q2));
		q2.addTransition(new dk.brics.automaton.Transition('C',q1));
		q1.addTransition(new dk.brics.automaton.Transition('D',q0));
		
		automaton.setInitialState(q0);
		
		Scanner scan = new Scanner(System.in);
		char char1=' ';
		while(true)
		{
			char1 = scan.next().charAt(0);
			
			dk.brics.automaton.State temp=automaton.getInitialState().step(char1);
			
			if(temp != null)
			{
				automaton.setInitialState(temp);
			}
			
			System.out.println(temp);
			
			
			
		}
*/
		/*
		dk.brics.automaton.Automaton automaton = new dk.brics.automaton.Automaton();
		
		//Se crean los Estados del aut—mata 
		State q0 = new State();
		State q1 = new State();
		State q2 = new State();
		q0.setEventCauseThisState(new StackEvent("Kiyo"));
		q0.setAccept(true);
		q1.setEventCauseThisState(new StackEvent("Kiyo2"));
		//A -> On, B -> Push, C -> Pop, D -> Off
		q0.addTransition(new dk.brics.automaton.Transition('A',q1));
		q1.addTransition(new dk.brics.automaton.Transition('B',q2));
		q2.addTransition(new dk.brics.automaton.Transition('C',q1));
		q1.addTransition(new dk.brics.automaton.Transition('D',q0));
		
		automaton.setInitialState(q0);
		
		Scanner scan = new Scanner(System.in);
		char char1=' ';
		while(true)
		{
			char1 = scan.next().charAt(0);
			
			State temp=(State)automaton.getInitialState().step(char1);
			
			
			if(temp != null)
			{
				automaton.setInitialState(temp);
				System.out.println(temp);
				
				if(temp.getEventCauseThisState()!=null){
				System.out.println(temp.getEventCauseThisState().toString());
				}
			}
			
			
			System.out.println(automaton.run("ABCD"));
			
			
			
		}*/
		
}
}
