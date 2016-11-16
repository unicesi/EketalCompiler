package local;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.jboss.cache.Cache;
import org.jboss.cache.CacheFactory;
import org.jboss.cache.DefaultCacheFactory;
import org.jboss.cache.Fqn;

public class RunExecute {
	
	private static Logger log = Logger.getLogger(RunExecute.class);
	
	public static void main(String[] args)throws IOException {
		BasicConfigurator.configure();

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
				
		log.info("Starting cache");
		
		CacheFactory factory = new DefaultCacheFactory();
		Cache cache = factory.createCache("etc/config/total-replication.xml");
		cache.create();
		cache.start();
		
		Fqn fqn = Fqn.fromString("/datarace/example");
		
		Thread thrd = new SleepThread(10000); 
		thrd.run();
		cache.get(fqn, "value1");
		
		thrd = new SleepThread(); 
		thrd.run();
		
		cache.put(fqn, "value3", "value3");
		
		
		thrd = new SleepThread(); 
		thrd.run();
		
		cache.get(fqn, "value3");
		
		thrd = new SleepThread(10000); 
		thrd.run();
		
		cache.stop();
		cache.destroy();
		

		bf.close();
	}
	
}
