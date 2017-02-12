package pstl.musicxml.rhythmicstructures;

import java.util.ArrayList;

import pstl.musicxml.rhythmicstructures.items.Chord;
import pstl.musicxml.rhythmicstructures.items.Note;
import pstl.musicxml.rhythmicstructures.items.Rest;
import pstl.musicxml.rhythmicstructures.items.Tie;

public class RythmicTree {
	//XXX include voices as ArrayList<ArrayList<Chord>> voices ?
	private Signature signture;
	private ArrayList<Chord> chords;
	//durée
	//proportion liste de RT
	public RythmicTree(Signature signature) {
		this();
		this.signture = signature;
	}

	public RythmicTree() {
		this.chords = new ArrayList<>();
	}

	public Signature getSignture() {
		return signture;
	}

	public ArrayList<Chord> getChords() {
		return chords;
	}

	public void addChord(Chord c) {
		chords.add(c);
	}
	
	public void setSignture(Signature signture) {
		this.signture = signture;
	}
	
	@Override
	public String toString() {
		String result = "";
		int i = 0, crtDuration = 0;
		result += "(" + signture;

		if (!chords.isEmpty())
			result += " ";
		
		for (Chord c : chords) {
			if (crtDuration == 0)
				result += "(";
			
			result += c;
			
			crtDuration += c.getDuration();
			
			if (crtDuration >= signture.getBeats()) {
				result += ")";
				
				crtDuration = 0;
			}
			if (i < chords.size()-1) {
				result += " ";
			}
			i++;
		}
		
//		if (crtDuration == 0)
//			result += ")";


		result += ")";
		return result;
	}

	public static void main(String[] args) {
		RythmicTree rt = new RythmicTree(new Signature(4, 4));

		Chord c1 = new Chord(4);
		c1.addItem(new Note("A", 4, 4));
		
		Chord c2 = new Chord(4);
		c2.addItem(new Note("A", 4, 4));
		
		Chord c3 = new Chord(1);
		c3.addItem(new Note("A", 1, 1));
		c3.addItem(new Note("A", 1, 1));
		c3.addItem(new Tie("A", 1, 1));
		c3.addItem(new Rest(3));
		
		
		rt.addChord(c1);
		rt.addChord(c2);
		rt.addChord(c3);
		System.out.println(rt);
	}

}
