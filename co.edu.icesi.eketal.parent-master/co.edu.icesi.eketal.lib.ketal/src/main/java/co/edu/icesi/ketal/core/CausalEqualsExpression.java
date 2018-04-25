package co.edu.icesi.ketal.core;

/**
 * Expression. compares the relation of events causality.
 *
 */
public class CausalEqualsExpression implements Expression
{
	public Event event;
	
	/**
	 * Constructor to create a CausalEqualsExpression with the given event
	 * @param anEvent to be used by the Expression
	 */
	public CausalEqualsExpression(Event anEvent)
	{
		this.event=anEvent;
	}
	
	/*
	 * This method evaluates whether two events are causal related.
	 * The way we choose to evaluates the causality, is to evaluate the causal relationship, the incoming event happens before 
	 * the Event described in this Expression. 
	 */
	@Override
	public boolean evaluate(Event event)
	{
		if(event!=null && this.event != null){
			if(event.getTransportedVectorTime() != null && this.event.getTransportedVectorTime() !=null){
				return event.getTransportedVectorTime().lessThanOrEqual(this.event.getTransportedVectorTime());
			}
		}
		return false;
	}

	@Override
	/**
	 * Get the associated event
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

	@Override
	public double accuracyLevel(Event incomingEvent) {
		return 1;
	}
}
