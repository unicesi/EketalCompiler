package co.edu.icesi.ketal.test.defaultEqualsEventTest;

import java.net.URL;
import java.util.List;

import co.edu.icesi.ketal.core.*;

import org.jgroups.Address;
import org.jgroups.protocols.TransportedVectorTime;


public class TestEvent1 implements Event{

	private Character eventChar;
	
	public TestEvent1(Character a) {
		eventChar=a;

	}
	
	public Character getCharacterOfAlphabet() {
		return eventChar;
	}
	
	@Override
	public boolean equals(Event anEvent) {
       if (this == anEvent)
           return true;
       if (anEvent == null)
           return false;
       if (getClass() != anEvent.getClass())
           return false;
       TestEvent1 other = (TestEvent1) anEvent;
       if (eventChar == null) {
           if (other.getCharacterOfAlphabet() != null)
               return false;
       } else if (!eventChar.equals(other.getCharacterOfAlphabet()))
           return false;
       return true;
   }

	@Override
	public URL getLocalization() {
		return null;
	}

	@Override
	public boolean setLocalization(URL url) {
		return false;
		
	}

	@Override
	public List<URL> getTargetLocalization() {
		return null;
	}

	@Override
	public boolean setTargetLocalization(List<URL> url) {
		return false;
	}


	@Override
	public TransportedVectorTime getTransportedVectorTime() {
		//No need this method
		return null;
	}

	@Override
	public boolean setTransportedVectorTime(TransportedVectorTime tvt) {
		//No need this method
		return false;
	}

}
