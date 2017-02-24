package pstl.musicxml;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.w3c.dom.Document;

import pstl.musicxml.musicalstructures.Part;
import pstl.musicxml.musicalstructures.Score;
import pstl.musicxml.parsing.ParseException;
import pstl.musicxml.parsing.XMLParser;
import pstl.musicxml.tools.ScoreUtils;

@RunWith(Parameterized.class)
public class ScoreUtilsTest {
	private static String[] testDir = {"test-data/customfiles"};
	private static String pattern = ".*\\.(xml|mxl)";
	private static File rng = new File("grammars/rng/musicXML.rng");
	private static XMLParser parser;

	private File file;

	public ScoreUtilsTest(final File file) {
		this.file = file;
	}
	
	// TODO : add result checking.

	@Test
	public void processFile() {
		try {
			setup(file);
			testFile(file);
		} catch(Exception e) {
			System.out.println("Test failure for " + file + ".");
			e.printStackTrace();
			assertTrue(false);
		}
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
		Document d = parser.getDocument();
		Score score = ScoreUtils.loadFromDom(d);
		
		for (Part p : score.getParts()) {
			System.out.println(p);
		}
		
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
