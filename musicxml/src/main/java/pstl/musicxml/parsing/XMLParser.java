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
	private static int id = 0;
	
	private static String DEFAULT_STYLE = "node { "
			+ "text-background-color: white;"
			+ "shape : box;"
			+ "fill-color : red;"
			+ "text-size : 15px;"
			+ "text-padding : 5px;"
			+ "size : 15px;"
			+ "z-index : 1; } "
			+ "edge {"
+ "fill-color : red;}";
	
	
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

	
	public static void displayDocument(Document doc) {
		Element root = doc.getDocumentElement();
		System.out.println(displayNode(root, 0));
	}
	
	private static String displayNode(Node n, int level) {
		String result = "";
		String tabs = getTabs(level);
		NodeList childs = n.getChildNodes();
		result += tabs + "(" + n.getNodeName() + ") : {\n";
		
		level++;
		
		for (int i = 0; i < childs.getLength(); i++) {
			result += displayNode(childs.item(i), level);
		}
		
		if (n.getNodeName().equals("#text")) 
			result += tabs + "Content = \"" + n.getNodeValue().replace("\n", "").replace("\t", "")+ "\"\n";
		
		result += tabs + "}\n";
		
		return result;
	}
	
	
	private static String getTabs(int n) {
		String result = "";
		
		for (int i = 0; i < n; i++) {
			result += "\t";
		}
		
		return result;
	}
	
	public static void displayDocumentWithGraphStream(Document doc) {
		SingleGraph graph = new SingleGraph("Document");
		graph.addAttribute("ui.stylesheet", DEFAULT_STYLE);
		addNodeToGraph(graph, doc);
		
		graph.display();
	}
	
	private static org.graphstream.graph.Node addNodeToGraph(Graph g, Node n) {
		org.graphstream.graph.Node gNode = g.addNode(n.getNodeName() + " #" + id++);
		org.graphstream.graph.Node crtNode;
		NodeList childs = n.getChildNodes();
		String label = n.getNodeName();
		
		if (n.getNodeName().equals("#text")) 
			label += ": " + n.getNodeValue().replace("\n", "").replace("\t", "");
		
		setLabel(gNode, label);
		
		for (int i = 0; i < childs.getLength(); i++) {
			crtNode = addNodeToGraph(g, childs.item(i));
			g.addEdge("edge#" + id++, crtNode, gNode);
		}
			
		
		return gNode;
	}

	private static void setLabel(org.graphstream.graph.Node n, String l) {
		n.setAttribute("label", l);
	}
	
	
	
	public static void main(String[] args) {

		XMLParser parser = new XMLParser();
		File rng;
		try {
			//Bad!
//			System.setProperty("http.agent", "Mozilla/5.0 (X11; Linux x86_64; rv:47.0) Gecko/20100101 Firefox/47.0");
			
			parser.setInput("/home/alexandre/git/pstl_musicxml/musicxml/test-data/simple/helloworld.mxl");

			rng = new File("/home/alexandre/git/pstl_musicxml/musicxml/grammars/rng/musicXML.rng");
			parser.setRng(rng);
			
			
			Document doc = parser.getDocument();
			XMLParser.displayDocument(doc);
			XMLParser.displayDocumentWithGraphStream(doc);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		
	}
}
