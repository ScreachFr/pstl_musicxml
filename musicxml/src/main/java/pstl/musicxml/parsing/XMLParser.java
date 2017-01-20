package pstl.musicxml.parsing;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import pstl.musicxml.tools.FileUtils;

import com.thaiopensource.validate.ValidationDriver;

public class XMLParser {
	private File rngFile;
	private String input;
	private String dtdDir;

	public void setRng(File rngFile) throws IOException {
		if (!rngFile.exists())
			throw new IOException(rngFile + " does not exist!");		
		this.rngFile = rngFile;
	}

	public void setInput(String inputPath) {
		this.input = inputPath;
	}

	public void setDtdDir(String dtdDir) {
		this.dtdDir = dtdDir;
	}
	
	
	public Document getDocument() throws ParseException {
		try {
			Document result;
			final String inputText = FileUtils.getTextFromFile(input);
			final String rngAbsPath = rngFile.getAbsolutePath();
			final InputSource inputSource = ValidationDriver.fileInputSource(rngAbsPath);
			final ValidationDriver vd = new ValidationDriver();
			
			
			
			vd.loadSchema(inputSource);
			InputSource is = new InputSource(new StringReader(inputText));
			
			if (!vd.validate(is)) {
				throw new ParseException("Invalid xml :(");
			}

			final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			final DocumentBuilder db = dbf.newDocumentBuilder();

			is = new InputSource(new StringReader(inputText));
			
			
			result = db.parse(is);
			
			
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			throw new ParseException("Error while loading input");
		} catch (SAXException e) {
			e.printStackTrace();
			throw new ParseException("Error while loading rng to ValidationDriver");
		} catch (ParserConfigurationException e) {
			throw new ParseException("Error while configuring parser");
		}
	}

	public static void main(String[] args) {

		XMLParser parser = new XMLParser();
		File rng;
		try {
			//Bad!
			System.setProperty("http.agent", "Mozilla/5.0 (X11; Linux x86_64; rv:47.0) Gecko/20100101 Firefox/47.0");
			
			parser.setInput("/home/alexandre/git/pstl_musicxml/test-data/simple/helloworld.mxl");

			rng = new File("/home/alexandre/git/pstl_musicxml/musicxml/grammars/rng/musicXML.rng");
			parser.setRng(rng);
			
			
			Document doc = parser.getDocument();
			NodeList nl = doc.getDocumentElement().getChildNodes();
			
			for (int i = 0; i < nl.getLength(); i++) {
				System.out.println(nl.item(i));
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}
}
