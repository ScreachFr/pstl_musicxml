package pstl.musicxml;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import pstl.musicxml.parsing.ParseException;
import pstl.musicxml.parsing.XMLParser;

@RunWith(Parameterized.class)
public class ParserTest {
//	private static String[] testDir = {"test-data/chorales.all.musicxml", "test-data/simple"};
	private static String[] testDir = {"test-data/customfiles"};
	private static String pattern = ".*\\.(xml|mxl)";
	private static File rng = new File("grammars/rng/musicXML.rng");
	private static XMLParser parser;

	private File file;


	public ParserTest(final File file) {
		this.file = file;
	}

	@Test
	public void processFile() throws  ParseException, IOException {
		setup(file);
		testFile(file);
	}


	private void setup(File file) {
		parser = new XMLParser();
		try {
			parser.setRng(rng);
			parser.setInput(file.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void testFile(File file) throws ParseException {
		System.err.println("Testing " + file.getAbsolutePath() + " ...");
		assertTrue(file.exists());
		validateFile(file);
	}


	public void validateFile(File file) throws ParseException {
		parser.getDocument();
	}

	@Parameters(name = "{0}")
	public static Collection<File[]> data() throws Exception {
		Collection<File[]> result = new ArrayList<>();
		
		for (String s : testDir) {
			result.addAll(ParserTest.getFileListForTest(s));
		}
		
		return result;
	} 
	
	public static Collection<File[]> getFileListForTest(String dir) {
		Collection<File[]> result = new ArrayList<>();
		
		File d = new File(dir);
		
		System.out.println(d.getAbsolutePath());
		
		if (!d.isDirectory())
			return null;
		
		String[] files = d.list();
		
		for (String s : files) {
			if(s.matches(pattern))
				result.add(new File[]{new File(d.getAbsolutePath() + "/" + s)});
		}
		
		return result;
	}

}
