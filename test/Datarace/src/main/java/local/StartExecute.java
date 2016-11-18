package local;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Set;

import java.util.TreeSet;

import org.jboss.cache.Cache;
import org.jboss.cache.CacheFactory;
import org.jboss.cache.DefaultCacheFactory;
import org.jboss.cache.Fqn;
import org.jboss.cache.config.Configuration;
import org.jboss.cache.config.Configuration.CacheMode;
import org.jboss.cache.lock.IsolationLevel;
import org.jboss.cache.transaction.GenericTransactionManagerLookup;

import org.mockito.Mockito;

//import co.edu.icesi.eketal.handlercontrol.EventHandler;

import static org.mockito.Mockito.*;

public class StartExecute {

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
		
		CacheFactory factory = Mockito.mock(CacheFactory.class);
		when(factory.createCache(anyString())).thenReturn(Mockito.mock(Cache.class));
		
		Cache cache = factory.createCache("etc/config/total-replication.xml");
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
		
//		EventHandler.getInstance().close();
		
		System.out.println("[INFO] Stopped transation");
	}
}
