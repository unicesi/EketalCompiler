package co.edu.icesi.ketal.core;

public class BuchiTransition extends Transition{
	
	public BuchiTransition(State begin, State end, Expression exp) {
		super(begin, end, exp);
	}
	
	public BuchiTransition(State begin, State end, Character character, Expression label){
		super(begin, end, character);
		this.setExpression(label);
	}

}
