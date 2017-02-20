package pstl.musicxml;

import java.util.ArrayList;

import pstl.musicxml.rhythmicstructures.Signature;
import pstl.musicxml.rhythmicstructures.items.Chord;


public class Measure implements Comparable<Measure>{
	private int number;
	private ArrayList<Chord> chords;
	private Signature signature;


	public Measure(int number) {
		this.number = number;
		chords = new ArrayList<>();
	}

	public ArrayList<Chord> getChords() {
		return chords;
	}

	public void setSignature(Signature signature) {
		this.signature = signature;
	}

	public Signature getSignature() {
		return signature;
	}

	public void addChord(Chord c) {
		chords.add(c);
	}

	public void setChords(ArrayList<Chord> chords) {
		this.chords = chords;
	}

	@Override
	public int compareTo(Measure o) {
		return Integer.compare(number, o.number);
	}

	@Override
	public String toString() {
		String result = "";

		result += "#" + number + "(" ;
		
		if (signature == null)
			result += "NS" + " ";
		else
			result += signature + " ";

		if (chords.isEmpty()) {
			result += "Empty";
		} else {
			for (Chord c : chords) {
				result += c.toMeasureString() + " ";
			}
		}

		result += ")";
		return result;
	}
}









