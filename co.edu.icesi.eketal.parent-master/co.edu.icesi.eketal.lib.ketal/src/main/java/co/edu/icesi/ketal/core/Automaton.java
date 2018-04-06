package co.edu.icesi.ketal.core;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import dk.brics.automaton.RegExp;

/**
 * Finite-State automaton, with recognition of Events mapped through a Character. 
 *
 */
public class Automaton {
	
	private dk.brics.automaton.Automaton automaton;
	private State begin;
	private State current;
	private Set<State> endStates;
	private Set<Transition> transitions;
	private Hashtable<Expression, Character> expressionsExp_Char;
	private Event incomingEvent;
	
	private Set<Transition> transitionsOfCurrentState;
	private Hashtable<Character, Expression> expressionsChar_Exp;
	
	/**
	 * Empty constructor. Permits to create an Automaton. 
	 */
	public Automaton(){
		
		automaton= new dk.brics.automaton.Automaton();		
		begin = null;
		current = null;
		endStates = new HashSet<State>();
		transitions= new HashSet<Transition>();
		expressionsExp_Char = new Hashtable<Expression,Character>();
		expressionsChar_Exp = new Hashtable<Character, Expression>();
	}

	/**
	 * Constructor. Permits to create an Automaton with a specific Regular Expression
	 * @param regexp. Regular Expression.
	 */
	public Automaton(String regexp){
		
		automatonByRegularExpression(regexp);
		expressionsChar_Exp = new Hashtable<Character, Expression>();
		expressionsExp_Char = new Hashtable<Expression, Character>();
		
	}

	/**
	 * Constructs an Automaton, with a Regular Expression, and a set of expressions.
	 * @param regexp: Regular Expression
	 * @param expressions: HashTable<Object, Character> of Expressions, that are matched with a character.
	 *  
	 */
	public Automaton(String regexp, Hashtable<Expression,Character> expressions){
		
		try{
		
		automatonByRegularExpression(regexp);
		
		Enumeration<Character> elements = expressions.elements();
		Character result = null;

		while (elements.hasMoreElements())
		{
			result = (Character) elements.nextElement();
			
			if (regexp.indexOf(result.charValue())==-1)
			{
				throw new KetalException("There are errors in the mapped Expressions");
			}
		}
		
		this.expressionsExp_Char = expressions;
		
		fillMapCharacterExpression();
		
		//The current Transitions set is updated.
		findTransitionsCurrentState();
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Constructs an Automaton, with specific params.
	 * @param transitions: Transitions, between states.
	 * @param begin: Start State.
	 * @param end: Set of end states.
	 */
	public Automaton(Set<Transition> transitions, State begin, Set<State> end){
		this.begin = begin;
		this.current = begin;
		this.endStates = end;
		this.transitions = transitions;
		
		this.automaton = new dk.brics.automaton.Automaton();
		this.automaton.setInitialState(begin.getState());
		
		expressionsChar_Exp = new Hashtable<Character, Expression>();
		expressionsExp_Char = new Hashtable<Expression, Character>();
		
	}
	
	/**
	 * Constructs an Automaton, with specific params.
	 * @param transitions: Transitions, between states.
	 * @param begin: Start State.
	 * @param end: Set of end states.
	 */
	public Automaton(Set<Transition> transitions, State begin, Set<State> end, Hashtable<Expression,Character> expressions){
	
		try{
		this.begin = begin;
		this.current = begin;
		this.endStates = end;
		this.transitions = transitions;
		this.expressionsExp_Char = expressions;
		
		this.automaton = new dk.brics.automaton.Automaton();
		this.automaton.setInitialState(begin.getState());
		
		fillMapCharacterExpression();
		
		//The current Transitions set is updated.
		findTransitionsCurrentState();
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * The map expressions is bijective function. 
	 * This method fills the other HashTable.
	 * 
	 * @throws KetalException in the case is not a bijective function.
	 */
	private void fillMapCharacterExpression() throws KetalException
	{
		expressionsChar_Exp =  new Hashtable<Character, Expression>();
		
		// First we get all the expressions
		Enumeration<Expression> keys = expressionsExp_Char.keys();
		Expression result = null;
		int numberOfSize=0;

		while (keys.hasMoreElements())
		{
			result = keys.nextElement();
			numberOfSize = expressionsChar_Exp.size();
			expressionsChar_Exp.put(expressionsExp_Char.get(result), result);
						
			if(numberOfSize>=expressionsChar_Exp.size())
			{
				throw new KetalException("There are errors in the mapped Expressions");
			}
			
		}
		
	}
	
	/**
	 * This Automata Facade change the estates and dynamically change the state
	 * and the transitions.
	 */
	private void findTransitionsCurrentState(){
		
		Iterator<Transition> it=transitions.iterator();
		Transition temp=null;
		
		transitionsOfCurrentState =  new HashSet<Transition>();
		
		/*
		if(transitionsOfCurrentState!=null)
		{
		transitionsOfCurrentState.clear();
		
		}else{
			transitionsOfCurrentState =  new HashSet<Transition>();
		}*/
		
		while(it.hasNext()){
			
			temp = (Transition)it.next();
			
			if(temp.getBegin().equals(current)){
				temp.setExpression(getExpressionMapCharacter(temp.getCharacter()));
				transitionsOfCurrentState.add(temp);
				
			}
			
		}		
		
	}
		
	/**
	 * Pruebaaaa
	 * TODO Es una prueba para hacer el STEP.
	 * 
	 */
	public boolean step(Character c)
	{
		State temp = new State(current.getState().step(c));
		
		if(temp.getState()!=null)
		{
			current=temp;
			return true;
		}
		return false;
	}
	
	/**
	 * TODO Step que reciba un event, Se est� Trabajando esto.
	 * @param eve
	 * @return
	 */
	public boolean step(Event eve)
	{
		
		Iterator<Transition> itTrans = transitionsOfCurrentState.iterator();
		
		Transition transition=null;
		while(itTrans.hasNext())
		{
			transition=itTrans.next();
			
			//The first Transition that is found and is true to do the perform the transition, we choose that.
			
			//This is the initial Transition if we no have events to compare.
			//if(!transition.evaluateExpression(eve) && current.getEventCauseThisState() == null)
			if(current.getEventCauseThisState() == null)
			{				
				if( perform(eve, transition.getCharacter()))
				{
					return true;
				}
			}
			
			//The case the transition have the events to compare or could be.
			if(transition.evaluateExpression(eve))
			{	
				if( perform(eve, transition.getCharacter()))
				{
					return true;
				}
				
			}else{
				
				
				if(transition.getExpression().getEvent().equals(eve))
				{
					Expression exp = transition.getExpression();
					exp.setEvent(eve);
					transition.setExpression(exp);
					
					if(incomingEvent != null){
						if(transition.evaluateExpression(incomingEvent))
						{	
							if (perform(eve, transition.getCharacter()))
							{
								return true;
							}
							
						}
					}
					
					if(current.getEventCauseThisState() != null){
						if(transition.evaluateExpression(current.getEventCauseThisState()))
						{	
							if( perform(eve, transition.getCharacter()))
							{
								return true;
							}
							
						}
					}
					
				}
			}
			
		}
		
		incomingEvent = eve;
		return false;
	}	
	
	/**
	 * Get the State attribute begin
	 * @return Start State of the automaton.
	 */
	public State getBegin() {
		return begin;
	}

	/**
	 * Get the Set of end states
	 * @return set of end States.
	 */
	public Set<State> getEndStates() {
		return endStates;
	}

	/**
	 * Get the set of transitions
	 * @return set of Transitions between states.
	 */
	public Set<Transition> getTransitions() {
		//return transitions;
		return transitionsOfCurrentState;
	}

	/**
	 * Get the current state of the automaton
	 * @return Current state of the automaton.
	 */
	public State getCurrentState(){
		return this.current;
	}
	
	/**
	 * Look for an Expression in the HashTable and get its character
	 * @param exp Expression to be searched in the HashTable
	 * @return Character mapped to a specific Expression.
	 */
	public Character getCharacterMapExpression(Expression exp){
		
		if(exp !=null){
	
			//Search in the HashTable Expressions if the Expression exp exists.
			Character c = (Character) expressionsExp_Char.get(exp);
			
			if (c != null){
				return c.charValue();
			}
		}
		return '-';
	}
	
	/**
	 * Look for an Expression in the HashTable and get its character
	 * @param exp Expression to be searched in the HashTable
	 * @return Character mapped to a specific Expression.
	 */
	private Expression getExpressionMapCharacter(Character cha)
	{
		if(cha !=null){
			
			//Search in the HashTable the Character mapped with a Expression
			Expression exp = (Expression) expressionsChar_Exp.get(cha);
			if (exp != null){
				return exp;
			}
		}
		return null;
		
	}

	/**
	 * Get the HashTable with the mapping of expressions and characters
	 * @return HashTable mapping expressions with characters
	 */
	public Hashtable<Expression,Character> getMapAlphabet()
	{
		return expressionsExp_Char;
	}

	/**
	 * Set a regular expression of this automaton.
	 * @param regexp Regular expression that will define the language recognized by this automaton
	 */
	public void setRegularExpression(String regexp)
	{
		automatonByRegularExpression(regexp);
	}

	/**
	 * Establish if the current state, has a transition to other, consuming the param.
	 * @param c char to be performed
	 * @return boolean if the perform was successful
	 * 
	 * 
	 * Modified by Oscar Garc�s dic-2012
	 */
	public boolean perform(Event event, char c)
	{
		if (current != null)
		{
			if (current.getState() != null)
			{
				State temp = new State(current.getState().step(c));
				//TODO this is a soft solution to a basic problem. No resolved.
				// When a dk.brics.State step (C) the instance change.
				
				if (temp.getState()!= null)
				{
					current.setState(temp.getState());
					current.setEventCauseThisState(event);
					findTransitionsCurrentState();
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Evaluate, if an Event is already mapped.
	 * @param event to be evaluated
	 * @return True: Event has mapped. False other case.
	 */
	public boolean evaluate(Event event){
	
		// Get the Expression mapped with the given event
		Expression exp = getExpression(event);
		
		if (exp !=null){
			
			try{
				if(exp.evaluate(event))
				{
					// Get the character mapped with the given expression
					char c = getCharacterMapExpression(exp);
					if (c != '-'){				
						return perform(event,c);
					}	
				}
			}
			catch(Exception ex){
				
			//int index	
				if(incomingEvent!=null && current.getEventCauseThisState() != null)
				{
					
				}
				
				if(incomingEvent!=null){
				Expression temp = exp;
				temp.setEvent(incomingEvent);
				
				if(exp.evaluate(event))
				{
					// Get the character mapped with the given expression
					char c = getCharacterMapExpression(exp);
					if (c != '-'){				
						return perform(event,c);
					}	
				}
				}else{
					incomingEvent = event;
				}
				
			}
		}		
		return false;
	}
	
	/**
	 * Check if the given event is present in an expression of any transition in the automaton
	 * This shall be removed
	 * @param event to be checked
	 * @return boolean true if there is some transition that has it, false in other case
	 */
	private boolean checkEvent(Event event){
		
		State current = getCurrentState();
		//Obtain the transitions.
		Iterator<Transition> i = getTransitions().iterator();
		Transition t;
		
		//Evaluate each Transition
		// If the event is found in an Expression, the algorithm returns true, in other case False.
		while (i.hasNext()){
			t = i.next();
			if (t.getBegin().equals(current)){
				if (t.getExpression().evaluate(event)){
					return true;
				}
			}
		}
		
		return false;
		
	}
	
	/**
	 * Association between an Expression and a Character.
	 * @param exp Expression to be mapped with the character
	 * @param symbol Character to be mapped with the expression
	 * @return boolean indicates if the mapping was successful
	 */
	public boolean mapExpressionToAlphabet(Expression exp, Character symbol)
	{	
		int size = expressionsExp_Char.size();
		int size2= expressionsChar_Exp.size();
		expressionsExp_Char.put(exp, symbol);
		expressionsChar_Exp.put(symbol, exp);

		if ((expressionsExp_Char.size() > size)&&(expressionsChar_Exp.size() > size2))
		{
			return true;
		}
		else
		{
			return false;
		}

	}
	
	public void initializeAutomaton(){
		findTransitionsCurrentState();
	}
	
	/**
	 * Looks for an expression associated with the event
	 * @param ev Event to be evaluated with the expressions of the automaton
	 * @return Expression associated with the given event, null if not found
	 */
	public Expression getExpression(Event ev) {
		
		// First we get all the expressions used in the transitions of the automaton
		Enumeration<Expression> keys = expressionsExp_Char.keys();
		Expression result = null;
		boolean done = false;

		// Iterates until there is no more expressions to evaluate or the variable done is true
		while (keys.hasMoreElements() && !done)
		{
			result = (Expression) keys.nextElement();
			
			if (result.evaluate(ev))
			{
				// If the evaluation is successful, we change the value of the variable and finish the cycle
				done = true;
			}

		}
		
		// The return depends on the done variable value
		if (done == false)
		{
			return null;
		}
		else
		{
			return result;
		}
	}
	
	/**
	 * Main method, using the Vector<Event> can define if the sequence of Events are recognized by the Automaton.
	 * @param word Vector of Events to be evaluated
	 * @return boolean
	 */
	public boolean isAWordAutomaton(Vector<Event> word)
	{
		
		boolean over=false;
		//We create a string to hold the mapped character for each event
		//thus we can use an external library that supports regular
		//expressions like dk.brics.automaton
			String myWord= "";
			Event eventWord=null;
			Character charEvent=new Character('-');
			
			
			for(int index=0; (word.size()>index)&&!over; index++)
			{
				eventWord=word.get(index);
				
				//In this code, first of all, get the expression that have a Event, then obtains the characther mapped to that Expression
				charEvent= getCharacterMapExpression(getExpression((eventWord)));
				if(charEvent=='-')
				{
					over=true;
					return false;
				}
				else
				{
					//Concat the character found into a String
					myWord=myWord+(charEvent.toString());
				}
			}
			
			//Define if the sequence of characteres (mapped of Events) is accepted by 
			//this automaton.
			return automaton.run(myWord);
	}
	
	/**
	 * Method used to create an automaton and initialize the attributes with the given regular expression
	 * @param regexp to be used to create the automaton
	 */
	private void automatonByRegularExpression(String regexp)
	{
		//First we create an dk.brics.automaton.Automaton with a specific regular expression
		automaton = new RegExp(regexp).toAutomaton();
		
		//Then we remove dead transitions
		automaton.removeDeadTransitions();
		if (!automaton.isDeterministic()){
			automaton.determinize();
		}
		
		//We map the begin and current State attributes with the initial state of the automaton
		begin = new State(automaton.getInitialState());
		current = begin;
		
		//We initialize the endStates HashSet and we fill it with the accept states of the automaton
		endStates = new HashSet<State>();
		Iterator<dk.brics.automaton.State> i = automaton.getAcceptStates().iterator();
		while (i.hasNext()){
			endStates.add(new State(i.next()));
		}	
		
		
		//We initialize the transitions HashSet 
		transitions = new HashSet<Transition>();
		//We get all the automaton's states
		Iterator<dk.brics.automaton.State> iState2 = automaton.getStates().iterator();
		Iterator<dk.brics.automaton.Transition> iTran3;
		dk.brics.automaton.Transition nextTran=null;
		dk.brics.automaton.State anStateDk=null;
		dk.brics.automaton.State nextStateDk=null;
		
		Set<State> alreadyStates = new HashSet<State>();
		State anState = current;
		State nextState = new State();
		alreadyStates.add(anState);
		
		while (iState2.hasNext()){
			anStateDk=iState2.next();
			
			anState=new State(anStateDk);
			
			if(!alreadyStates.contains(anState))
			{
				alreadyStates.add(anState);
			}
			//For each state, we get all the state's transitions
			iTran3 = anStateDk.getTransitions().iterator();
			while (iTran3.hasNext()){
				nextTran=iTran3.next();
				
				nextStateDk =nextTran.getDest();
				
				nextState = new State(nextStateDk);
				
				if(!alreadyStates.contains(nextState))
				{
					alreadyStates.add(nextState);
				}			
				
				// We fill the HashSet with those transitions
				transitions.add(new Transition(anState,nextState, nextTran.getMax()));
			}
		}
	}
}
