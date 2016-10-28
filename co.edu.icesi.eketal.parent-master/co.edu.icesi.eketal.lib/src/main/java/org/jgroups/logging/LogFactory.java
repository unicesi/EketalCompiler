package org.jgroups.logging;

public final class LogFactory {
	
	private LogFactory()
	  {
	    throw new InstantiationError("Must not instantiate this class");
	  }
	  
	  static
	  {
	    String customLogFactoryClass = System.getProperty("jgroups.logging.log_factory_class");
	    CustomLogFactory customLogFactoryX = null;
	    if (customLogFactoryClass != null) {
	      try
	      {
	        customLogFactoryX = (CustomLogFactory)Class.forName(customLogFactoryClass).newInstance();
	      }
	      catch (Exception e)
	      {
	        throw new RuntimeException(e);
	      }
	    }
	    custom_log_factory = customLogFactoryX;
	    USE_JDK_LOGGER = isPropertySet("jgroups.use.jdk_logger");
	  }
	  
	  public static final boolean IS_LOG4J2_AVAILABLE = isAvailable("org.apache.logging.log4j.core.Logger");
	  protected static final boolean USE_JDK_LOGGER;
	  protected static CustomLogFactory custom_log_factory;
	  
	  public static CustomLogFactory getCustomLogFactory()
	  {
	    return custom_log_factory;
	  }
	  
	  public static void setCustomLogFactory(CustomLogFactory factory)
	  {
	    custom_log_factory = factory;
	  }
	  
	  public static String loggerType()
	  {
	    if (USE_JDK_LOGGER) {
	      return "jdk";
	    }
	    if (IS_LOG4J2_AVAILABLE) {
	      return "log4j2";
	    }
	    return "jdk";
	  }
	  
	  protected static boolean isAvailable(String classname)
	  {
	    try
	    {
	      return Class.forName(classname) != null;
	    }
	    catch (ClassNotFoundException cnfe) {}
	    return false;
	  }
	  
	  protected static boolean isPropertySet(String property_name)
	  {
	    try
	    {
	      return Boolean.parseBoolean(System.getProperty(property_name));
	    }
	    catch (Throwable t) {}
	    return false;
	  }
	  
	  public static Log getLog(Class clazz)
	  {
	    if (custom_log_factory != null) {
	      return custom_log_factory.getLog(clazz);
	    }
	    if (USE_JDK_LOGGER) {
	      return new JDKLogImpl(clazz);
	    }
	    if (IS_LOG4J2_AVAILABLE) {
	      return new Log4J2LogImpl(clazz);
	    }
	    return new JDKLogImpl(clazz);
	  }
	  
	  public static Log getLog(String category)
	  {
	    if (custom_log_factory != null) {
	      return custom_log_factory.getLog(category);
	    }
	    if (USE_JDK_LOGGER) {
	      return new JDKLogImpl(category);
	    }
	    if (IS_LOG4J2_AVAILABLE) {
	      return new Log4J2LogImpl(category);
	    }
	    return new JDKLogImpl(category);
	  }
	
}
