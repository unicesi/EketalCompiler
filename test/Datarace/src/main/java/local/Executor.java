package local;

import java.util.Set;
import java.util.TreeSet;

import org.jboss.cache.Cache;
import org.jboss.cache.CacheFactory;
import org.jboss.cache.DefaultCacheFactory;
import org.jboss.cache.Fqn;
import org.jboss.cache.Node;

public class Executor {

	public static void main(String[] args) {
//		CacheFactory<String, Persona> factory = new DefaultCacheFactory<String, Persona>();
//		Cache<String, Persona> cache = factory.createCache();
//		cache.create();
//		cache.start();
		
//		Node<String, Persona> raiz = cache.getRoot();
		
		Set<Persona> set = new TreeSet<Persona>();
		agregarPersonas(set);
		
		
		for (Persona persona : set) {
			StringBuilder str = new StringBuilder();
			str.append("/");
			str.append(persona.primerApellido);
			str.append("/");
			str.append(persona.segundoApellido);
			str.append("/");
			str.append(persona.primerNombre);
			System.out.println(str);
//			Fqn<String> fqn = Fqn.fromString(str.toString());
		}
		
		
	}

	private static void agregarPersonas(Set<Persona> set) {
		Persona nueva = new Persona();
		nueva.cedula="123";
		nueva.primerNombre = "Daniel";
		nueva.primerApellido = "Perez";
		nueva.segundoApellido = "Rodriguez";
		set.add(nueva);
		
		nueva = new Persona();
		nueva.cedula="987";
		nueva.primerNombre = "Fernando";
		nueva.primerApellido = "Gomez";
		nueva.segundoApellido = "Timochenko";
		set.add(nueva);
		
		nueva = new Persona();
		nueva.cedula="6545";
		nueva.primerNombre = "Luis";
		nueva.primerApellido = "Alvarez";
		nueva.segundoApellido = "Arevalo";
		set.add(nueva);
		
		nueva = new Persona();
		nueva.cedula="1645";
		nueva.primerNombre = "David";
		nueva.primerApellido = "Giraldo";
		nueva.segundoApellido = "Lopez";
		set.add(nueva);
	}

}class Persona implements Comparable<Persona>{
	
	String cedula, primerNombre, primerApellido, segundoApellido;

	public int compareTo(Persona o) {
		return cedula.compareTo(o.cedula);
	}
	
	//http://docs.jboss.org/jbosscache/3.2.1.GA/userguide_en/pdf/userguide_en.pdf
	
}
