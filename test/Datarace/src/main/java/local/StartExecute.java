package local;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.cache.Cache;
import org.jboss.cache.CacheFactory;
import org.jboss.cache.DefaultCacheFactory;
import org.jboss.cache.Fqn;


import co.edu.icesi.eketal.handlercontrol.EventHandler;

public class StartExecute {

	private static Log log = LogFactory.getLog(StartExecute.class);
	
	public static void main(String[] args) throws IOException {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		
		String r = "";
		boolean bool = false;
		while (!bool) {
			System.out.println("Please write a command");
			r = bf.readLine();
			if (r.equals("start")) {
				bool = true;
			}
		}
				
		System.out.println("[INFO] Starting cache");
		
		CacheFactory factory = new DefaultCacheFactory();
		Cache cache = factory.createCache("config/total-replication.xml");

		cache.create();
		cache.start();
		
		Fqn fqn = Fqn.fromString("/datarace/example");
		
		System.out.println("[INFO] Preparing to put a value");
		Thread thrd = new SleepThread(10000); 
		thrd.run();
		
		cache.put(fqn, "value1", "value1");
		
		System.out.println("[INFO] Preparing to put a value");
		thrd = new SleepThread(); 
		thrd.run();
		
		cache.put(fqn, "value2", "value2");
		
		System.out.println("[INFO] Preparing to read a value");
		thrd = new SleepThread(); 
		thrd.run();
		
		cache.get(fqn, "value1");
		
		thrd = new SleepThread(10000); 
		thrd.run();
		
		cache.stop();
		cache.destroy();

		bf.close();
		
		EventHandler.getInstance().close();
		
		System.out.println("[INFO] Stopped transation");
	}
}
