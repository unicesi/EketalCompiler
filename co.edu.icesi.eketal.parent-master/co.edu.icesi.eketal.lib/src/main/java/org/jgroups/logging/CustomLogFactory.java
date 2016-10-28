package org.jgroups.logging;

public interface CustomLogFactory {

	  public abstract Log getLog(Class paramClass);
	  
	  public abstract Log getLog(String paramString);
	  
}
