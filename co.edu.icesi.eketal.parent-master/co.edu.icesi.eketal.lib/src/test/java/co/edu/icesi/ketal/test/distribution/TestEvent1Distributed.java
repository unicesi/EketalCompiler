package co.edu.icesi.ketal.test.distribution;

import java.io.Serializable;
import java.net.URL;
import java.util.List;

import co.edu.icesi.ketal.core.Event;

import org.jgroups.protocols.TransportedVectorTime;


public class TestEvent1Distributed  implements Event, Serializable{

	private static final long serialVersionUID = 14L;
		public Character alphabet;

		private TransportedVectorTime tvt;

		
		public TestEvent1Distributed(Character a) {
			alphabet=a;

		}
		
		public Character getCharacterOfAlphabet() {
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
			return tvt;
		}

		@Override
		public boolean setTransportedVectorTime(TransportedVectorTime tvt) {
			
			if(tvt!=null)
			{
				this.tvt=tvt;
				return true;
			}
			return false;
		}

}