package co.edu.icesi.ketal.core;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class BuchiAutomaton extends Automaton {
	
	public BuchiAutomaton(Set<Transition> transitions, State begin, Set<State> end, Hashtable<Expression,Character> expressions){
		super(transitions, begin, end, expressions);
	}
	
	@Override
	public boolean evaluate(Event event) {
		if(event instanceof CharEvent){
			for (Transition transition : transitionsOfCurrentState) {
				if(transition.evaluateExpression(event)){
					//return super.perform(event, transition.getCharacter());
					super.perform(event, transition.getCharacter());
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	protected void findTransitionsCurrentState(){
		Iterator<Transition> it=transitions.iterator();
		Transition temp=null;
		transitionsOfCurrentState =  new TreeSet<Transition>();
		while(it.hasNext()){
			temp = (Transition)it.next();
			if(temp.getBegin().equals(current)){
				temp.setExpression(getExpressionMapCharacter(temp.getCharacter()));
				transitionsOfCurrentState.add(temp);
			}
		}		
	}
}
