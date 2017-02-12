package pstl.musicxml.tools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import pstl.musicxml.Measure;
import pstl.musicxml.Part;
import pstl.musicxml.Score;
import pstl.musicxml.parsing.ParseException;
import pstl.musicxml.parsing.XMLParser;
import pstl.musicxml.rhythmicstructures.RythmicTree;
import pstl.musicxml.rhythmicstructures.Signature;
import pstl.musicxml.rhythmicstructures.items.Chord;
import pstl.musicxml.rhythmicstructures.items.IMusicalItem;
import pstl.musicxml.rhythmicstructures.items.Note;
import pstl.musicxml.rhythmicstructures.items.Rest;

public class ScoreUtils {
	//MusicXML tag names
	private final static String MXL_SCORE_PARTWISE = "score-partwise";
	private final static String MXL_SCORE_TIMEWISE = "score-timewise";
	private final static String MXL_PART_LIST = "part-list";
	private final static String MXL_SCORE_PART = "score-part";
	private final static String MXL_ATT_ID = "id";
	private final static String MXL_PART_NAME =  "part-name";
	private final static String MXL_PART =  "part";
	private final static String MXL_MEASURE =  "measure";
	private final static String MXL_NUMBER = "number";
	private final static String MXL_ATTRIBUTES = "attributes";
	private final static String MXL_TIME = "time";
	private final static String MXL_BEATS = "beats";
	private final static String MXL_BEAT_TYPE = "beat-type";
	private final static String MXL_NOTE = "note";
	private final static String MXL_CHORD = "chord";
	private final static String MXL_PITCH = "pitch";
	private final static String MXL_REST = "rest";
	private final static String MXL_DURATION = "duration";
	private final static String MXL_STEP = "step";
	private final static String MXL_OCTAVE = "octave";
	
	
	
	//TODO Make to different method to handle both part-wise and time-wise scores. 
	//TODO throw a buttload of exceptions to handle every error.
	//XXX Should I test every error case ?
	
	public static Score loadFromDom(Document document) {
		Score result = new Score();
		Node root = getPartWiseRoot(document);
		Node partList = getPartList(root);
		
		ArrayList<Node> partsDeclaration = getChildNodeByName(partList, MXL_SCORE_PART);
		ArrayList<Node> parts = getChildNodeByName(root, MXL_PART);
		
		for (Node scorePart : partsDeclaration) {
			result.getParts().add(createPartFromScorePart(scorePart));
		}
		
		for (Node part : parts) {
			fillParts(part, result);
		}
		

		return result;
	}
	
	private static void fillParts(Node partNode, Score score) {
		Part part = score.getPartById(partNode.getAttributes().getNamedItem(MXL_ATT_ID).getNodeValue());
		for (Node measure : getChildNodeByName(partNode, MXL_MEASURE)) {
			part.getMeasures().add(loadMeasures(measure));
		}
	}
	
	private static Measure loadMeasures(Node measure) {
		int number = Integer.parseInt(measure.getAttributes().getNamedItem(MXL_NUMBER).getNodeValue());
		Measure result = new Measure(number);

		ArrayList<Node> attributes = getChildNodeByName(measure, MXL_ATTRIBUTES);
		Signature s = null;
		for (Node att : attributes) {
			s = loadSignature(att);
			if (s != null)
				break;
		}
		
		//TODO throw an exception when s is null.
		
		result.setSignature(s);
		
		//TODO load chords.
		ArrayList<Node> notes = getChildNodeByName(measure, MXL_NOTE);
		boolean isChord = false;
		boolean containsChord;
		Chord crtChord = null;
		IMusicalItem crtItem;
		for (Node noteNode : notes) {
			crtItem = loadMuscialItem(noteNode);
			
			
			containsChord = containsNode(noteNode, MXL_CHORD);
			
			if (containsChord && !isChord) {
				isChord = true;
				crtChord = new Chord(crtItem.getDuration());
				crtChord.addItem(crtItem);
			} else if (containsChord && isChord) {
				crtChord.addItem(crtItem);
			} else if (!containsChord && isChord) {
				isChord = false;
				result.addChord(crtChord);
				
				crtChord = new Chord(crtItem.getDuration());
				crtChord.addItem(crtItem);
				
				result.addChord(crtChord);
			} else {
				
				crtChord = new Chord();
				crtChord.addItem(crtItem);
				result.addChord(crtChord);
				
			}
			
		}
		
		if (isChord)
			result.addChord(crtChord);
		
		return result;
	}
	
	private static IMusicalItem loadMuscialItem(Node noteNode) {
		int duration = Integer.parseInt(getSingleChildByName(noteNode, MXL_DURATION).getTextContent());
		
		if (containsNode(noteNode, MXL_REST)) {
			
			return new Rest(duration);
		} else {
			Node pitchNode = getSingleChildByName(noteNode, MXL_PITCH);
			String step = getSingleChildByName(pitchNode, MXL_STEP).getTextContent();
			int octave = Integer.parseInt(getSingleChildByName(pitchNode, MXL_OCTAVE).getTextContent());

			return new Note(step, octave, duration);
		}
	}
	
	private static boolean containsNode(Node n, String nodeName) {
		return (getSingleChildByName(n, nodeName) != null)? true : false;
	}
	
	private static Signature loadSignature(Node attributes) {
		Node time = getSingleChildByName(attributes, MXL_TIME);
		
		if (time == null)
			return null;
		int beats;
		int beatType;
		
		
		beats = Integer.parseInt(getSingleChildByName(time, MXL_BEATS).getTextContent());
		beatType = Integer.parseInt(getSingleChildByName(time, MXL_BEAT_TYPE).getTextContent());
		
		return new Signature(beats, beatType);
	}
	

	private static Node getRoot(Document d, String rootName) {
		NodeList children = d.getChildNodes();

		for (int i = 0; i < children.getLength(); i++) {
			if (children.item(i).getNodeName().equals(rootName))
				return children.item(i);
		}

		return null;
	}

	private static Node getPartWiseRoot(Document d) {
		return getRoot(d, MXL_SCORE_PARTWISE);
	}

	private static Node getTimeWiseRoot(Document d) {
		return getRoot(d, MXL_SCORE_TIMEWISE);
	}


	private static Node getPartList(Node root) {
		return getChildNodeByName(root, MXL_PART_LIST).get(0);
	}

	private static ArrayList<Node> getChildNodeByName(Node n, String childName) {
		ArrayList<Node> result = new ArrayList<>();
		NodeList children = n.getChildNodes();

		for (int i = 0; i < children.getLength(); i++) {
			if (children.item(i).getNodeName().equals(childName))
				result.add(children.item(i));
		}

		return result;
	}
	
	private static Node getSingleChildByName(Node n, String childName) {
		NodeList children = n.getChildNodes();

		for (int i = 0; i < children.getLength(); i++) {
			if (children.item(i).getNodeName().equals(childName))
				return  children.item(i);
		}
		
		return null;
	}

	private static Part createPartFromScorePart(Node scorePart) {
		String name, id;

		id = scorePart.getAttributes().getNamedItem(MXL_ATT_ID).getNodeValue();


		name = getChildNodeByName(scorePart, MXL_PART_NAME).get(0).getTextContent();
		return new Part(id, name);
	}

	public static void main(String[] args) {
		XMLParser parser = new XMLParser();
		File rng;
		try {
//			String input = "/home/alexandre/git/pstl_musicxml/musicxml/test-data/simple/helloworld.mxl";
//			String input = "/home/alexandre/git/pstl_musicxml/musicxml/test-data/chorales.all.musicxml/bwv0254.krn.xml";
			String testDir = "/home/alexandre/git/pstl_musicxml/musicxml/test-data/chorales.all.musicxml/";
			String pattern = ".*\\.(xml|mxl)";
			
			

			rng = new File("/home/alexandre/git/pstl_musicxml/musicxml/grammars/rng/musicXML.rng");
			parser.setRng(rng);
			
			Collection<File> files = FileUtils.getFileList(testDir);
			ArrayList<Score> scores = new ArrayList<>();
			Document crtDoc;
			Score crtScore;
			int i = 0;
			int skip = 0;
			
			for (File f : files) {
				
				if (!f.getAbsolutePath().matches(pattern)) {
					skip++;
					continue;
				}
					
					
					
				parser.setInput(f.getAbsolutePath());
				try {
					crtDoc = parser.getDocument();
					crtScore = loadFromDom(crtDoc);
					
					scores.add(crtScore);
					
					i++;
					if (i%10 == 0)
						System.out.println((int)(((i*1.0)/(files.size() * 1.0))*100) + "% done");
					
				} catch (ParseException e) {
					System.out.println("Can't parse " + f);
				}
				
			}
			
			System.out.println(scores.size() + "/" + files.size() + " score succesfully parsed.");
			System.out.println(skip + " files skipped.");
//			for (Score s : scores) {
//				System.out.println(s);
//			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
