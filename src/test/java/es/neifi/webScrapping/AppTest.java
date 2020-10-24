package es.neifi.webScrapping;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AppTest extends TestCase {

	public AppTest(String testName) {
		super(testName);
	}

	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	public void isFileCreated() {
		Huffingtonpost huffingtonpost_scrap = new Huffingtonpost("politica", 1);
		
		assertTrue(true);
	}
	
	
}
