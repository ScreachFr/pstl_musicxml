package pstl.musicxml.musicalstructures;

import java.util.ArrayList;

public class Score {
	
	private ArrayList<Part> parts;
	private Metronome tempo;
	
	public Score() {
		this.parts = new ArrayList<>();
	}
	
	public ArrayList<Part> getParts() {
		return parts;
	}
	
	public void convertBeams() {
		parts.forEach(item -> item.convertBeams());
	}
	
	public Part getPartById(String id) {
		for (Part p : parts) {
			if (p.getId().equals(id))
				return p;
		}
		
		return null;
	}
	//TODO add tempo
	@Override
	public String toString() {
		String result = "Score :";
		for (Part p : parts) {
			result += p + "\n";
		}
		return result;
	}
	
}
