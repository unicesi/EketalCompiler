package co.edu.icesi.ltl2buchi.translator;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

import gov.nasa.ltl.exceptions.ParseErrorException;
import gov.nasa.ltl.graph.Graph;
import gov.nasa.ltl.graphio.Writer;
import gov.nasa.ltl.trans.LTL2Buchi;

public class BuchiTranslator {

	public static Graph<String> translate(String formula) throws ParseErrorException{
		return LTL2Buchi.translate(formula);
	}

	public static String translateToString(String formula) throws ParseErrorException, FileNotFoundException {
		Graph<String> buchi = LTL2Buchi.translate(formula);
		ByteArrayOutputStream array = new ByteArrayOutputStream();
		ByteArrayPrinter stringPrinter = new ByteArrayPrinter(array);
		Writer<String> w = Writer.getWriter (Writer.Format.FSP, stringPrinter);
		w.write (buchi);
		return array.toString();
	}

}
