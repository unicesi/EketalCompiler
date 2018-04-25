package co.edu.icesi.ketal.test.buchi;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import co.edu.icesi.ketal.core.*;

public class TestBuchiCase extends BuchiAutomaton {
	private static TestBuchiCase instance;

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
			return new TestBuchiCase(transitionSet, initial, estadosFinales, expressions);
		} else {
			return instance;
		}
	}

	private TestBuchiCase(final Set<co.edu.icesi.ketal.core.BuchiTransition> transitions,
			final co.edu.icesi.ketal.core.State begin, final Set<co.edu.icesi.ketal.core.State> finalStates,
			final Hashtable<co.edu.icesi.ketal.core.Expression, Character> expressions) {
		super(transitions, begin, finalStates, expressions);
		findTransitionsCurrentState();
		instance = this;
	}

	private static co.edu.icesi.ketal.core.State initialize(final Set<co.edu.icesi.ketal.core.BuchiTransition> transitionSet,
			final Set<co.edu.icesi.ketal.core.State> estadosFinales,
			final Hashtable<co.edu.icesi.ketal.core.Expression, Character> expressions) {
		
		/*
		 * <>[]eventHello
		 * RES = firstState,
		 * finalState=(eventHello-> finalState),
		 * firstState=(TRUE-> firstState |eventHello-> finalState).
		 * AS = { finalState }
		 */
		
		// Estado inicial
		co.edu.icesi.ketal.core.State inicial = null;

		int consecutivo = 65;
		Character caracter = (char) consecutivo;
		String nombreEvento = "";
		String estadoLlegada = "";

		// Definición del estado: firstState
		String estadoFirstState = "firstState";
		estados.put(estadoFirstState, new co.edu.icesi.ketal.core.State());
		// start start 1
		// Estado inicial: firstState
		inicial = estados.get(estadoFirstState);
		// Definición del estado: finalState
		String estadoFinalState = "finalState";
		estados.put(estadoFinalState, new co.edu.icesi.ketal.core.State());
		
		
		// Transicion de eventHello -> finalState
		estadoLlegada = "finalState";
		if (!estados.containsKey(estadoLlegada)) {
			estados.put(estadoFirstState, new co.edu.icesi.ketal.core.State());
		}
		caracter = (char) consecutivo;
		consecutivo++;
		nombreEvento = "eventHello";
		Expression expressionEventHello = new DefaultEqualsExpression(new NamedEvent(nombreEvento));
		if (!expressions.containsKey(expressionEventHello)) {
			expressions.put(expressionEventHello, caracter);
		}
		co.edu.icesi.ketal.core.BuchiTransition firstStateEventHello = new co.edu.icesi.ketal.core.BuchiTransition(
				estados.get(estadoFirstState), estados.get(estadoLlegada), expressions.get(expressionEventHello), expressionEventHello);
		transitionSet.add(firstStateEventHello);

		// Transicion de TRUE -> firstState
		estadoLlegada = "firstState";
		if (!estados.containsKey(estadoLlegada)) {
			estados.put(estadoFirstState, new co.edu.icesi.ketal.core.State());
		}
		caracter = (char) consecutivo;
		consecutivo++;
		nombreEvento = "TRUE";
		Expression trueExpression=new TrueExpression();
		if (!expressions.containsKey(trueExpression)) {
			expressions.put(trueExpression, caracter);
		}
		co.edu.icesi.ketal.core.BuchiTransition firstStateEventWorld = new co.edu.icesi.ketal.core.BuchiTransition(
				estados.get(estadoFirstState), estados.get(estadoLlegada), expressions.get(trueExpression), trueExpression);
		transitionSet.add(firstStateEventWorld);
		
		// Transicion de eventHello -> finalState
	    estadoLlegada = "finalState";
	    if(!estados.containsKey(estadoLlegada)){
	    	estados.put(estadoFinalState, new State());
	    }
	    caracter = (char)consecutivo;
	    consecutivo++;
	    nombreEvento = "eventHello";
	    expressionEventHello = new DefaultEqualsExpression(new NamedEvent(nombreEvento));
	    if(!expressions.containsKey(expressionEventHello)){
	    	expressions.put(expressionEventHello, caracter);
	    }
	    BuchiTransition finalStateInitServer = new BuchiTransition(estados.get(estadoFinalState), estados.get(estadoLlegada), expressions.get(expressionEventHello), expressionEventHello);
	    transitionSet.add(finalStateInitServer);
		// Estado final FinalState
		estados.get(estadoFinalState).setAccept(true);
		estadosFinales.add(estados.get(estadoFinalState));

		return inicial;
	}
}
