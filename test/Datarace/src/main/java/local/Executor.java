package local;

import java.util.Set;

import java.util.TreeSet;

import org.apache.log4j.*;
import org.jboss.cache.Cache;
import org.jboss.cache.CacheFactory;
import org.jboss.cache.DefaultCacheFactory;
import org.jboss.cache.Fqn;
import org.jboss.cache.Node;

public class Executor {

	private static Logger log = Logger.getLogger(Executor.class);
	
	public static void main(String[] args) {
		BasicConfigurator.configure();
		log.info("Starting cache");
		CacheFactory<String, Persona> factory = new DefaultCacheFactory<String, Persona>();
		Cache<String, Persona> cache = factory.createCache();
		cache.create();
		cache.start();
		
		Node<String, Persona> raiz = cache.getRoot();
		
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
			Fqn<String> fqn = Fqn.fromString(str.toString());
			
			Node<String, Persona> nodo = raiz.addChild(fqn);			
		}
		
		
		
		log.info("Stopping cache");
		
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

/*
 * // Let's get a hold of the root node.
Node rootNode = cache.getRoot();
// Remember, JBoss Cache stores data in a tree structure.
// All nodes in the tree structure are identified by Fqn objects.
Fqn peterGriffinFqn = Fqn.fromString("/griffin/peter");
// Create a new Node
Node peterGriffin = rootNode.addChild(peterGriffinFqn);
// let's store some data in the node
peterGriffin.put("isCartoonCharacter", Boolean.TRUE);
peterGriffin.put("favoriteDrink", new Beer());
// some tests (just assume this code is in a JUnit test case)
assertTrue(peterGriffin.get("isCartoonCharacter"));
assertEquals(peterGriffinFqn, peterGriffin.getFqn());
assertTrue(rootNode.hasChild(peterGriffinFqn));
Set keys = new HashSet();
keys.add("isCartoonCharacter");
keys.add("favoriteDrink");
assertEquals(keys, peterGriffin.getKeys());
// let's remove some data from the node
peterGriffin.remove("favoriteDrink");
assertNull(peterGriffin.get("favoriteDrink");
// let's remove the node altogether
rootNode.removeChild(peterGriffinFqn);
assertFalse(rootNode.hasChild(peterGriffinFqn));

Fqn peterGriffinFqn = Fqn.fromString("/griffin/peter");
cache.put(peterGriffinFqn, "isCartoonCharacter", Boolean.TRUE);
cache.put(peterGriffinFqn, "favoriteDrink", new Beer());
assertTrue(peterGriffin.get(peterGriffinFqn, "isCartoonCharacter"));
assertTrue(cache.getRootNode().hasChild(peterGriffinFqn));
cache.remove(peterGriffinFqn, "favoriteDrink");
assertNull(cache.get(peterGriffinFqn, "favoriteDrink");
cache.removeNode(peterGriffinFqn);
assertFalse(cache.getRootNode().hasChild(peterGriffinFqn));
*/
