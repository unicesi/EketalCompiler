package co.edu.icesi.ketal.core;

/**
 * Binary Operation between two expressions.  
 *
 */
public class And implements Binary{
	
	private Expression leftExpression= null;
	private Expression rightExpression=null;
	
	/**
	 * Constructs an operation with two expressions.
	 * @param left First Expression.
	 * @param right Second Expression.
	 */
	public And(Expression left, Expression right){
		leftExpression=left;
		rightExpression=right;
	}
	
	
	@Override
	/**
	 * Evaluates, if the left expression and the right expression has the same event.
	 * @return True: If two expression has the same event. False: Other case. 
	 * @param Event, to compare the two expressions.
	 */
	public boolean evaluate(Event event) {
		return leftExpression.evaluate(event) && rightExpression.evaluate(event);
	}

	@Override
	/**
	 * This method is not supported by binary Expression. The use of this method shall
	 * throw an UnsupportedOperationException
	 * @return None
	 */
	public Event getEvent() {
		throw new UnsupportedOperationException("The Binary Expressions can not do this operation");
	}


	@Override
	/**
	 * This method is not supported by binary Expression. The use of this method shall
	 * throw an UnsupportedOperationException
	 * @return None
	 */
	public boolean setEvent(Event anEvent) {
		throw new UnsupportedOperationException("The Binary Expressions can not do this operation");
	}


}
