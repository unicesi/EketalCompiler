package co.edu.icesi.ketal.core;

/**
 * Extension of the dk.brics.automaton.Transition.
 * Expression is part of the alphabet to move of one state to other.
 * TODO: Review how Expressions should be used in transitions. They are not used
 * right now.
 */
public class Transition {
	protected State begin;
	protected State end;
	protected Expression expression;
	protected Character character;
	
	protected dk.brics.automaton.Transition transition;
	
	/**
	 * Future Constructor. Automatic map Exp and a Character. 
	 * @param transition
	 * @param begin
	 */
	public Transition(State begin, State end, Expression exp){
		this.begin = begin;
		this.end = end;
		this.expression=exp;
	}
	
	
	public Transition(State begin, State end, Character character)
	{
		this.begin=begin;
		this.end = end;
		this.character=character;
		
		dk.brics.automaton.Transition tran= new dk.brics.automaton.Transition(character, end.getState()); 
		begin.getState().addTransition(tran);
		this.transition = tran;
	}

	/**
	 * Get the State attribute begin
	 * @return State
	 */
	public State getBegin() {
		return begin;
	}

	/**
	 * Get the State attribute End
	 * @return State
	 */
	public State getEnd() {
		return end;
	}

	/**
	 * Get the Expression associated with this Transition
	 * @return Expression
	 */
	public Expression getExpression() {
		return expression;
	}
	
	public void setExpression(Expression exp)
	{
		this.expression=exp;
		
	}
	
	/**
	 * Try a transition using an Expression.
	 * @param incomingEvent
	 * @return
	 */
	public boolean evaluateExpression(Event incomingEvent)
	{
		if (begin != null)
		{
			if (begin.getState() != null)
			{
				if(this.expression.evaluate(incomingEvent)){
					//begin.setState(begin.getState().step(c));
					if (begin.getState()!= null)
					{
						return true;
					}
				}
				
			}
		}
		return false;
	}
	
	public double accuracyLevel(Event incomingEvent){
		return evaluateExpression(incomingEvent)?1:0;
	}
	
	/**
	 * Return mapped to an Expression and to Transition
	 * @return
	 */
	public Character getCharacter()
	{
		return character;
	}
}
