package co.edu.icesi.ketal.test.automatonNoRegEx;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;

import org.junit.Test;

import co.edu.icesi.ketal.core.Automaton;
import co.edu.icesi.ketal.core.DefaultEqualsExpression;
import co.edu.icesi.ketal.core.Event;
import co.edu.icesi.ketal.core.Expression;
import co.edu.icesi.ketal.core.NamedEvent;
import co.edu.icesi.ketal.core.State;
import co.edu.icesi.ketal.core.Transition;

public class TestAutomaton {
	
	private Automaton automata;
	
	@Test
	public void testAutamatonEvaluate(){
		initAutomaton();
		Event hello = new NamedEvent("eventoHello");
		Event world = new NamedEvent("eventoWorld");
		
		Vector<Event> eventos = new Vector<Event>();
		eventos.add(hello);
		eventos.add(world);
		eventos.add(hello);
		
		assertFalse(automata.isAWordAutomaton(eventos));
		eventos.add(hello);
		assertTrue(automata.isAWordAutomaton(eventos));
	}
	
	
	@Test
	public void testAutamatonStep(){
		initAutomaton();
		
		assertFalse(automata.evaluate(new NamedEvent("eventoWorld")));
		
		assertTrue(automata.evaluate(new NamedEvent("eventoHello")));
		assertTrue(automata.evaluate(new NamedEvent("eventoWorld")));
		assertTrue(automata.evaluate(new NamedEvent("eventoHello")));
		assertTrue(automata.evaluate(new NamedEvent("eventoHello")));
	}	
	
	private void initAutomaton() {
		//Relación evento caracter
		Map<String, Character> mapping = new TreeMap<String, Character>();
	    //Estado inicial
	    State inicial = null;
	    //lista de estados finales
	    Set<State> estadosFinales = new HashSet<State>();
	    //Conjunto de nombres y estados
	    Map<String, State> estados = new HashMap<String, State>();
	    
	    //map de eventos con transiciones
	    HashSet<Transition> transitionSet = new HashSet<Transition>();
	    
	    Hashtable<Expression,Character> expressions = new Hashtable<>();
	    
	    int consecutivo = 65;
	    Character caracter = (char)consecutivo;
	    String nombreEvento = "";
	    String estadoLlegada = "";
	    
	    //Definición del estado: firstState
	    String estadoFirstState = "firstState";
	    estados.put(estadoFirstState, new State());
	    //start start 1
	    //Estado inicial: firstState
	    inicial = estados.get(estadoFirstState);
	    //Definición del estado: middleState
	    String estadoMiddleState = "middleState";
	    estados.put(estadoMiddleState, new State());
	    //Definición del estado: finalState
	    String estadoFinalState = "finalState";
	    estados.put(estadoFinalState, new State());
	    //"Transiciones de " + eventoHello+" -> "+middleState
    	estadoLlegada = "middleState";
    	if(!estados.containsKey(estadoLlegada)){
    		estados.put(estadoFirstState, new State());
    	}
    	caracter = (char)consecutivo;
    	consecutivo++;
    	nombreEvento = "eventoHello";
    	if(!mapping.containsKey(nombreEvento)){	    		
    		mapping.put(nombreEvento, caracter);
    		expressions.put(new DefaultEqualsExpression(new NamedEvent(nombreEvento)), mapping.get(nombreEvento));
    	}
    	co.edu.icesi.ketal.core.Transition firstStateEventoHello = new Transition(estados.get(estadoFirstState), estados.get(estadoLlegada), mapping.get(nombreEvento));
    	transitionSet.add(firstStateEventoHello);
    	//"Transiciones de " + eventoHello+" -> "+finalState
    	estadoLlegada = "finalState";
    	if(!estados.containsKey(estadoLlegada)){
    		estados.put(estadoMiddleState, new State());
    	}
    	caracter = (char)consecutivo;
    	consecutivo++;
    	nombreEvento = "eventoHello";
    	if(!mapping.containsKey(nombreEvento)){
    		mapping.put(nombreEvento, caracter);	    		
    		expressions.put(new DefaultEqualsExpression(new NamedEvent(nombreEvento)), mapping.get(nombreEvento));
    	}
    	co.edu.icesi.ketal.core.Transition middleStateEventoHello = new Transition(estados.get(estadoMiddleState), estados.get(estadoLlegada), mapping.get(nombreEvento));
    	transitionSet.add(middleStateEventoHello);
    //"Transiciones de " + eventoWorld+" -> "+firstState
    	estadoLlegada = "firstState";
    	if(!estados.containsKey(estadoLlegada)){
    		estados.put(estadoMiddleState, new State());
    	}
    	caracter = (char)consecutivo;
    	consecutivo++;
    	nombreEvento = "eventoWorld";
    	if(!mapping.containsKey(nombreEvento)){
    		mapping.put(nombreEvento, caracter);	    		
    		expressions.put(new DefaultEqualsExpression(new NamedEvent(nombreEvento)), mapping.get(nombreEvento));
    	}
    	co.edu.icesi.ketal.core.Transition middleStateEventoWorld = new Transition(estados.get(estadoMiddleState), estados.get(estadoLlegada), mapping.get(nombreEvento));
    	transitionSet.add(middleStateEventoWorld);
	    //Estado final FinalState
    	estados.get(estadoFinalState).setAccept(true);
	    estadosFinales.add(estados.get(estadoFinalState));
	    Automaton automata = new Automaton(transitionSet, inicial, estadosFinales, expressions);
	    automata.initializeAutomaton();
	    this.automata = automata;
	}

}
