package co.edu.icesi.ltl2buchi.translator;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;

public class ByteArrayPrinter extends PrintStream {

	public ByteArrayPrinter(ByteArrayOutputStream out) throws FileNotFoundException {
		super(out);
	}
}
