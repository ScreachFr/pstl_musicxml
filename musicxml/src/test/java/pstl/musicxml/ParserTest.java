package pstl.musicxml;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import pstl.musicxml.parsing.ParseException;
import pstl.musicxml.parsing.XMLParser;
import pstl.musicxml.tools.FileUtils;

public class ParserTest {
	private static String[] testDir = {"test-data/chorales.all.musicxml"};

	private static File rng = new File("grammars/rng/musicXML.rng");
	private static XMLParser parser;

	
	
	@Test
	public static void testFile() {
		Collection<File> data = ParserTest.data();
		
		parser = new XMLParser();
		
		
		for (File f : data) {
			setup(f);
			try {
				parser.getDocument();
			} catch (ParseException e) {
				e.printStackTrace();
				//Failure
				assertTrue(false);
			}
		}
		
		
		assertTrue(true);
	}


	private static void setup(File file) {
		parser = new XMLParser();
		try {
			parser.setRng(rng);
			parser.setInput(file.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Collection<File> data() {
		Collection<File> result = new ArrayList<>();

		for (String s : testDir) {
			result.addAll(FileUtils.getFileList(s));
		}

		return result;
	} 

}
