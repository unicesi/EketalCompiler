package co.edu.icesi.ketal.test.buchi;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import co.edu.icesi.ketal.core.BuchiAutomaton;
import co.edu.icesi.ketal.core.BuchiTransition;
import co.edu.icesi.ketal.core.DefaultEqualsExpression;
import co.edu.icesi.ketal.core.Expression;
import co.edu.icesi.ketal.core.NamedEvent;
import co.edu.icesi.ketal.core.NotExpression;
import co.edu.icesi.ketal.core.Or;
import co.edu.icesi.ketal.core.State;

public class TestBuchiComposed extends BuchiAutomaton {
	private static TestBuchiComposed instance;

	public static HashMap<String, co.edu.icesi.ketal.core.State> estados = new HashMap<String, co.edu.icesi.ketal.core.State>();

	public static co.edu.icesi.ketal.core.Automaton getInstance() {
		if (instance == null) {
			// lista de estados finales
			Set<co.edu.icesi.ketal.core.State> estadosFinales = new HashSet();
			// conjunto de transiciones
			Set<co.edu.icesi.ketal.core.BuchiTransition> transitionSet = new HashSet();
			// map de expresiones con caracteres
			Hashtable<co.edu.icesi.ketal.core.Expression, Character> expressions = new Hashtable();
			co.edu.icesi.ketal.core.State initial = initialize(transitionSet, estadosFinales, expressions);
			return new TestBuchiComposed(transitionSet, initial, estadosFinales, expressions);
		} else {
			return instance;
		}
	}

	private TestBuchiComposed(final Set<co.edu.icesi.ketal.core.BuchiTransition> transitions,
			final co.edu.icesi.ketal.core.State begin, final Set<co.edu.icesi.ketal.core.State> finalStates,
			final Hashtable<co.edu.icesi.ketal.core.Expression, Character> expressions) {
		super(transitions, begin, finalStates, expressions);
		initializeAutomaton();
		instance = this;
	}

	private static co.edu.icesi.ketal.core.State initialize(final Set<co.edu.icesi.ketal.core.BuchiTransition> transitionSet,
			final Set<co.edu.icesi.ketal.core.State> estadosFinales,
			final Hashtable<co.edu.icesi.ketal.core.Expression, Character> expressions) {
		
		//(eventHello)->((otherEvent)U(anotherEvent))
		//RES = S0,
		//S1=(TRUE-> S1),
		//S2=(anotherEvent-> S1 |otherEvent-> S2),
		//S0=(anotherEvent-> S1 |!eventHello-> S1 |otherEvent-> S2).
		//AS = { S1 }
		
		// Relación evento caracter
		Map<String, Character> mapping = new TreeMap<String, Character>();
		// Estado inicial
		co.edu.icesi.ketal.core.State inicial = null;

		int consecutivo = 65;
		Character caracter = (char) consecutivo;
		String nombreEvento = "";
		String estadoLlegada = "";

		// Definición del estado: S0
		String estadoS0 = "S0";
		estados.put(estadoS0, new co.edu.icesi.ketal.core.State());
		// start start 1
		// Estado inicial: S0
		inicial = estados.get(estadoS0);
		// Definición del estado: finalState
		String estadoS1 = "S1";
		estados.put(estadoS1, new co.edu.icesi.ketal.core.State());
		// Definición del estado: finalState
		String estadoS2 = "S2";
		estados.put(estadoS2, new co.edu.icesi.ketal.core.State());
		
		
		// Transicion de anotherEvent|!eventHello -> S1
		estadoLlegada = "S1";
		if (!estados.containsKey(estadoLlegada)) {
			estados.put(estadoS0, new co.edu.icesi.ketal.core.State());
		}
		caracter = (char) consecutivo;
		consecutivo++;
		
		Expression expressionAnotherEventEventHello = new Or(new DefaultEqualsExpression(new NamedEvent("anotherEvent")), new NotExpression(new DefaultEqualsExpression(new NamedEvent("eventHello"))));
		if (!mapping.containsKey(nombreEvento)) {
			mapping.put(nombreEvento, caracter);
			expressions.put(expressionAnotherEventEventHello, mapping.get(nombreEvento));
		}
		co.edu.icesi.ketal.core.BuchiTransition firstStateEventHello = new co.edu.icesi.ketal.core.BuchiTransition(
				estados.get(estadoS0), estados.get(estadoLlegada), mapping.get(nombreEvento), expressionAnotherEventEventHello);
		transitionSet.add(firstStateEventHello);
		
		// Transicion de otherEvent-> S2
	    estadoLlegada = "S2";
	    if(!estados.containsKey(estadoLlegada)){
	    	estados.put(estadoS1, new State());
	    }
	    caracter = (char)consecutivo;
	    consecutivo++;
	    Expression expression = new DefaultEqualsExpression(new NamedEvent("otherEvent"));
	    if(!mapping.containsKey(nombreEvento)){
	    	mapping.put(nombreEvento, caracter);
	    	expressions.put(expression, mapping.get(nombreEvento));
	    }
	    BuchiTransition finalStateInitServer = new BuchiTransition(estados.get(estadoS1), estados.get(estadoLlegada), mapping.get(nombreEvento),expression);
	    transitionSet.add(finalStateInitServer);
		
	    
	    // Transicion de otherEvent-> S2
	    estadoLlegada = "S2";
	    if(!estados.containsKey(estadoLlegada)){
	    	estados.put(estadoS1, new State());
	    }
	    caracter = (char)consecutivo;
	    consecutivo++;
	    expression = new DefaultEqualsExpression(new NamedEvent("otherEvent"));
	    if(!mapping.containsKey(nombreEvento)){
	    	mapping.put(nombreEvento, caracter);
	    	expressions.put(expression, mapping.get(nombreEvento));
	    }
	    BuchiTransition otherEventS0 = new BuchiTransition(estados.get(estadoS2), estados.get(estadoLlegada), mapping.get(nombreEvento),expression);
	    transitionSet.add(otherEventS0);
		
	 // Transicion de anotherEvent-> S1
	    estadoLlegada = "S1";
	    if(!estados.containsKey(estadoLlegada)){
	    	estados.put(estadoS1, new State());
	    }
	    caracter = (char)consecutivo;
	    consecutivo++;
	    expression = new DefaultEqualsExpression(new NamedEvent("anotherEvent"));
	    if(!mapping.containsKey(nombreEvento)){
	    	mapping.put(nombreEvento, caracter);
	    	expressions.put(expression, mapping.get(nombreEvento));
	    }
	    BuchiTransition otherEventS2 = new BuchiTransition(estados.get(estadoS2), estados.get(estadoLlegada), mapping.get(nombreEvento),expression);
	    transitionSet.add(otherEventS2);
	    
		// Transicion de TRUE -> S1
		estadoLlegada = "S1";
		if (!estados.containsKey(estadoLlegada)) {
			estados.put(estadoS0, new co.edu.icesi.ketal.core.State());
		}
		caracter = (char) consecutivo;
		consecutivo++;
		nombreEvento = "TRUE";
		if (!mapping.containsKey(nombreEvento)) {
			mapping.put(nombreEvento, caracter);
			expressions.put(new DefaultEqualsExpression(new NamedEvent(nombreEvento)), mapping.get(nombreEvento));
		}
		co.edu.icesi.ketal.core.BuchiTransition firstStateEventWorld = new co.edu.icesi.ketal.core.BuchiTransition(
				estados.get(estadoS1), estados.get(estadoLlegada), mapping.get(nombreEvento), null);
		firstStateEventWorld.setAnyEvent(true);
		transitionSet.add(firstStateEventWorld);
	    
	    
		// Estado final S1
		estados.get(estadoS1).setAccept(true);
		estadosFinales.add(estados.get(estadoS1));

		return inicial;
	}
}
