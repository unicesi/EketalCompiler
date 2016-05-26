package co.edu.icesi.ketal.core;

/**
 * Default Equals Expression. Used to determine the use of an Event
 *
 */
public class DefaultEqualsExpression implements Expression{
	
	private Event event;
	
	/**
	 * Constructor to create a DefaultEqualsExpression with the given event
	 * @param anEvent to be used by the Expression
	 */
	public DefaultEqualsExpression(Event anEvent)
	{
		this.event=anEvent;
	}
	
	/* (non-Javadoc)
	 * @see co.edu.icesi.ketal.core.Expression#evaluate(co.edu.icesi.ketal.core.Event)
	 */
	@Override
	public boolean evaluate(Event anEvent) {
	
		return this.event.equals(anEvent);
	}
	
	@Override
	/**
	 * @return Event associated with the Expression
	 */
	public Event getEvent()
	{
		return event;
	}

	@Override
	public boolean setEvent(Event anEvent) {

		try
		{
			this.event=anEvent;
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}		
}
