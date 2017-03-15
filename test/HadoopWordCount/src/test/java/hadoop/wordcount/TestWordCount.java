package hadoop.wordcount;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Random;

import org.junit.Test;

public class TestWordCount {

	@Test
	public void test() {
		Random ran = new Random();
	    String[] args = {"\\./1728.txt","out"+ran.nextInt(100)};
	    final InputStream original = System.in;
	    try {
			WordCount2.main(args);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	    System.setIn(original);
	}
	//Sin: 3497
	//Con: 29302
	//32368
}
