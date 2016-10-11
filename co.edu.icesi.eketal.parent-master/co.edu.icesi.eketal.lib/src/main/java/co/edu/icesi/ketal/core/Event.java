package co.edu.icesi.ketal.core;


import java.net.URL;
import java.util.List;

import org.jgroups.Address;
import org.jgroups.protocols.TransportedVectorTime;


/**
 * It describes any class with Event Behavior. It means, the programmer can
 * adapt its classes, to be used in this library by implementing this interface
 */
public interface Event
{
	/**
	 * Method to determine if two events are equal, this method is needed 
	 * if you shall use the DefaultEqualsExpression.
	 * @param e Event to compare
	 * @return boolean true if the events are equal, false in any other case
	 */
	public boolean equals(Event e);

	/**
	 * Method to get the localization of the event in form of a class URL
	 * @return URL
	 */
	public Address getLocalization();
	
	/**
	 * Method to set the localization of the event
	 * @param url Representing the localization
	 */
	public boolean setLocalization(Address url);
	
	/**
	 * Method to get the localization of a specific target if it is necessary.
	 * It means, if the event must be executed in a specific target.
	 * @return
	 */
	public List<Address> getTargetLocalization();
	
	/**
	 * Sets the localization of the target of the event, it means if the event must be executed in a specific 
	 * target
	 * @param url
	 * @return
	 */
	public boolean setTargetLocalization(List<Address> url);


	/**
	 * This method is used by CausalEqualsExpression to determine the causal relationship
	 * between two different events. The transportedVectorTime is an Object modified by the Causal Protocol 
	 * @return TransportedVectorTime
	 */
	public TransportedVectorTime getTransportedVectorTime();
	
	/**
	 * This method is used by Causal Protocol in the JGroups Stack.
	 * If the Expression CausalEqualsExpression must be used, this method is important.
	 * @return true if it can be set or false in other case.
	 */
	public boolean setTransportedVectorTime(TransportedVectorTime tvt);


	//todo: documentar
		//public Character getCharacterOfAlphabet();
		
		//todo: documentar (determinar si el caracter es el que est� asociado con el evento).
		//todo: modificar lo necesario, se cambi� el nombre incluyendo el "is" y se adicion� el par�metro.
		//public boolean isCharacterOfAlphabet(Character myChar);
		
		//todo: documentar
		//todo: eliminar este m�todo
		//public int hashCode();
}
