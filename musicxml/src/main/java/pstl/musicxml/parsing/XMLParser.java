package pstl.musicxml.parsing;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
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
	private Pattern dtdPattern;

	public final static String REGEX_DTD = "(<!DOCTYPE (score-partwise|score-timewise) PUBLIC)(\\s)*(\")([a-z A-Z]|-|\\/|[0-9]|\\.)*(\")(\\s)*(\")([a-z A-Z]|:|\\/|\\.)*(\")(\\s)*(>)";

	public XMLParser() {
		dtdPattern = Pattern.compile(REGEX_DTD);
	}


	public void setRng(File rngFile) throws IOException {
		if (!rngFile.exists())
			throw new IOException(rngFile + " does not exist!");		
		this.rngFile = rngFile;
	}

	public void setInput(String inputPath) {
		this.input = inputPath;
	}


	public Document getDocument() throws ParseException {
		try {
			Document result;
			String inputText = FileUtils.getTextFromFile(input);
			inputText = removeDTD(inputText);
			final String rngAbsPath = rngFile.getAbsolutePath();
			final InputSource inputSource = ValidationDriver.fileInputSource(rngAbsPath);
			final ValidationDriver vd = new ValidationDriver();
			vd.loadSchema(inputSource);

			InputSource is = new InputSource(new StringReader(inputText));

			if (!vd.validate(is)) {
				throw new ParseException("Invalid xml :(");
			}

			System.out.println("Validation done");

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

	private String removeDTD(String input) {
		Matcher matcher = dtdPattern.matcher(input);


		return matcher.replaceAll("");
	}



	public static void main(String[] args) {

		XMLParser parser = new XMLParser();
		File rng;
		try {
			//Bad!
			//			System.setProperty("http.agent", "Mozilla/5.0 (X11; Linux x86_64; rv:47.0) Gecko/20100101 Firefox/47.0");

//			parser.setInput("/home/alexandre/git/pstl_musicxml/musicxml/test-data/simple/helloworld.mxl");
			parser.setInput("/home/alexandre/git/pstl_musicxml/musicxml/test-data/chorales.all.musicxml/bwv0254.krn.xml");
			

			rng = new File("/home/alexandre/git/pstl_musicxml/musicxml/grammars/rng/musicXML.rng");
			parser.setRng(rng);


			Document doc = parser.getDocument();
			DomVisualizer.displayDocument(doc);
			DomVisualizer.displayDocumentWithGraphStream(doc);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}


	}
}
