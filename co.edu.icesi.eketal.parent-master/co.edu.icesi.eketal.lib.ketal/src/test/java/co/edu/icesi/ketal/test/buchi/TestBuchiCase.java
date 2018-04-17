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
			Set<co.edu.icesi.ketal.core.Transition> transitionSet = new HashSet();
			// map de expresiones con caracteres
			Hashtable<co.edu.icesi.ketal.core.Expression, Character> expressions = new Hashtable();
			co.edu.icesi.ketal.core.State initial = initialize(transitionSet, estadosFinales, expressions);
			return new TestBuchiCase(transitionSet, initial, estadosFinales, expressions);
		} else {
			return instance;
		}
	}

	public boolean evaluate(final co.edu.icesi.ketal.core.Event event) {
		if (event instanceof CharEvent) {
			return super.evaluate(event);
		}
		return false;
	}

	private TestBuchiCase(final Set<co.edu.icesi.ketal.core.Transition> transitions,
			final co.edu.icesi.ketal.core.State begin, final Set<co.edu.icesi.ketal.core.State> finalStates,
			final Hashtable<co.edu.icesi.ketal.core.Expression, Character> expressions) {
		//TODO fix this super
		//super(transitions, begin, finalStates, expressions);
		initializeAutomaton();
		instance = this;
	}

	private static co.edu.icesi.ketal.core.State initialize(final Set<co.edu.icesi.ketal.core.Transition> transitionSet,
			final Set<co.edu.icesi.ketal.core.State> estadosFinales,
			final Hashtable<co.edu.icesi.ketal.core.Expression, Character> expressions) {
		
		/*
		 * <>[]eventHello
		 * RES = S0,
		 * S1=(eventHello-> S1),
		 * S0=(TRUE-> S0 |eventHello-> S1).
		 * AS = { S1 }
		 */
		
		// Relación evento caracter
		Map<String, Character> mapping = new TreeMap<String, Character>();
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
		// Definición del estado: middleState
		String estadoMiddleState = "middleState";
		estados.put(estadoMiddleState, new co.edu.icesi.ketal.core.State());
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
		if (!mapping.containsKey(nombreEvento)) {
			mapping.put(nombreEvento, caracter);
			expressions.put(new DefaultEqualsExpression(new NamedEvent(nombreEvento)), mapping.get(nombreEvento));
		}
		co.edu.icesi.ketal.core.Transition firstStateEventHello = new co.edu.icesi.ketal.core.Transition(
				estados.get(estadoFirstState), estados.get(estadoLlegada), mapping.get(nombreEvento));
		transitionSet.add(firstStateEventHello);

		// Transicion de eventWorld -> middleState
		estadoLlegada = "middleState";
		if (!estados.containsKey(estadoLlegada)) {
			estados.put(estadoFirstState, new co.edu.icesi.ketal.core.State());
		}
		caracter = (char) consecutivo;
		consecutivo++;
		nombreEvento = "eventWorld";
		if (!mapping.containsKey(nombreEvento)) {
			mapping.put(nombreEvento, caracter);
			expressions.put(new DefaultEqualsExpression(new NamedEvent(nombreEvento)), mapping.get(nombreEvento));
		}
		co.edu.icesi.ketal.core.Transition firstStateEventWorld = new co.edu.icesi.ketal.core.Transition(
				estados.get(estadoFirstState), estados.get(estadoLlegada), mapping.get(nombreEvento));
		transitionSet.add(firstStateEventWorld);

		// Transicion de otherEvent -> finalState
		estadoLlegada = "finalState";
		if (!estados.containsKey(estadoLlegada)) {
			estados.put(estadoMiddleState, new co.edu.icesi.ketal.core.State());
		}
		caracter = (char) consecutivo;
		consecutivo++;
		nombreEvento = "otherEvent";
		if (!mapping.containsKey(nombreEvento)) {
			mapping.put(nombreEvento, caracter);
			expressions.put(new DefaultEqualsExpression(new NamedEvent(nombreEvento)), mapping.get(nombreEvento));
		}
		co.edu.icesi.ketal.core.Transition middleStateOtherEvent = new co.edu.icesi.ketal.core.Transition(
				estados.get(estadoMiddleState), estados.get(estadoLlegada), mapping.get(nombreEvento));
		transitionSet.add(middleStateOtherEvent);

		// Transicion de otherEvent -> finalState
		estadoLlegada = "finalState";
		if (!estados.containsKey(estadoLlegada)) {
			estados.put(estadoFinalState, new co.edu.icesi.ketal.core.State());
		}
		caracter = (char) consecutivo;
		consecutivo++;
		nombreEvento = "otherEvent";
		if (!mapping.containsKey(nombreEvento)) {
			mapping.put(nombreEvento, caracter);
			expressions.put(new DefaultEqualsExpression(new NamedEvent(nombreEvento)), mapping.get(nombreEvento));
		}
		co.edu.icesi.ketal.core.Transition finalStateOtherEvent = new co.edu.icesi.ketal.core.Transition(
				estados.get(estadoFinalState), estados.get(estadoLlegada), mapping.get(nombreEvento));
		transitionSet.add(finalStateOtherEvent);

		// Estado final FinalState
		estados.get(estadoFinalState).setAccept(true);
		estadosFinales.add(estados.get(estadoFinalState));

		return inicial;
	}
}
