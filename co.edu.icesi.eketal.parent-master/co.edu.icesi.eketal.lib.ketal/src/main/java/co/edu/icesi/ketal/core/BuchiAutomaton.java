package co.edu.icesi.ketal.core;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class BuchiAutomaton extends Automaton {
	
	public BuchiAutomaton(Set<? extends BuchiTransition> transitions, State begin, Set<State> end, Hashtable<Expression,Character> expressions){
		super((Set<Transition>)(Set<? extends Transition>)transitions, begin, end, expressions);
		//super((Set<Transition>)(Set<?>)transitions, begin, end, expressions);
	}
	
	@Override
	public boolean evaluate(Event event) {
		if(event instanceof NamedEvent){
			List<Transition> validTransitions = new ArrayList<>();
			for (Transition transition : transitionsOfCurrentState) {
				if(transition.evaluateExpression(event)){
					//return super.perform(event, transition.getCharacter());
					validTransitions.add(transition);
				}
			}
			switch (validTransitions.size()) {
			case 0:
				return false;
			case 1:
				perform(event, validTransitions.get(0).getCharacter());
				return true;
			default:
				int accurateTransition = 0;
				double precision = 0;
				for (int i = 0; i < validTransitions.size(); i++) {
					double accuracylevel = validTransitions.get(i).accuracyLevel(event);
					if(accuracylevel>precision){
						accurateTransition=i;
						precision = accuracylevel;
					}
				}
				perform(event, validTransitions.get(accurateTransition).getCharacter());
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean perform(Event event, char c) {
		if (current != null)
		{
			if (current.getState() != null)
			{
				State temp = new State(current.getState().step(c));
				//TODO this is a soft solution to a basic problem. No resolved.
				// When a dk.brics.State step (C) the instance change.
				
				if (temp.getState()!= null)
				{
					current = new State(temp.getState());
					current.setAccept(current.getState().isAccept());
					current.setEventCauseThisState(event);
					findTransitionsCurrentState();
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
				//temp.setExpression(getExpressionMapCharacter(temp.getCharacter()));
				transitionsOfCurrentState.add(temp);
			}
		}		
	}
}
