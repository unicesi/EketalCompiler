package co.edu.icesi.ketal.core;

/**
 * Expression. Permits the evaluation of an event and generates a boolean result
 */
public interface Expression {
	
	/**
	 * This method must compare the Event that has with the param.
	 * @param event: Param
	 * @return True, if param and the Event of the expression are equals.
	 */
	public boolean evaluate(Event event);
	
	/**
	 * This method returns the associated Event of the expression
	 * @return the actual Event associated with this Expression
	 * 
	 * modified by Okgarces
	 */
	public Event getEvent();
	
	/**
	 * This method sets and permits to modify the event associated with the 
	 * expression
	 * @param anEvent the new Event associated with this Expression
	 * 
	 * modified by Okgarces
	 */
	public boolean setEvent(Event anEvent);
	
}
