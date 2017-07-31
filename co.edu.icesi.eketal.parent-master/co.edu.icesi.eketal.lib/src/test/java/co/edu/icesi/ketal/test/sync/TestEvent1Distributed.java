package co.edu.icesi.ketal.test.sync;

import java.io.Serializable;
import java.net.URL;
import java.util.List;

import org.jgroups.protocols.TransportedVectorTime;

import co.edu.icesi.ketal.core.Event;
public class TestEvent1Distributed  implements Event, Serializable{

		/**
	 * 
	 */
		private static final long serialVersionUID = 101010101L;
		public Character alphabet;
		
		public TestEvent1Distributed(Character a) {
			alphabet=a;

		}
		
		public Character getCharacterOfAlphabet() {
			//No need this method
			return alphabet;
		}


		@Override
		public boolean equals(Event anEvent) {
			if (this == anEvent)
				return true;
			if (anEvent == null)
				return false;
			if (getClass() != anEvent.getClass())
				return false;
			TestEvent1Distributed other = (TestEvent1Distributed) anEvent;
			if (alphabet == null) {
				if (other.alphabet != null)
					return false;
			} else if (!alphabet.equals(other.alphabet))
				return false;
			return true;
		}
	
		@Override
		public String toString()
		{
		return alphabet+"";	
		}

		@Override
		public URL getLocalization() {
			return null;
		}

		@Override
		public boolean setLocalization(URL url) {
			return true;
			
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