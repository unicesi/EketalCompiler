package local;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Set;

import java.util.TreeSet;

import org.apache.log4j.*;
import org.jboss.cache.Cache;
import org.jboss.cache.CacheFactory;
import org.jboss.cache.DefaultCacheFactory;
import org.jboss.cache.Fqn;
import org.jboss.cache.config.Configuration;
import org.jboss.cache.config.Configuration.CacheMode;
import org.jboss.cache.lock.IsolationLevel;
import org.jboss.cache.transaction.GenericTransactionManagerLookup;

import org.mockito.Mockito;
import static org.mockito.Mockito.*;

public class StartExecute {

	private static Logger log = Logger.getLogger(StartExecute.class);

	public static void main(String[] args) throws IOException {
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
		
		cache.put(fqn, "value1", "value1");
		
		thrd = new SleepThread(); 
		thrd.run();
		
		cache.put(fqn, "value2", "value2");
		
		thrd = new SleepThread(); 
		thrd.run();
		
		cache.get(fqn, "value1");
		
		thrd = new SleepThread(10000); 
		thrd.run();
		
		cache.stop();
		cache.destroy();
		
		
		/*
		 * 		
		int tInicial = 0;
		int numDatos = 10000;

		
//		CacheFactory factory = Mockito.mock(CacheFactory.class);
//		when(factory.createCache(anyString())).thenReturn(Mockito.mock(Cache.class));

		System.out.println(cache.toString());
		
		long inicial = System.currentTimeMillis();
		ponerDatos(tInicial, cache, fqn, numDatos, "Nuevo");
		double t1 = (double)(System.currentTimeMillis()-inicial)/1000;
		
		inicial = System.currentTimeMillis();
		leerDatos(cache, numDatos, "Nuevo", fqn);
		double t2 = (double)(System.currentTimeMillis()-inicial)/1000;
		
		System.out.println("Tiempo: "+t1);
		System.out.println("Tiempo: "+t2);
		
		log.info("Stopping cache");

		bool = false;
		while (!bool) {
			System.out.println("Please write an event");
			r = bf.readLine();
			if (r.equals("stop")) {
				bool = true;
			}else if (r.equals("consultar")) {
				System.out.println(cache.toString());
			}
				
		}
		
		 */
		bf.close();
	}

	private static void leerDatos(Cache cache, int numDatos, String inicial, Fqn fqn) {
		for (int i = 0; i < numDatos; i++) {
			String temp = inicial+i;
			String retorno = (String) cache.get(fqn, temp);
			System.out.println(retorno);
		}
	}

	private static void ponerDatos(int tInicial, Cache cache, Fqn fqn, int numDatos, String inicial) {
		for (int i = tInicial; i < tInicial+numDatos; i++) {
			String temp = inicial+i;
			cache.put(fqn, temp, temp);
		}
	}
}
