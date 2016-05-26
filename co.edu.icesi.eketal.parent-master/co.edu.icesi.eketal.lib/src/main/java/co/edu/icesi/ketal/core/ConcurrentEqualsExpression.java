package co.edu.icesi.ketal.core;

/**
 * Expression. compares the relation of events concurrency.
 *
 */
public class ConcurrentEqualsExpression implements Expression
{
	public Event event;
	
	
	/**
	 * Constructor to create a CausalEqualsExpression with the given event
	 * @param anEvent to be used by the Expression
	 */
	public ConcurrentEqualsExpression(Event anEvent)
	{
		this.event=anEvent;
	}
	
	/*
	 * This method evaluates whether two events are concurrent.
	 * The way we choose to evaluates the concurrency, is to evaluate the causal relationship betwen the events. In both ways. 
	 */
	@Override
	public boolean evaluate(Event event)
	{
		boolean acausalb = this.event.getTransportedVectorTime().lessThanOrEqual(event.getTransportedVectorTime());
		boolean bcausala = event.getTransportedVectorTime().lessThanOrEqual(this.event.getTransportedVectorTime());

		return !(acausalb && bcausala);
	}
	
	@Override
	/**
	 * Get the event associated
	 * @return Event evaluated by the Expression
	 */
	public Event getEvent()
	{
		return event;
	}
	
	@Override
	/**
	 * Set the event associated
	 * @param event Event evaluated by the Expression
	 * @return boolean
	 */
	public boolean setEvent(Event event)
	{
		try
		{
			this.event=event;
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}

}
