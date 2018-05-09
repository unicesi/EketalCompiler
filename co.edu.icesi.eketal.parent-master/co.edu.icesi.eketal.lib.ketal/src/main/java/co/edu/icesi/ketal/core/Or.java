package co.edu.icesi.ketal.core;

/**
 * Binary Operation between two expressions.  
 *
 */
public class Or implements Binary{

	Expression leftExpression;
	Expression rightExpression;
	/**
	 * Constructs an operation with two expressions.
	 * @param left First Expression.
	 * @param right Second Expression.
	 */
	public Or(Expression left, Expression right){
		leftExpression=left;
		rightExpression=right;
	}
	
	@Override
	/**
	 *  Evaluates, if the left expression or the right expression has a specific event. 
	 * @return True: If two expression has the same event. False: Other case. 
	 * @param Event, to compare the two expressions.
	 */
	public boolean evaluate(Event event) {
		return leftExpression.evaluate(event) || rightExpression.evaluate(event);
	}

	@Override
	public Event getEvent() {
		throw new UnsupportedOperationException("The Binary Expressions can not do this operation");
	}

	@Override
	public boolean setEvent(Event anEvent) {
		throw new UnsupportedOperationException("The Binary Expressions can not do this operation");
	}

	@Override
	public double accuracyLevel(Event incomingEvent) {
		return Double.max(leftExpression.accuracyLevel(incomingEvent), rightExpression.accuracyLevel(incomingEvent));
	}



}
