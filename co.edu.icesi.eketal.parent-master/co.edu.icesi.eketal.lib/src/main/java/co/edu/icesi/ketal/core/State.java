package co.edu.icesi.ketal.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Facade State of the dk.brics.automaton.State
 */
public class State implements Cloneable{
	
	private static final Log logger = LogFactory
			.getLog(State.class);
	
	private dk.brics.automaton.State state;
	
	// This Event is saved to show what event happened to stay in this State
	private Event eventCausethisState;
	
	/**
	 * Constructor
	 * @param state
	 */
	public State(){
		state =  new dk.brics.automaton.State();
	}
	
	/**
	 * This constructor shall be used by Automaton Class to create this State.
	 * @param state
	 */
	protected State(dk.brics.automaton.State state)
	{
		this.state=state;
	}
	/**
	 * Get the State attribute
	 * @return State
	 */
	public dk.brics.automaton.State getState(){
		return this.state;
	}
	
	/**
	 * Set State attribute
	 * @param state
	 */
	public void setState(dk.brics.automaton.State state){
		this.state = state;
	}
	
	/**
	 * Get the Event that happened and make the transition to this current State
	 * @return Event
	 */
	public Event getEventCauseThisState()
	{
		return eventCausethisState;
	}
	
	/**
	 * Set the Event that happened and make the transition to this current State
	 * @return The New Event, makes the transition happens.
	 */
	public void setEventCauseThisState(Event event)
	{
		this.eventCausethisState=event;
		
	}
	
	/**
	 * Sets param about the Associated State is accept state.
	 * @param accept
	 */
	public void setAccept(boolean accept)
	{
		state.setAccept(accept);
	}
	
	/**
	 * Return true if the associated state is an Accept State. False in other case.  
	 * @return
	 */
	public boolean getAccept()
	{
		return state.isAccept();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		return result;
	}

	/* 
	 * Two States are equals if the associated states are equals.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof State))
			return false;
		State other = (State) obj;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		return true;
	}

	public Object clone(){
        Object obj=null;
        try{
            obj=super.clone();
        }catch(CloneNotSupportedException ex){
        	logger.error("Error al Clonar");
//            System.out.println("Error al Clonar");
        }
        return obj;
    }
	
	
}
