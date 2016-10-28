package org.jgroups.logging;

public abstract interface Log {
	public abstract boolean isFatalEnabled();
  
	public abstract boolean isErrorEnabled();
  
	public abstract boolean isWarnEnabled();
  
	public abstract boolean isInfoEnabled();
  
	public abstract boolean isDebugEnabled();
  
	public abstract boolean isTraceEnabled();
  
	public abstract void fatal(String paramString);
  
	public abstract void fatal(String paramString, Object... paramVarArgs);
  
	public abstract void fatal(String paramString, Throwable paramThrowable);
  
	public abstract void error(String paramString);
  
	public abstract void error(String paramString, Object... paramVarArgs);
  
	public abstract void error(String paramString, Throwable paramThrowable);
  
	public abstract void warn(String paramString);
  
	public abstract void warn(String paramString, Object... paramVarArgs);
  
	public abstract void warn(String paramString, Throwable paramThrowable);
  
	public abstract void info(String paramString);
  
	public abstract void info(String paramString, Object... paramVarArgs);
  
	public abstract void debug(String paramString);
  
	public abstract void debug(String paramString, Object... paramVarArgs);
  
	public abstract void debug(String paramString, Throwable paramThrowable);
	  
	public abstract void trace(Object paramObject);
	 
	public abstract void trace(String paramString);
	 
	public abstract void trace(String paramString, Object... paramVarArgs);
	  
	public abstract void trace(String paramString, Throwable paramThrowable);
	  
	public abstract void setLevel(String paramString);
	  
	public abstract String getLevel();

}
