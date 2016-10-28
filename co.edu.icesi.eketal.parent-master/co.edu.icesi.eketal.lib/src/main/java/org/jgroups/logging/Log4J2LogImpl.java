package org.jgroups.logging;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.simple.SimpleLogger;

public class Log4J2LogImpl implements Log {
	protected final org.apache.logging.log4j.Logger logger;
	  protected static final Level[] levels = { Level.TRACE, Level.DEBUG, Level.INFO, Level.WARN, Level.ERROR, Level.FATAL };
	  
	  public Log4J2LogImpl(String category)
	  {
	    this.logger = LogManager.getFormatterLogger(category);
	  }
	  
	  public Log4J2LogImpl(Class<?> category)
	  {
	    this.logger = LogManager.getFormatterLogger(category);
	  }
	  
	  public boolean isFatalEnabled()
	  {
	    return this.logger.isFatalEnabled();
	  }
	  
	  public boolean isErrorEnabled()
	  {
	    return this.logger.isErrorEnabled();
	  }
	  
	  public boolean isWarnEnabled()
	  {
	    return this.logger.isWarnEnabled();
	  }
	  
	  public boolean isInfoEnabled()
	  {
	    return this.logger.isInfoEnabled();
	  }
	  
	  public boolean isDebugEnabled()
	  {
	    return this.logger.isDebugEnabled();
	  }
	  
	  public boolean isTraceEnabled()
	  {
	    return this.logger.isTraceEnabled();
	  }
	  
	  public void trace(Object msg)
	  {
	    this.logger.trace(msg);
	  }
	  
	  public void trace(String msg)
	  {
	    this.logger.trace(msg);
	  }
	  
	  public void trace(String msg, Object... args)
	  {
	    this.logger.trace(msg, args);
	  }
	  
	  public void trace(String msg, Throwable throwable)
	  {
	    this.logger.trace(msg, throwable);
	  }
	  
	  public void debug(String msg)
	  {
	    this.logger.debug(msg);
	  }
	  
	  public void debug(String msg, Object... args)
	  {
	    this.logger.debug(msg, args);
	  }
	  
	  public void debug(String msg, Throwable throwable)
	  {
	    this.logger.debug(msg, throwable);
	  }
	  
	  public void info(String msg)
	  {
	    this.logger.info(msg);
	  }
	  
	  public void info(String msg, Object... args)
	  {
	    this.logger.info(msg, args);
	  }
	  
	  public void warn(String msg)
	  {
	    this.logger.warn(msg);
	  }
	  
	  public void warn(String msg, Object... args)
	  {
	    this.logger.warn(msg, args);
	  }
	  
	  public void warn(String msg, Throwable throwable)
	  {
	    this.logger.warn(msg, throwable);
	  }
	  
	  public void error(String msg)
	  {
	    this.logger.error(msg);
	  }
	  
	  public void error(String format, Object... args)
	  {
	    this.logger.error(format, args);
	  }
	  
	  public void error(String msg, Throwable throwable)
	  {
	    this.logger.error(msg, throwable);
	  }
	  
	  public void fatal(String msg)
	  {
	    this.logger.fatal(msg);
	  }
	  
	  public void fatal(String msg, Object... args)
	  {
	    this.logger.fatal(msg, args);
	  }
	  
	  public void fatal(String msg, Throwable throwable)
	  {
	    this.logger.fatal(msg, throwable);
	  }
	  
	  public String getLevel()
	  {
	    for (Level level : levels) {
	      if (this.logger.isEnabled(level)) {
	        return level.toString();
	      }
	    }
	    return "n/a";
	  }
	  
	  public void setLevel(String level)
	  {
	    Level new_level = strToLevel(level);
	    if (new_level == null) {
	      return;
	    }
	    if ((this.logger instanceof org.apache.logging.log4j.core.Logger)) {
	      ((org.apache.logging.log4j.core.Logger)this.logger).setLevel(new_level);
	    } else if ((this.logger instanceof SimpleLogger)) {
	      ((SimpleLogger)this.logger).setLevel(new_level);
	    }
	  }
	  
	  private static Level strToLevel(String level)
	  {
	    if (level == null) {
	      return null;
	    }
	    level = level.toLowerCase().trim();
	    if (level.equals("fatal")) {
	      return Level.FATAL;
	    }
	    if (level.equals("error")) {
	      return Level.ERROR;
	    }
	    if (level.equals("warn")) {
	      return Level.WARN;
	    }
	    if (level.equals("warning")) {
	      return Level.WARN;
	    }
	    if (level.equals("info")) {
	      return Level.INFO;
	    }
	    if (level.equals("debug")) {
	      return Level.DEBUG;
	    }
	    if (level.equals("trace")) {
	      return Level.TRACE;
	    }
	    return null;
	  }

}
