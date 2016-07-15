package test;

import static org.junit.Assert.*;

import org.junit.Test;

import co.edu.icesi.eketal.automaton.MiPrimerAuromata;
import co.edu.icesi.ketal.core.Automaton;
import co.edu.icesi.ketal.core.Event;
import co.edu.icesi.ketal.core.NamedEvent;

public class TestAutomaton {

	Automaton instance = MiPrimerAuromata.getInstance();

    @Test
    public void testCase(){
        Event eventHello = new NamedEvent("eventHello");
        Event eventWorld = new NamedEvent("eventWorld");


        instance.getCurrentState().toString();
        assertTrue(instance.evaluate(eventHello));
        instance.getCurrentState().toString();
        assertTrue(instance.evaluate(eventWorld));
        instance.getCurrentState().toString();
    }

}
