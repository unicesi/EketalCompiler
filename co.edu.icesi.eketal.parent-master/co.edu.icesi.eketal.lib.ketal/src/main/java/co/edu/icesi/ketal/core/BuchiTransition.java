package co.edu.icesi.ketal.core;

public class BuchiTransition extends Transition implements Comparable<BuchiTransition>{
	
	public BuchiTransition(State begin, State end, Character character, Expression label){
		super(begin, end, character);
		expression = label;
	}
	
	@Override
	public double accuracyLevel(Event incomingEvent) {
		return expression.accuracyLevel(incomingEvent);
	}
	
	@Override
	public boolean evaluateExpression(Event incomingEvent) {
		return super.evaluateExpression(incomingEvent);
	}
	
	@Override
	public int compareTo(BuchiTransition o) {
		int retorno = character-o.character;
		if(retorno==0){
			if(!(begin.equals(o.begin)&&end.equals(o.end)&&expression.equals(o.expression))){
				return -1;
			}
		}
		return retorno;
	}

}
