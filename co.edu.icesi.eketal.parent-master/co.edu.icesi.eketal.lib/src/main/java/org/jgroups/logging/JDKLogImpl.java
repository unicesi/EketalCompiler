package org.jgroups.logging;

import java.util.Arrays;
import java.util.IllegalFormatException;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class JDKLogImpl
  implements Log
{
  protected final Logger logger;
  
  public JDKLogImpl(String category)
  {
    this.logger = Logger.getLogger(category);
  }
  
  public JDKLogImpl(Class category)
  {
    this.logger = Logger.getLogger(category.getName());
  }
  
  private void log(Level lv, String msg)
  {
    log(lv, msg, null);
  }
  
  private void log(Level lv, String msg, Throwable e)
  {
    if (this.logger.isLoggable(lv))
    {
      LogRecord r = new LogRecord(lv, msg);
      r.setThrown(e);
      for (StackTraceElement frame : new Exception().getStackTrace()) {
        if (!frame.getClassName().equals(THIS_CLASS_NAME))
        {
          r.setSourceClassName(frame.getClassName());
          r.setSourceMethodName(frame.getMethodName());
          break;
        }
      }
      this.logger.log(r);
    }
  }
  
  public boolean isTraceEnabled()
  {
    return this.logger.isLoggable(Level.FINER);
  }
  
  public boolean isDebugEnabled()
  {
    return this.logger.isLoggable(Level.FINE);
  }
  
  public boolean isInfoEnabled()
  {
    return this.logger.isLoggable(Level.INFO);
  }
  
  public boolean isWarnEnabled()
  {
    return this.logger.isLoggable(Level.WARNING);
  }
  
  public boolean isErrorEnabled()
  {
    return this.logger.isLoggable(Level.SEVERE);
  }
  
  public boolean isFatalEnabled()
  {
    return this.logger.isLoggable(Level.SEVERE);
  }
  
  public void trace(String msg)
  {
    log(Level.FINER, msg);
  }
  
  public void trace(String msg, Object... args)
  {
    if (isTraceEnabled()) {
      log(Level.FINER, format(msg, args));
    }
  }
  
  public void trace(Object msg)
  {
    log(Level.FINER, msg.toString());
  }
  
  public void trace(String msg, Throwable t)
  {
    log(Level.FINER, msg, t);
  }
  
  public void debug(String msg)
  {
    log(Level.FINE, msg);
  }
  
  public void debug(String msg, Object... args)
  {
    if (isDebugEnabled()) {
      log(Level.FINE, format(msg, args));
    }
  }
  
  public void debug(String msg, Throwable t)
  {
    log(Level.FINE, msg, t);
  }
  
  public void info(String msg)
  {
    log(Level.INFO, msg);
  }
  
  public void info(String msg, Object... args)
  {
    if (isInfoEnabled()) {
      log(Level.INFO, format(msg, args));
    }
  }
  
  public void warn(String msg)
  {
    log(Level.WARNING, msg);
  }
  
  public void warn(String msg, Object... args)
  {
    if (isWarnEnabled()) {
      log(Level.WARNING, format(msg, args));
    }
  }
  
  public void warn(String msg, Throwable t)
  {
    log(Level.WARNING, msg, t);
  }
  
  public void error(String msg)
  {
    log(Level.SEVERE, msg);
  }
  
  public void error(String format, Object... args)
  {
    if (isErrorEnabled()) {
      log(Level.SEVERE, format(format, args));
    }
  }
  
  public void error(String msg, Throwable t)
  {
    log(Level.SEVERE, msg, t);
  }
  
  public void fatal(String msg)
  {
    log(Level.SEVERE, msg);
  }
  
  public void fatal(String msg, Object... args)
  {
    if (isFatalEnabled()) {
      log(Level.SEVERE, format(msg, args));
    }
  }
  
  public void fatal(String msg, Throwable t)
  {
    log(Level.SEVERE, msg, t);
  }
  
  public String getLevel()
  {
    Level level = this.logger.getLevel();
    return level != null ? level.toString() : "off";
  }
  
  public void setLevel(String level)
  {
    Level new_level = strToLevel(level);
    if (new_level != null) {
      this.logger.setLevel(new_level);
    }
  }
  
  protected String format(String format, Object... args)
  {
    try
    {
      return String.format(format, args);
    }
    catch (IllegalFormatException ex)
    {
      error("Illegal format string \"" + format + "\", args=" + Arrays.toString(args));
    }
    catch (Throwable t)
    {
      error("Failure formatting string: format string=" + format + ", args=" + Arrays.toString(args));
    }
    return format;
  }
  
  protected static Level strToLevel(String level)
  {
    if (level == null) {
      return null;
    }
    level = level.toLowerCase().trim();
    if (level.equals("fatal")) {
      return Level.SEVERE;
    }
    if (level.equals("error")) {
      return Level.SEVERE;
    }
    if (level.equals("warn")) {
      return Level.WARNING;
    }
    if (level.equals("warning")) {
      return Level.WARNING;
    }
    if (level.equals("info")) {
      return Level.INFO;
    }
    if (level.equals("debug")) {
      return Level.FINE;
    }
    if (level.equals("trace")) {
      return Level.FINER;
    }
    return null;
  }
  
  private static final String THIS_CLASS_NAME = JDKLogImpl.class.getName();
}
