package local;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.jboss.cache.Cache;
import org.jboss.cache.CacheFactory;
import org.jboss.cache.DefaultCacheFactory;
import org.jboss.cache.Fqn;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

import co.edu.icesi.eketal.handlercontrol.EventHandler;

public class RunExecute {
	
	
	public static void main(String[] args)throws IOException {

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
		
		CacheFactory factory = Mockito.mock(CacheFactory.class);
		when(factory.createCache(anyString())).thenReturn(Mockito.mock(Cache.class));
		
		Cache cache = factory.createCache("etc/config/total-replication.xml");
		cache.create();
		cache.start();
		
		Fqn fqn = Fqn.fromString("/datarace/example");
		
		System.out.println("[INFO] Preparing to read a value");
		Thread thrd = new SleepThread(10000); 
		thrd.run();
		cache.get(fqn, "value1");
		
		System.out.println("[INFO] Preparing to put a value");
		thrd = new SleepThread(); 
		thrd.run();
		
		cache.put(fqn, "value3", "value3");
		
		System.out.println("[INFO] Preparing to read a value");
		thrd = new SleepThread(); 
		thrd.run();
		
		cache.get(fqn, "value3");
		
		thrd = new SleepThread(10000); 
		thrd.run();
		
		cache.stop();
		cache.destroy();
		
		bf.close();
				
		System.out.println("[INFO] Stopped transation");
		
		System.exit(0);
	}
	
}
