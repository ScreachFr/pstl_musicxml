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
import pstl.musicxml.Type;
import pstl.musicxml.parsing.ParseException;
import pstl.musicxml.parsing.XMLParser;
import pstl.musicxml.rhythmicstructures.RhythmicThreeFactory;
import pstl.musicxml.rhythmicstructures.RhythmicTree;
import pstl.musicxml.rhythmicstructures.Signature;
import pstl.musicxml.rhythmicstructures.items.Chord;
import pstl.musicxml.rhythmicstructures.items.IMusicalItem;
import pstl.musicxml.rhythmicstructures.items.Note;
import pstl.musicxml.rhythmicstructures.items.Rest;
import pstl.musicxml.symboles.unary.*;

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
	private final static String MXL_TYPE = "type";



	//TODO Make to different method to handle both part-wise and time-wise scores. 
	//Update : time-wise scores don't seems really wildly used. IMO we don't need to implement that right now. 
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
			crtItem = loadMusicalItem(noteNode);

			containsChord = containsNode(noteNode, MXL_CHORD);

			if (containsChord && !isChord) {
				isChord = true;
				crtChord = new Chord(crtItem.getType());
				crtChord.addItem(crtItem);
			} else if (containsChord && isChord) {
				crtChord.addItem(crtItem);
			} else if (!containsChord && isChord) {
				isChord = false;
				result.addChord(crtChord);

				crtChord = new Chord(crtItem.getType());
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

	private static IMusicalItem loadMusicalItem(Node noteNode) {
		Type type  = getTypeFromMXLType(getSingleChildByName(noteNode, MXL_TYPE).getTextContent());
		
		
		
		if (containsNode(noteNode, MXL_REST)) {

			return new Rest(type);
		} else {
			Node pitchNode = getSingleChildByName(noteNode, MXL_PITCH);
			String step = getSingleChildByName(pitchNode, MXL_STEP).getTextContent();
			int octave = Integer.parseInt(getSingleChildByName(pitchNode, MXL_OCTAVE).getTextContent());
			Note result = new Note(step, octave, type);
			
			Node alterNode = getSingleChildByName(pitchNode, Alter.getTrigger());
			
			if (alterNode != null) {
				result.addExtraSymbol(new Alter(Integer.parseInt(alterNode.getTextContent())));
			}
			
			lookForExtraSymbols(result, noteNode);
			
			return result;
		}
	}
	
	
	//XXX You can add new symbols here.
	private static void lookForExtraSymbols(Note note, Node noteNode) {
		NodeList cList = noteNode.getChildNodes();
		
		Node crtNode;
		String nodeName;
		for (int i = 0; i < cList.getLength(); i++) {
			crtNode = cList.item(i);
			nodeName = crtNode.getNodeName();
			if (nodeName.equals(Dot.getTrigger())) {
				note.addExtraSymbol(Dot.getDot());
			}
		}
		
		
	}
	
	private static Type getTypeFromMXLType(String mxlType) {
		if (mxlType.equals(Type.WHOLE.getMxlEquivalent())) {
			return Type.WHOLE;
		} else if (mxlType.equals(Type.EIGHTH.getMxlEquivalent())) {
			return Type.EIGHTH;
		} else if (mxlType.equals(Type.QUARTER.getMxlEquivalent())) {
			return Type.QUARTER;
		} else if (mxlType.equals(Type.HALF.getMxlEquivalent())) {
			return Type.HALF;
		} else if (mxlType.equals(Type.SIXTEENTH.getMxlEquivalent())) {
			return Type.SIXTEENTH;
		} else if (mxlType.equals(Type.THIRTY_SECOND.getMxlEquivalent())) {
			return Type.THIRTY_SECOND;
		} else if (mxlType.equals(Type.SIXTY_FOURTH.getMxlEquivalent())) {
			return Type.SIXTY_FOURTH;
		} else if (mxlType.equals(Type.HUNDRED_TWENTY_EIGHT.getMxlEquivalent())) {
			return Type.HUNDRED_TWENTY_EIGHT;
		} else {
			return Type.UNKNOWN;
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
	
	//XXX Timewise scores are not really popular it seems. Will implement that later.
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
//			String testDir = "/home/alexandre/git/pstl_musicxml/musicxml/test-data/xmlsamples";
			String pattern = ".*\\.(xml|mxl)";
//			String pattern = ".*\\.(xml)";



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
					System.out.println("Parsing " + f.getAbsolutePath());
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
			ArrayList<ArrayList<RhythmicTree>> rts = new ArrayList<>();

			for (Score score : scores) {
				System.out.println(score);
			}

			for (Score s : scores) {
				rts.add(RhythmicThreeFactory.buildRtFromScore(s));
			}

			for (ArrayList<RhythmicTree> arrayList : rts) {
				for (RhythmicTree rt : arrayList) {
					System.out.println(rt);
				}
			}





		} catch (IOException e) {
			e.printStackTrace();
		}

	}

//	public static void main(String[] args) {
//		XMLParser parser = new XMLParser();
//		File rng;
//		String input = "/home/alexandre/git/pstl_musicxml/musicxml/test-data/simple/simpleTest.mxl";
//
//
//
//		rng = new File("/home/alexandre/git/pstl_musicxml/musicxml/grammars/rng/musicXML.rng");
//		try {
//			parser.setRng(rng);
//			parser.setInput(input);
//			Document crtDoc;
//			Score crtScore;
//
//			crtDoc = parser.getDocument();
//			crtScore = ScoreUtils.loadFromDom(crtDoc);
//			
//			ArrayList<RhythmicTree> rts = RhythmicThreeFactory.buildRtFromScore(crtScore);
//			System.out.println(crtScore);
//			for (RhythmicTree rt : rts) {
//				System.out.println(rt);
//			}
//			
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
}



