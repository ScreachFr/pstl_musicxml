package pstl.musicxml.rhythmicstructures;

import java.util.ArrayList;
public class Chord {
	private ArrayList<Note> notes;
	private int duration;
	
	public Chord() {
	}
	
	public Chord(int duration) {
		notes = new ArrayList<>();
		this.duration = duration;
	}
	
	public void addNote(Note n) {
		notes.add(n);
	}
	
	public int getDuration() {
		return duration;
	}
	
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	@Override
	public String toString() {
		String result = "";
		
		if (notes.size() == 1)
			return notes.get(0) + "";
		else if (notes.size() == 0)
			return "";
		
		int i = 0;
		result += duration + "(";
		
		for (Note note : notes) {
			result += note + "";
			if (i < notes.size()-1)
				result += " ";
			i++;
		}
		result += ")";
		return result;
	}

	
	public static void main(String[] args) {
		Chord c = new Chord(1);
		
		c.addNote(new Note(1, 1));
		c.addNote(new Note(1, 1));
		c.addNote(new Note(1, 1));
		c.addNote(new Note(1, 1));
		
		System.out.println(c);
	}
}
