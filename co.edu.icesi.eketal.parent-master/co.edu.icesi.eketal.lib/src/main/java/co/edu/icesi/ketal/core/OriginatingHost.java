package co.edu.icesi.ketal.core;

/**
 * This class describes an Unary Expression, where the Events
 * of Expression determines the originating host.
 * 
 * @author okgarces
 *
 */
public class OriginatingHost implements Unary {

	Expression exp;
	/**
	 * Default constructor of Unary expression. 
	 * @param exp Expression.
	 */
	public OriginatingHost(Expression exp)
	{
		this.exp=exp;		
	}
	
	@Override
	/**
	 * Defines if match the described originating host by this expression
	 * with the host of the incoming event.
	 * @return True if exists the match, otherwise False.
	 * @param event to compare with this specific Expression.
	 */
	public boolean evaluate(Event event) {
		
		return event.getLocalization().equals(exp.getEvent().getLocalization());
		
	}

	@Override
	/**
	 *  @return Event associated with the Expression. 
	 */
	public Event getEvent() {
		return exp.getEvent();
	}

	@Override
	/**
	 * Sets the event associated with the Expression.
	 */
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

}
