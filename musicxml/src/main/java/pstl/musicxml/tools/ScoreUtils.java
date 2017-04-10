package pstl.musicxml.tools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.hamcrest.core.IsInstanceOf;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import pstl.musicxml.musicalstructures.Measure;
import pstl.musicxml.musicalstructures.Metronome;
import pstl.musicxml.musicalstructures.Part;
import pstl.musicxml.musicalstructures.Score;
import pstl.musicxml.musicalstructures.Signature;
import pstl.musicxml.musicalstructures.Type;
import pstl.musicxml.musicalstructures.items.Chord;
import pstl.musicxml.musicalstructures.items.IMusicalItem;
import pstl.musicxml.musicalstructures.items.Note;
import pstl.musicxml.musicalstructures.items.Rest;
import pstl.musicxml.musicalstructures.symbols.binary.Beam;
import pstl.musicxml.musicalstructures.symbols.binary.BinarySymbolFactory;
import pstl.musicxml.musicalstructures.symbols.binary.Slur;
import pstl.musicxml.musicalstructures.symbols.binary.Slur.SlurType;
import pstl.musicxml.musicalstructures.symbols.unary.Alter;
import pstl.musicxml.musicalstructures.symbols.unary.Dot;
import pstl.musicxml.musicalstructures.symbols.unary.FermataNotation;
import pstl.musicxml.musicalstructures.symbols.unary.Grace;
import pstl.musicxml.musicalstructures.symbols.unary.InvertedMordent;
import pstl.musicxml.musicalstructures.symbols.unary.Mordent;
import pstl.musicxml.musicalstructures.symbols.unary.Snap_pizzicato;
import pstl.musicxml.musicalstructures.symbols.unary.Spiccato;
import pstl.musicxml.musicalstructures.symbols.unary.Staccatissimo;
import pstl.musicxml.musicalstructures.symbols.unary.Staccato;
import pstl.musicxml.musicalstructures.symbols.unary.Tenuto;
import pstl.musicxml.musicalstructures.symbols.unary.Tremolo;
import pstl.musicxml.musicalstructures.symbols.unary.TrillMark;
import pstl.musicxml.musicalstructures.symbols.unary.Turn;
import pstl.musicxml.parsing.ParseException;
import pstl.musicxml.parsing.XMLParser;
import pstl.musicxml.rhythmicstructures.RhythmicTreeFactory;
import pstl.musicxml.rhythmicstructures.RhythmicTree;

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
	private final static String MXL_NOTATIONS = "notations";
	private final static String MXL_ORNAMENTS = "ornaments";
	private final static String MXL_ARTICULATIONS = "articulations";
	private final static String MXL_TECHNICAL= "technical";
	private final static String MXL_SLUR= "slur";
	private final static String MXL_LONG= "long";
	private final static String MXL_SLASH= "slach";
	
	private final static String MXL_METRONOME_BEAT_UNIT = "beat-unit";
	private final static String MXL_METRONOME_PER_MINUTE = "per-minute"; 
	private final static String MXL_METRONOME_BEAT_UNIT_DOT = "beat-unit-dot";
	
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
		boolean containsChord;
		Chord crtChord = null;
		IMusicalItem crtItem;
		Note crtNote;
		for (Node noteNode : notes) {
//			crtItem = loadMusicalItem(noteNode);
//
//			containsChord = containsNode(noteNode, MXL_CHORD);
//
//			if (!(crtItem instanceof Note)) {
//				result.addItem(crtItem);
//			} else {
//				crtNote = (Note) crtItem;
//				if (containsChord && !isChord) { // contains a chord tag unlike the previous note.
//					isChord = true;
//					crtChord = new Chord(crtItem.getType());
//					crtChord.addNote(crtNote);
//				} else if (containsChord && isChord) { // chord continuation
//					crtChord.addNote(crtNote);
//				} else if (!containsChord && isChord) {
//					isChord = false;
//					result.addItem(crtChord);
//
//					crtChord = new Chord(crtItem.getType());
//					crtChord.addNote(crtNote);
//
//					result.addItem(crtChord);
//				}  else {
//
//					crtChord = new Chord();
//					crtChord.addNote(crtNote);
//					result.addItem(crtChord);
//
//				}
//				lastNote = crtNote;
//			}
			
			
			crtItem = loadMusicalItem(noteNode);
			containsChord = containsNode(noteNode, MXL_CHORD);
			
			if (!(crtItem instanceof Note)) {
				result.addItem(crtItem);
			} else {
				crtNote = (Note) crtItem;
				
				if (!containsChord) {
					if (crtChord != null)
						result.addItem(crtChord);
					
					crtChord = new Chord(crtNote.getType());
					crtChord.addNote(crtNote);
				} else {
					crtChord.addNote(crtNote);
				}
				
			}
			
		}

		if (!crtChord.isEmpty())
			result.addItem(crtChord);

		return result;
	}

	private static IMusicalItem loadMusicalItem(Node noteNode) {
		Type type  = getTypeFromMXLType(getSingleChildByName(noteNode, MXL_TYPE).getTextContent());

		if (containsNode(noteNode, MXL_REST)) { //Is a rest

			return new Rest(type);
		} else { // Is a note.
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
			if (nodeName.equals(Dot.getTrigger())) { // dot
				note.addExtraSymbol(Dot.getDot());
			} else if (nodeName.equals(Beam.getTrigger())) { // beam
				note.addExtraSymbol(buildBeamFromNod(crtNode));
			} else if (nodeName.equals(Grace.getTrigger())) { // grace
				Element e = (Element)crtNode;
				String slash = e.getAttribute(MXL_SLASH);
				boolean isSlashed = false;
				if(slash.equals("yes"))
					isSlashed = true;
				
				Grace grace = Grace.getGrace();
				grace.setSlash(isSlashed);
				
				note.addExtraSymbol(grace);
			} else if(nodeName.equals(MXL_NOTATIONS)){ // notations
				
				addNotations(note, crtNode);
				
			}

			//add others note node
		}
	}


	public static void addNotations(Note note, Node notationsNode){
		NodeList childsList = notationsNode.getChildNodes();
		Node childNotationsNode;
		String childName;

		for(int i = 0; i < childsList.getLength(); i++){
			childNotationsNode = childsList.item(i);
			childName = childNotationsNode.getNodeName();

			if(childName.equals(MXL_ORNAMENTS)){
				NodeList ornamentsChildList = childNotationsNode.getChildNodes();
				for(int j = 0; j < ornamentsChildList.getLength(); j++){
					Node ornamentsChild = ornamentsChildList.item(j);
					addOrnaments(note, ornamentsChild);
				}
			} else if(childName.equals(MXL_ARTICULATIONS)){
				NodeList articulationsChildList = childNotationsNode.getChildNodes();
				for(int j = 0; j < articulationsChildList.getLength(); j++){
					Node articulationsChild = articulationsChildList.item(j);
					addArticulations(note, articulationsChild);
				}
			} else if(childName.equals(MXL_TECHNICAL)){
				NodeList articulationsChildList = childNotationsNode.getChildNodes();
				for(int j = 0; j < articulationsChildList.getLength(); j++){
					Node articulationsChild = articulationsChildList.item(j);
					addTechnicals(note, articulationsChild);
				}
			} else if(childName.equals(MXL_SLUR)){
				Element e = (Element)childNotationsNode;
				int number = Integer.parseInt(e.getAttribute(MXL_NUMBER));
				SlurType type = BinarySymbolFactory.getSlurTypeFromString(e.getAttribute(MXL_TYPE));
				
				Slur slur = new Slur(type, number);
				note.addExtraSymbol(slur);
			}

			//add other notations

		}

	}

	private static Beam buildBeamFromNod(Node beamNode) {
		int number = Integer.parseInt(beamNode.getAttributes().getNamedItem(MXL_NUMBER).getTextContent());

		String rawType = beamNode.getTextContent();

		return new Beam(BinarySymbolFactory.getTypeFromString(rawType), number);
	}


	public static void addOrnaments(Note note, Node node){
		String nodeName = node.getNodeName();

		if(nodeName.equals(TrillMark.getTrigger())){
			note.addExtraSymbol(TrillMark.getTrillMark());
		} else if(nodeName.equals(Tremolo.getTrigger())){
			Element e = (Element)node;
			String type = e.getAttribute(MXL_TYPE);
			
			if(type.equals(""))
				type = "single";
			
			int markNumber = Integer.parseInt(node.getTextContent());

			Tremolo tremolo = Tremolo.getTremolo();
			tremolo.setNum(markNumber);
			tremolo.setType(type);
			note.addExtraSymbol(tremolo);
		} else if(nodeName.equals(Turn.getTrigger())){
			note.addExtraSymbol(Turn.getTurn());
		} else if(nodeName.equals(Mordent.getTrigger())){
			Element e = (Element)node;
			boolean isLong = Boolean.parseBoolean(e.getAttribute(MXL_LONG));

			Mordent m = Mordent.getMordent();
			m.setLong(isLong);
			note.addExtraSymbol(m);
		} else if(nodeName.equals(InvertedMordent.getTrigger())){
			Element e = (Element)node;
			boolean isLong = Boolean.parseBoolean(e.getAttribute(MXL_LONG));

			InvertedMordent im = InvertedMordent.getInvertedMordent();
			im.setLong(isLong);
			note.addExtraSymbol(im);
		} else if(nodeName.equals(FermataNotation.getTrigger())){
			note.addExtraSymbol(FermataNotation.getFermataNotation());
		}

		//add others ornaments
	}


	public static void addArticulations(Note note, Node node){
		String nodeName = node.getNodeName();

		if(nodeName.equals(Staccatissimo.getTrigger())){
			note.addExtraSymbol(Staccatissimo.getStaccatissimo());
		} else if(nodeName.equals(Staccato.getTrigger())){
			note.addExtraSymbol(Staccato.getStaccato());
		} else if(nodeName.equals(Spiccato.getTrigger())){
			note.addExtraSymbol(Spiccato.getSpiccato());
		} else if(nodeName.equals(Tenuto.getTrigger())){
			note.addExtraSymbol(Tenuto.getTenuto());
		}

		//add others articulations
	}


	public static void addTechnicals(Note note, Node node){
		String nodeName = node.getNodeName();
		
		if(nodeName.equals(Snap_pizzicato.getTrigger())){
			note.addExtraSymbol(Snap_pizzicato.getSnap_pizzicato());
		}
		
		//add others technical
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

	/*
	  	<metronome font-family="EngraverTextT" font-size="6.1" parentheses="yes">
            <beat-unit>quarter</beat-unit>
            <per-minute>85</per-minute>
        </metronome>
	 */
	private static Metronome getMetronome(Node metronomeNode) {
		boolean doted = false;
		Node beatUnit, perMinute;
		
		int perMinuteValue;
		Type beatUnitValue;
		
		beatUnit = getSingleChildByName(metronomeNode, MXL_METRONOME_BEAT_UNIT);
		if (beatUnit == null) {
			beatUnit = getSingleChildByName(metronomeNode, MXL_METRONOME_BEAT_UNIT_DOT);
			doted = true;
		}
		if (beatUnit == null)
			return null;
		
		perMinute = getSingleChildByName(metronomeNode, MXL_METRONOME_PER_MINUTE);
		
		perMinuteValue = Integer.parseInt(perMinute.getTextContent());
		beatUnitValue = getTypeFromMXLType(beatUnit.getTextContent());
		
		
		return new Metronome(beatUnitValue, perMinuteValue, doted);
	}
	
	//	public static void main(String[] args) {
	//		XMLParser parser = new XMLParser();
	//		File rng;
	//		try {
	////			String input = "/home/alexandre/git/pstl_musicxml/musicxml/test-data/simple/helloworld.mxl";
	////			String input = "/home/alexandre/git/pstl_musicxml/musicxml/test-data/chorales.all.musicxml/bwv0254.krn.xml";
	//			String testDir = "/home/alexandre/git/pstl_musicxml/musicxml/test-data/chorales.all.musicxml/";
	////			String testDir = "/home/alexandre/git/pstl_musicxml/musicxml/test-data/xmlsamples";
	//			String pattern = ".*\\.(xml|mxl)";
	////			String pattern = ".*\\.(xml)";
	//
	//
	//
	//			rng = new File("/home/alexandre/git/pstl_musicxml/musicxml/grammars/rng/musicXML.rng");
	//			parser.setRng(rng);
	//
	//			Collection<File> files = FileUtils.getFileList(testDir);
	//			ArrayList<Score> scores = new ArrayList<>();
	//			Document crtDoc;
	//			Score crtScore;
	//			int i = 0;
	//			int skip = 0;
	//
	//			for (File f : files) {
	//
	//				if (!f.getAbsolutePath().matches(pattern)) {
	//					skip++;
	//					continue;
	//				}
	//
	//
	//
	//				parser.setInput(f.getAbsolutePath());
	//				try {
	//					System.out.println("Parsing " + f.getAbsolutePath());
	//					crtDoc = parser.getDocument();
	//					crtScore = loadFromDom(crtDoc);
	//
	//					scores.add(crtScore);
	//
	//					i++;
	//					if (i%10 == 0)
	//						System.out.println((int)(((i*1.0)/(files.size() * 1.0))*100) + "% done");
	//
	//				} catch (ParseException e) {
	//					System.out.println("Can't parse " + f);
	//				}
	//
	//			}
	//
	//			System.out.println(scores.size() + "/" + files.size() + " score succesfully parsed.");
	//			System.out.println(skip + " files skipped.");
	//			ArrayList<ArrayList<RhythmicTree>> rts = new ArrayList<>();
	//
	//			for (Score score : scores) {
	//				System.out.println(score);
	//			}
	//
	//			for (Score s : scores) {
	//				rts.add(RhythmicThreeFactory.buildRtFromScore(s));
	//			}
	//
	//			for (ArrayList<RhythmicTree> arrayList : rts) {
	//				for (RhythmicTree rt : arrayList) {
	//					System.out.println(rt);
	//				}
	//			}
	//
	//
	//
	//
	//
	//		} catch (IOException e) {
	//			e.printStackTrace();
	//		}
	//
	//	}

	public static void main(String[] args) throws IOException, ParseException {
		XMLParser parser = new XMLParser();
		File rng;
//		String input = "/home/alexandre/git/pstl_musicxml/musicxml/test-data/customfiles/t_beam01.xml";
//		String input = "/home/alexandre/git/pstl_musicxml/musicxml/test-data/customfiles/t01-chord.xml";
		String input = "/home/alexandre/git/pstl_musicxml/musicxml/test-data/customfiles/bigfile.xml";
//		String input = "/home/alexandre/git/pstl_musicxml/musicxml/test-data/simple/test_slur_2_measures.xml";



		rng = new File("/home/alexandre/git/pstl_musicxml/musicxml/grammars/rng/musicXML.rng");

//		String pattern = ".*\\.(xml)";


		parser.setRng(rng);
		parser.setInput(input);
		Document crtDoc;
		Score crtScore;

		crtDoc = parser.getDocument();
		crtScore = ScoreUtils.loadFromDom(crtDoc);
		
		crtScore.convertBeams();

		ArrayList<RhythmicTree> rts = RhythmicTreeFactory.buildRtFromScore(crtScore);
		System.out.println(crtScore);
		for (RhythmicTree rt : rts) {
			System.out.println(rt);
//			rt.getAllExtraSymbols().forEach(System.out::print);
		}
		
	}
}



