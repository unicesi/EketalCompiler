package co.edu.icesi.ketal.core;

public class BuchiTransition extends Transition implements Comparable<BuchiTransition>{
	
	private boolean anyEvent;
	
	public BuchiTransition(State begin, State end, Expression exp) {
		super(begin, end, exp);
		anyEvent = false;
	}
	
	public BuchiTransition(State begin, State end, Character character, Expression label){
		super(begin, end, character);
		expression = label;
		anyEvent = false;
	}

	public boolean isAnyEvent() {
		return anyEvent;
	}

	public void setAnyEvent(boolean anyEvent) {
		this.anyEvent = anyEvent;
	}

	@Override
	public int compareTo(BuchiTransition o) {
		int retorno = character-o.character;
		if(anyEvent){
			//We prefer that the TRUE transitions must be evaluated at latest. 
			retorno+=1000;
		}
		return retorno;
	}

}
