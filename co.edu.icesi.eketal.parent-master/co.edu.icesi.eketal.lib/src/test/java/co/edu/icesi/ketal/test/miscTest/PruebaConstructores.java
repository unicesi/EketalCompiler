package co.edu.icesi.ketal.test.miscTest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Set;

import org.jgroups.protocols.TransportedVectorTime;

import co.edu.icesi.ketal.*;
import co.edu.icesi.ketal.core.And;
import co.edu.icesi.ketal.core.Automaton;
import co.edu.icesi.ketal.core.CausalEqualsExpression;
import co.edu.icesi.ketal.core.DefaultEqualsExpression;
import co.edu.icesi.ketal.core.Event;
import co.edu.icesi.ketal.core.Expression;
import co.edu.icesi.ketal.core.OriginatingHost;
import co.edu.icesi.ketal.core.State;
import co.edu.icesi.ketal.core.Transition;


public class PruebaConstructores {
	public static void main(String[] args)
	{
		crarAutomataCausal();	
	}
	
	public static void crearAutomataUsandoExpressiones(){
		
		Hashtable<Expression, Character> hash = new Hashtable<Expression, Character>();
		hash.put(new DefaultEqualsExpression(new StackEvent("A")), 'A');
		hash.put(new DefaultEqualsExpression(new StackEvent("B")), 'B');
		hash.put(new DefaultEqualsExpression(new StackEvent("C")), 'C');
		hash.put(new DefaultEqualsExpression(new StackEvent("D")), 'D');
		hash.put(new DefaultEqualsExpression(new StackEvent("E")), 'E');	
		
		//First Constructor without Expressions map
		Automaton auto= new Automaton("ABCDE");
		
		auto.mapExpressionToAlphabet(new DefaultEqualsExpression(new StackEvent("A")), 'A');
		auto.mapExpressionToAlphabet(new DefaultEqualsExpression(new StackEvent("B")), 'B');
		auto.mapExpressionToAlphabet(new DefaultEqualsExpression(new StackEvent("C")), 'C');
		auto.mapExpressionToAlphabet(new DefaultEqualsExpression(new StackEvent("D")), 'D');
		auto.mapExpressionToAlphabet(new DefaultEqualsExpression(new StackEvent("E")), 'E');
		
		auto.initializeAutomaton();
		
		//Second Constructor with Expressions map
		//Automaton auto= new Automaton("ABCDE", hash);
		Scanner scan = new Scanner(System.in);
		
		while(true)
		{
			
			
			if(auto.step(scan.next().charAt(0)))
			{
				System.out.println("Cambio exitoso");
			}
			System.out.println(auto.getCurrentState().getState());
		}
		
	}
	
	public static void crearAutomataUsandoTransiciones()
	{
		//Se crean los estados
				State q0 = new State();
				State q1 = new State();
				State q2 = new State();
				State q3 = new State();
				State q4 = new State();
						
				//Se crean las transiciones
				Transition tran1 = new Transition(q0, q1, new Character('A'));
				Transition tran2 = new Transition(q1, q2, new Character('B'));
				Transition tran3 = new Transition(q2, q3, new Character('C'));
				Transition tran4 = new Transition(q3, q4, new Character('D'));
				Transition tran5 = new Transition(q4, q0, new Character('E'));
				
				
				//Este ser’a la colecci—n que se pasar’a por par‡metro.
				Set<Transition> sets = new HashSet<Transition>();
				sets.add(tran1);
				sets.add(tran2);
				sets.add(tran3);
				sets.add(tran4);
				sets.add(tran5);
				
				Hashtable<Expression, Character> hash = new Hashtable<Expression, Character>();
				hash.put(new DefaultEqualsExpression(new StackEvent("A")), 'A');
				hash.put(new DefaultEqualsExpression(new StackEvent("B")), 'B');
				hash.put(new DefaultEqualsExpression(new StackEvent("C")), 'C');
				hash.put(new DefaultEqualsExpression(new StackEvent("D")), 'D');
				hash.put(new DefaultEqualsExpression(new StackEvent("E")), 'E');	
				
				//First Constructor without Expressions map
				Automaton auto= new Automaton(sets, q0, new HashSet<State>());
				
				auto.mapExpressionToAlphabet(new DefaultEqualsExpression(new StackEvent("A")), 'A');
				auto.mapExpressionToAlphabet(new DefaultEqualsExpression(new StackEvent("B")), 'B');
				auto.mapExpressionToAlphabet(new DefaultEqualsExpression(new StackEvent("C")), 'C');
				auto.mapExpressionToAlphabet(new DefaultEqualsExpression(new StackEvent("D")), 'D');
				auto.mapExpressionToAlphabet(new DefaultEqualsExpression(new StackEvent("E")), 'E');
				
				auto.initializeAutomaton();
				
				//Second Constructor with Expressions map
				//Automaton auto= new Automaton(sets, q0, new HashSet<State>(), hash);
				
				Scanner scan = new Scanner(System.in);
				
				while(true)
				{
					
					
					if(auto.step(scan.next().charAt(0)))
					{
						System.out.println("Cambio exitoso");
					}
					System.out.println(auto.getCurrentState().getState());
				}
	}
	
	public static void crearAutomataExpresionEquals()
	{
		//Se crean los estados
		State q0 = new State();
		State q1 = new State();
		State q2 = new State();
		State q3 = new State();
		State q4 = new State();
				
		//Se crean las transiciones
		Transition tran1 = new Transition(q0, q1, new Character('A'));
		Transition tran2 = new Transition(q1, q2, new Character('B'));
		Transition tran3 = new Transition(q2, q3, new Character('C'));
		Transition tran4 = new Transition(q3, q4, new Character('D'));
		Transition tran5 = new Transition(q4, q0, new Character('E'));
		
		
		//Este ser’a la colecci—n que se pasar’a por par‡metro.
		Set<Transition> sets = new HashSet<Transition>();
		sets.add(tran1);
		sets.add(tran2);
		sets.add(tran3);
		sets.add(tran4);
		sets.add(tran5);
		
		Hashtable<Expression, Character> hash = new Hashtable<Expression, Character>();
		hash.put(new DefaultEqualsExpression(new StackEvent("A")), 'A');
		hash.put(new DefaultEqualsExpression(new StackEvent("B")), 'B');
		hash.put(new DefaultEqualsExpression(new StackEvent("C")), 'C');
		hash.put(new DefaultEqualsExpression(new StackEvent("D")), 'D');
		hash.put(new DefaultEqualsExpression(new StackEvent("E")), 'E');	
				
		//First Constructor without Expressions map
		/*Automaton auto= new Automaton(sets, q0, new HashSet<State>());
		
		auto.mapExpressionToAlphabet(new DefaultEqualsExpression(new StackEvent("A")), 'A');
		auto.mapExpressionToAlphabet(new DefaultEqualsExpression(new StackEvent("B")), 'B');
		auto.mapExpressionToAlphabet(new DefaultEqualsExpression(new StackEvent("C")), 'C');
		auto.mapExpressionToAlphabet(new DefaultEqualsExpression(new StackEvent("D")), 'D');
		auto.mapExpressionToAlphabet(new DefaultEqualsExpression(new StackEvent("E")), 'E');
		
		auto.initializeAutomaton();
		*/
		
		//Second Constructor with Expressions map
		Automaton auto= new Automaton("ABCDE", hash);
		
		//Third Constructor with Expressions map
		//Automaton auto= new Automaton(sets, q0, new HashSet<State>(), hash);
		
		//First example consuming this Events
		Event event1 = new StackEvent("A");
		Event event2 = new StackEvent("B");
		Event event3 = new StackEvent("C");
		Event event4 = new StackEvent("D");
		Event event5 = new StackEvent("E");
		
		auto.step(event1);
		System.out.println(auto.getCurrentState().getState());
		auto.step(event2);
		System.out.println(auto.getCurrentState().getState());
		auto.step(event3);
		System.out.println(auto.getCurrentState().getState());
		auto.step(event4);
		System.out.println(auto.getCurrentState().getState());
		auto.step(event5);
		System.out.println(auto.getCurrentState().getState());
	}
	
	public static void crearAutomataErradoPorMalMap()
	{

		//Se crean los estados
		State q0 = new State();
		State q1 = new State();
		State q2 = new State();
		State q3 = new State();
		State q4 = new State();
		
		//Se crean las transiciones
		Transition tran1 = new Transition(q0, q1, new Character('A'));
		Transition tran2 = new Transition(q1, q2, new Character('B'));
		Transition tran3 = new Transition(q2, q3, new Character('C'));
		Transition tran4 = new Transition(q3, q4, new Character('D'));
		Transition tran5 = new Transition(q3, q4, new Character('E'));
		
		
		//Este ser’a la colecci—n que se pasar’a por par‡metro.
		Set<Transition> sets = new HashSet<Transition>();
		sets.add(tran1);
		sets.add(tran2);
		sets.add(tran3);
		sets.add(tran4);
		sets.add(tran5);
		
		Hashtable<Expression, Character> hash = new Hashtable<Expression, Character>();
		hash.put(new DefaultEqualsExpression(new StackEvent("A")), 'A');
		hash.put(new DefaultEqualsExpression(new StackEvent("B")), 'B');
		hash.put(new DefaultEqualsExpression(new StackEvent("C")), 'C');
		hash.put(new DefaultEqualsExpression(new StackEvent("D")), 'D');
		//Here is the error, Automata needs a bijective function or Map.
		hash.put(new DefaultEqualsExpression(new StackEvent("E")), 'D');
			
		Automaton auto= new Automaton("ABCD",hash);
		//Automaton auto= new Automaton(sets, q0, new HashSet<State>(), hash);
	}
	
	public void crearAutomataParaEjemploStack()
	{
		//Se crean los Estados del aut—mata 
		State q0 = new State();
		State q1 = new State();
		State q2 = new State();
		
		//A -> On, B -> Push, C -> Pop, D -> Off
		Transition t1 = new Transition(q0, q1, 'A');
		Transition t2 = new Transition(q1, q2, 'B');
		Transition t3 = new Transition(q2, q1, 'C');
		Transition t4 = new Transition(q1, q0, 'D');

		//Mapea las Expresiones a los caracteres, usados para las transiciones	
		Hashtable<Expression,Character> expressions = new Hashtable<Expression, Character>();
		expressions.put(new CausalEqualsExpression(new StackEvent("On")),'A');
		expressions.put(new CausalEqualsExpression(new StackEvent("Push")),'B');
		expressions.put(new CausalEqualsExpression(new StackEvent("Pop")),'C');
		expressions.put(new CausalEqualsExpression(new StackEvent("Off")),'D');
		
		Set<Transition> transitions = new HashSet<Transition>();
		transitions.add(t1);
		transitions.add(t2);
		transitions.add(t3);
		transitions.add(t4);
		

		Automaton auto = new Automaton(transitions,q0,null,expressions);
	}
	
	public void crearAutomataParaEjemploTransaccional()
	{
		//Se crean los Estados del aut—mata 
		State q0 = new State();
		State q1 = new State();
		State q2 = new State();
		State q3 = new State();
		State q4 = new State();
		State q5 = new State();
				
		//A -> sendPrepare, B -> sendCommit, C -> aproveCommit, D -> abortCommit
		//E -> receivePrepare F -> receiveCommit&&originatingHost G -> CancelChanges 
		Transition t1 = new Transition(q0,q1,'A');
		Transition t2 = new Transition(q1,q2,'B');
		Transition t3 = new Transition(q2,q0,'C');
		Transition t4 = new Transition(q2,q5,'D');
		Transition t5 = new Transition(q0,q3,'E');
		Transition t6 = new Transition(q3,q4,'F');
		Transition t7 = new Transition(q5,q0,'E');
		
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
						
		Set<Transition> transitions = new HashSet<Transition>();
		transitions.add(t1);
		transitions.add(t2);
		transitions.add(t3);
		transitions.add(t4);
		transitions.add(t5);
		transitions.add(t6);
		transitions.add(t7);
		
		Automaton transactionAutomaton = new Automaton(transitions,q0,null, expressions);

	}

	public static void crarAutomataCausal(){
		//Se crean los Estados del aut—mata 
				State q0 = new State();
				State q1 = new State();
				State q2 = new State();
				
				//A -> On, B -> Push, C -> Pop, D -> Off
				Transition t1 = new Transition(q0, q1, 'A');
				Transition t2 = new Transition(q1, q2, 'B');
				Transition t3 = new Transition(q2, q1, 'C');
				Transition t4 = new Transition(q1, q0, 'D');

				//Mapea las Expresiones a los caracteres, usados para las transiciones	
				Hashtable<Expression,Character> expressions = new Hashtable<Expression, Character>();
				expressions.put(new CausalEqualsExpression(new StackEvent("On")),'A');
				expressions.put(new CausalEqualsExpression(new StackEvent("Push")),'B');
				expressions.put(new CausalEqualsExpression(new StackEvent("Pop")),'C');
				expressions.put(new CausalEqualsExpression(new StackEvent("Off")),'D');
				
				Set<Transition> transitions = new HashSet<Transition>();
				transitions.add(t1);
				transitions.add(t2);
				transitions.add(t3);
				transitions.add(t4);
				

				Automaton auto = new Automaton(transitions,q0,null,expressions);
				
				StackEvent event1 = new StackEvent("On");
				int [] array = {1,0,0};
				TransportedVectorTime tvt = new TransportedVectorTime(0,array);
				event1.setTransportedVectorTime(tvt);
				
				auto.step(event1);
				
				System.out.println(auto.getCurrentState().getState());
				
				StackEvent event2 = new StackEvent("Push");
				int [] array2 = {2,0,0};
				TransportedVectorTime tvt2 = new TransportedVectorTime(0,array2);
				event2.setTransportedVectorTime(tvt2);
				
				auto.step(event2);
				
				System.out.println(auto.getCurrentState().getState());
				
				StackEvent event3 = new StackEvent("Pop");
				int [] array3 = {3,0,0};
				TransportedVectorTime tvt3 = new TransportedVectorTime(0,array3);
				event2.setTransportedVectorTime(tvt3);
				
				auto.step(event3);
				
				System.out.println(auto.getCurrentState().getState());
				
				StackEvent event4 = new StackEvent("Pop");
				int [] array4 = {4,0,0};
				TransportedVectorTime tvt4 = new TransportedVectorTime(0,array4);
				event2.setTransportedVectorTime(tvt4);
				
				auto.step(event4);
				
				System.out.println(auto.getCurrentState().getState());
				
	}
}


/*
 * //Algoritmo para crear transiciones.
		/*
		Set<State> alreadyStates = new HashSet<State>();
		State anState = new State();
		State nextState = new State();
		anState.setState(new dk.brics.automaton.State());
		alreadyStates.add(anState);
						
		for(Transition a: sets)
		{
			anState= a.getBegin();
			nextState = a.getEnd();
			
			if(!alreadyStates.contains(anState))
			{
				anState.setState(new dk.brics.automaton.State());
				alreadyStates.add(anState);
			}
			if(!alreadyStates.contains(nextState))
			{
				nextState.setState(new dk.brics.automaton.State());
				alreadyStates.add(nextState);
			}
			
			anState.getState().addTransition(new dk.brics.automaton.Transition(a.getCharacter(), nextState.getState()));
		}
		*/



