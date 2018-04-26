package co.edu.icesi.ketal.test.buchi;

import co.edu.icesi.ketal.core.And;
import co.edu.icesi.ketal.core.BuchiAutomaton;
import co.edu.icesi.ketal.core.BuchiTransition;
import co.edu.icesi.ketal.core.DefaultEqualsExpression;
import co.edu.icesi.ketal.core.Expression;
import co.edu.icesi.ketal.core.NamedEvent;
import co.edu.icesi.ketal.core.NotExpression;
import co.edu.icesi.ketal.core.State;
import co.edu.icesi.ketal.core.TrueExpression;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Property extends BuchiAutomaton {
  private static Property instance;
  
  public static HashMap<String, State> estados = new HashMap<String, State>();
  
  /**
   * RES = S0,
   * S0=(TRUE-> S1 |!otherEvent-> S0),
   * S1=(eventHello-> S1 |eventHello&!otherEvent-> S0).
   * AS = { S0, S1 }
   * 
   */
  public static BuchiAutomaton getInstance() {
    if(instance==null){
    	//lista de estados finales
    	Set<State> estadosFinales = new HashSet();
    	//conjunto de transiciones
    	  	Set<BuchiTransition> transitionSet = new HashSet();
    	//map de expresiones con caracteres
    	  	Hashtable<Expression, Character> expressions = new Hashtable();
    	  	State initial = initialize(transitionSet, estadosFinales, expressions);
    	return new Property(transitionSet, initial, estadosFinales, expressions);
    }else{
    	return instance;						
    }
  }
  
  private Property(final Set<BuchiTransition> transitions, final State begin, final Set<State> finalStates, final Hashtable<Expression, Character> expressions) {
    super(transitions, begin, finalStates, expressions);
    findTransitionsCurrentState();
    instance = this;
  }
  
  private static State initialize(final Set<BuchiTransition> transitionSet, final Set<State> estadosFinales, final Hashtable<Expression, Character> expressions) {
    //Relación evento caracter
    Map<Expression, Character> mapping = new TreeMap<Expression, Character>();
    //Estado inicial
    State inicial = null;
    
    int consecutivo = 65;
    Character caracter = (char)consecutivo;
    Expression expression;
    String estadoLlegada = "";
    
    //Definición del estado: S0
    String estadoS0 = "S0";
    estados.put(estadoS0, new State());
    //Estado inicial: S0
    inicial = estados.get(estadoS0);
    //Definición del estado: S1
    String estadoS1 = "S1";
    estados.put(estadoS1, new State());
    
    //Transicion TRUE->S1
    estadoLlegada = "S1";
    if(!estados.containsKey(estadoLlegada)){
    	estados.put(estadoS0, new State());
    }
    caracter = (char)consecutivo;
    consecutivo++;
    expression = new TrueExpression();
    if(!expressions.containsKey(expression)){
    	expressions.put(expression, caracter);
    }
    BuchiTransition S0S1 = new BuchiTransition(estados.get(estadoS0), estados.get(estadoLlegada), expressions.get(expression), expression);
    transitionSet.add(S0S1);
    
    //Transicion !otherEvent->S0
    estadoLlegada = "S0";
    if(!estados.containsKey(estadoLlegada)){
    	estados.put(estadoS0, new State());
    }
    caracter = (char)consecutivo;
    consecutivo++;
    expression = new NotExpression(new DefaultEqualsExpression(new NamedEvent("otherEvent")));
    if(!expressions.containsKey(expression)){
    	expressions.put(expression, caracter);
    }
    BuchiTransition S0S0 = new BuchiTransition(estados.get(estadoS0), estados.get(estadoLlegada), expressions.get(expression), expression);
    transitionSet.add(S0S0);
    
    
    //Estado final S0
    estados.get(estadoS0).setAccept(true);
    estadosFinales.add(estados.get(estadoS0));
    //Transicion eventHello->S1
    estadoLlegada = "S1";
    if(!estados.containsKey(estadoLlegada)){
    	estados.put(estadoS1, new State());
    }
    caracter = (char)consecutivo;
    consecutivo++;
    expression = new DefaultEqualsExpression(new NamedEvent("eventHello"));
    if(!expressions.containsKey(expression)){
    	expressions.put(expression, caracter);
    }
    BuchiTransition S1S1 = new BuchiTransition(estados.get(estadoS1), estados.get(estadoLlegada), expressions.get(expression), expression);
    transitionSet.add(S1S1);
    
    //Transicion eventHello&!otherEvent->S0
    estadoLlegada = "S0";
    if(!estados.containsKey(estadoLlegada)){
    	estados.put(estadoS1, new State());
    }
    caracter = (char)consecutivo;
    consecutivo++;
    expression = new And(new DefaultEqualsExpression(new NamedEvent("eventHello")),new NotExpression(new DefaultEqualsExpression(new NamedEvent("otherEvent"))));
    if(!expressions.containsKey(expression)){
    	expressions.put(expression, caracter);
    }
    BuchiTransition S1S0 = new BuchiTransition(estados.get(estadoS1), estados.get(estadoLlegada), expressions.get(expression), expression);
    transitionSet.add(S1S0);
    
    
    //Estado final S1
    estados.get(estadoS1).setAccept(true);
    estadosFinales.add(estados.get(estadoS1));
    return inicial;
  }
}
