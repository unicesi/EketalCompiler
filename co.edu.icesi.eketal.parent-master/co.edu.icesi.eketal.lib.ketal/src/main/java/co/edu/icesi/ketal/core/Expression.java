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
	
	/**
	 * Calculates the level of acceptance between a transition and an incoming event.
	 * @precondition using this method assumes that evaluate(incomingEvent) returns true. 
	 * @param incomingEvent
	 * @return If the event is the same, must return max level of acceptance.
	 * If the event if different, must return 0,5 (because by precondition the evaluation eventually becomes true)
	 * If is a trueEvent, must return 0,3 (lowest level of acceptance, as this means that is the complement function of transitions for that state)
	 */
	
	public double accuracyLevel(Event incomingEvent);
	
}
