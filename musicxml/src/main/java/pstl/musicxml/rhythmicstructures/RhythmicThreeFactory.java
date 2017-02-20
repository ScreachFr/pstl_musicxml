package pstl.musicxml.rhythmicstructures;

import java.util.ArrayList;

import pstl.musicxml.Measure;
import pstl.musicxml.Part;
import pstl.musicxml.Score;
import pstl.musicxml.rhythmicstructures.items.Chord;

public class RhythmicThreeFactory {
	public static ArrayList<RhythmicTree> buildRtFromScore(Score score) {
		ArrayList<RhythmicTree> result = new ArrayList<>();
		
		for (Part p : score.getParts()) {
			result.add(buildRTFromPart(p));
		}
		
		return result;
	}
	
	
	public static RhythmicTree buildRTFromPart(Part part) {
		RhythmicTree result = new RhythmicTree();
		
		for (Measure m : part.getMeasures()) {
			result.addChild(buidRTFromMeasure(m));
		}

		return result;
	}
	
	public static RhythmicTree buidRTFromMeasure(Measure measure) {
		ArrayList<Chord> chords = measure.getChords();
		
		RhythmicTree result = new RhythmicTree(measure.getSignature());
		
		//TODO this method is kinda dumb. Need to include beams and stuff like that to return a true well formed RT.
		
		for (Chord chord : chords) {
			result.addChild(new RhythmicTree(chord.getType()));
		}
		
		
		return result;
	}
}
