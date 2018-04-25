package co.edu.icesi.ketal.test.buchi;

import co.edu.icesi.ketal.core.BuchiAutomaton;
import co.edu.icesi.ketal.core.BuchiTransition;
import co.edu.icesi.ketal.core.DefaultEqualsExpression;
import co.edu.icesi.ketal.core.Expression;
import co.edu.icesi.ketal.core.NamedEvent;
import co.edu.icesi.ketal.core.NotExpression;
import co.edu.icesi.ketal.core.Or;
import co.edu.icesi.ketal.core.State;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class UntilExample extends BuchiAutomaton {
	private static UntilExample instance;

	public static HashMap<String, State> estados = new HashMap<String, State>();

	/**
	 * []((out)->((!(initServer))U(initClient))) RES = S0, S0=(initClient-> S0
	 * |!initServer-> S1 |!out-> S0), S1=(initClient-> S0 |!initServer-> S1). AS
	 * = { S0 }
	 */
	public static BuchiAutomaton getInstance() {
		if (instance == null) {
			// lista de estados finales
			Set<State> estadosFinales = new HashSet();
			// conjunto de transiciones
			Set<BuchiTransition> transitionSet = new HashSet();
			// map de expresiones con caracteres
			Hashtable<Expression, Character> expressions = new Hashtable();
			State initial = initialize(transitionSet, estadosFinales, expressions);
			return new UntilExample(transitionSet, initial, estadosFinales, expressions);
		} else {
			return instance;
		}
	}

	private UntilExample(final Set<BuchiTransition> transitions, final State begin, final Set<State> finalStates,
			final Hashtable<Expression, Character> expressions) {
		super(transitions, begin, finalStates, expressions);
		super.findTransitionsCurrentState();
		instance = this;
	}

	private static State initialize(final Set<BuchiTransition> transitionSet, final Set<State> estadosFinales,
			final Hashtable<Expression, Character> expressions) {
		// Relación evento caracter
		Map<Expression, Character> mapping = new TreeMap<Expression, Character>();
		// Estado inicial
		State inicial = null;

		int consecutivo = 65;
		Character caracter = (char) consecutivo;
		Expression expression;
		String estadoLlegada = "";

		// Definición del estado: S0
		String estadoS0 = "S0";
		estados.put(estadoS0, new State());
		// Definición del estado: S1
		String estadoS1 = "S1";
		estados.put(estadoS1, new State());
		// Estado inicial: S1
		inicial = estados.get(estadoS0);

		// Transicion initClient|!out->S0
		estadoLlegada = "S0";
		if (!estados.containsKey(estadoLlegada)) {
			estados.put(estadoS0, new State());
		}
		caracter = (char) consecutivo;
		consecutivo++;
		expression = new Or(new DefaultEqualsExpression(new NamedEvent("initClient")),
				new NotExpression(new DefaultEqualsExpression(new NamedEvent("out"))));
		if (!expressions.containsKey(expression)) {
			expressions.put(expression, caracter);
		}
		BuchiTransition S0S0 = new BuchiTransition(estados.get(estadoS0), estados.get(estadoLlegada),
				expressions.get(expression), expression);
		transitionSet.add(S0S0);

		// Transicion !initServer->S1
		estadoLlegada = "S1";
		if (!estados.containsKey(estadoLlegada)) {
			estados.put(estadoS0, new State());
		}
		caracter = (char) consecutivo;
		consecutivo++;
		expression = new NotExpression(new DefaultEqualsExpression(new NamedEvent("initServer")));
		if (!expressions.containsKey(expression)) {
			expressions.put(expression, caracter);
		}
		BuchiTransition S0S1 = new BuchiTransition(estados.get(estadoS0), estados.get(estadoLlegada),
				expressions.get(expression), expression);
		transitionSet.add(S0S1);

		// Estado final S0
		estados.get(estadoS0).setAccept(true);
		estadosFinales.add(estados.get(estadoS0));
		// Transicion initClient->S0
		estadoLlegada = "S0";
		if (!estados.containsKey(estadoLlegada)) {
			estados.put(estadoS1, new State());
		}
		caracter = (char) consecutivo;
		consecutivo++;
		expression = new DefaultEqualsExpression(new NamedEvent("initClient"));
		if (!expressions.containsKey(expression)) {
			expressions.put(expression, caracter);
		}
		BuchiTransition S1S0 = new BuchiTransition(estados.get(estadoS1), estados.get(estadoLlegada),
				expressions.get(expression), expression);
		transitionSet.add(S1S0);

		// Transicion !initServer->S1
		estadoLlegada = "S1";
		if (!estados.containsKey(estadoLlegada)) {
			estados.put(estadoS1, new State());
		}
		caracter = (char) consecutivo;
		consecutivo++;
		expression = new NotExpression(new DefaultEqualsExpression(new NamedEvent("initServer")));
		if (!expressions.containsKey(expression)) {
			expressions.put(expression, caracter);
		}
		BuchiTransition S1S1 = new BuchiTransition(estados.get(estadoS1), estados.get(estadoLlegada),
				expressions.get(expression), expression);
		transitionSet.add(S1S1);

		return inicial;
	}

	public void destroy() {
		instance = null;
	}

}
