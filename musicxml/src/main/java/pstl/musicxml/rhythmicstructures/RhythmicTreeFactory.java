package pstl.musicxml.rhythmicstructures;

import java.util.ArrayList;

import pstl.musicxml.musicalstructures.Measure;
import pstl.musicxml.musicalstructures.Part;
import pstl.musicxml.musicalstructures.Score;
import pstl.musicxml.musicalstructures.items.Chord;
import pstl.musicxml.musicalstructures.items.Group;import pstl.musicxml.musicalstructures.items.Rest;
import pstl.musicxml.musicalstructures.items.Tie;


public class RhythmicTreeFactory {
	public static ArrayList<RhythmicTree> buildRtFromScore(Score score) {
		ArrayList<RhythmicTree> result = new ArrayList<>();
		
		for (Part p : score.getParts()) {
			result.addAll(buildRTFromPart(p));
		}
		
		return result;
	}
	
	
	public static ArrayList<RhythmicTree> buildRTFromPart(Part part) {
		ArrayList<RhythmicTree> result = new ArrayList<>();

		for (Measure m : part.getMeasures()) {
			result.add(buidRTFromMeasure(m));
		}
		
		return result;
	}
	
	//TODO add a possible default signature
	public static RhythmicTree buidRTFromMeasure(Measure measure) {
		RhythmicTree result = new RhythmicTree(ItemType.Measure, measure.getSignature());
		
		result.setSignature(measure.getSignature());
		
		measure.getItems().forEach(item -> {
			if (item instanceof Chord) {
				result.addChild(buildRTFromChord((Chord) item));
			} else if (item instanceof Group) {
				result.addChild(buildRTFromGroup((Group) item));
			} else if (item instanceof Rest) {
				result.addChild(new RhythmicTree(ItemType.Rest, new Fraction(1, item.getType())));
			} else if (item instanceof Tie) {
				result.addChild(new RhythmicTree(ItemType.Tie, new Fraction(1, item.getType())));
			}
		});
		
		result.calculateFractions();
		
		return result;
		
	}
	
	public static RhythmicTree buildRTFromGroup(Group group) {
		RhythmicTree rt = new RhythmicTree(ItemType.Measure, group.getFraction());
		
		group.getItems().forEach(item -> {
			if (item instanceof Chord) {
				rt.addChild(buildRTFromChord((Chord) item));
			} else if (item instanceof Group) {
				rt.addChild(buildRTFromGroup((Group) item));
			}
		});
		
		return rt;
	}
	
	public static RhythmicTree buildRTFromChord(Chord c) {
		RhythmicTree rt = new RhythmicTree(ItemType.Note, new Fraction(1, c.getType()));
		
		c.getNotes().forEach(note -> {
			if (note.getExtraSymbols() != null)
				rt.addExtraSymbol(note.getExtraSymbols());
		});
		
		return rt;
	}
}
