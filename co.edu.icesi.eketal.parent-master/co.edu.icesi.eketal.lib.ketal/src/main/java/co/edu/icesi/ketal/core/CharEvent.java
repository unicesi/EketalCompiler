package co.edu.icesi.ketal.core;

import java.net.URL;
import java.util.List;

import org.jgroups.protocols.TransportedVectorTime;

public class CharEvent implements Event{
	
	private Character event;
	
	@Override
	public boolean equals(Event e) {
		if(e instanceof CharEvent){
			CharEvent compare = (CharEvent)e;
			return compare.event==event;
		}
		return false;
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
		return null;
	}

	@Override
	public boolean setTransportedVectorTime(TransportedVectorTime tvt) {
		return false;
	}

}
