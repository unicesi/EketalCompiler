package co.edu.icesi.ketal.core;

public class TrueExpression implements Unary {

	public TrueExpression() {
		
	}
	
	@Override
	public boolean evaluate(Event event) {
		return true;
	}

	@Override
	public Event getEvent() {
		return null;
	}

	@Override
	public boolean setEvent(Event anEvent) {
		return false;
	}

	@Override
	public double accuracyLevel(Event incomingEvent) {
		return 0.3;
	}

}
