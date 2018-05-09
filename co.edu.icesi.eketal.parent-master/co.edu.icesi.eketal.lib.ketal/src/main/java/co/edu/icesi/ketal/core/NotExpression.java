package co.edu.icesi.ketal.core;

/**
 * Unary operation. 
 */
public class NotExpression implements Unary{

	Expression exp;
	
	/**
	 * @param exp Expression to be negated
	 */
	public NotExpression(Expression exp)
	{
		this.exp=exp;
	}
	
	/* (non-Javadoc)
	 * @see co.edu.icesi.ketal.core.Expression#evaluate(co.edu.icesi.ketal.core.Event)
	 */
	@Override
	public boolean evaluate(Event event) {
		return !exp.evaluate(event);
	}

	@Override
	public Event getEvent() {
		return exp.getEvent();
	}

	@Override
	public boolean setEvent(Event anEvent) {
		try
		{
			this.exp.setEvent(anEvent);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	@Override
	public double accuracyLevel(Event incomingEvent) {
		return 1-exp.accuracyLevel(incomingEvent);
	}


}
