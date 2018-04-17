package co.edu.icesi.ketal.core;

import java.util.Iterator;
import java.util.TreeSet;

public class BuchiAutomaton extends Automaton {
	
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
