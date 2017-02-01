package pstl.musicxml.parsing;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomVisualizer {
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
	

}
