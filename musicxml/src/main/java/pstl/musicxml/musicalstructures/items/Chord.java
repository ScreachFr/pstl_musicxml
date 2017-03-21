package pstl.musicxml.musicalstructures.items;

import java.util.ArrayList;

import pstl.musicxml.musicalstructures.Type;
import pstl.musicxml.musicalstructures.symbols.binary.Beam;

public class Chord implements IMusicalItem {
	private ArrayList<Note> notes;
	private Type type;
	public Chord() {
		notes = new ArrayList<>();
	}
	
	public Chord(Type type) {
		notes = new ArrayList<>();
		this.type = type;
	}
	
	@Override
	public Type getType() {
		return type;
	}
	
	public void addNote(Note n) {
		notes.add(n);
		
		if (type == null)
			type = n.getType();
	}
	

	public void setType(Type type) {
		this.type = type;
	}
	
	public ArrayList<Note> getNotes() {
		return notes;
	}
	
	public ArrayList<Beam> getBeams() {
		ArrayList<Beam> result = new ArrayList<>();
		
		for (Note note : notes) {
			result.addAll(note.getBeams());
		}
		
		return result;
	}
	
	public void removeBeam(int number) {
		notes.forEach(item -> item.removeBeam(number));
	}
	
	
	@Override
	public String toString() {
		String result = "";
		
		if (notes.size() == 1)
			return notes.get(0) + "";
		else if (notes.size() == 0)
			return "";
		
		int i = 0;
		result += type.getNumber() + "(";
		
		for (IMusicalItem item : notes) {
			result += item.toMeasureString() + "";
			if (i < notes.size()-1)
				result += " ";
			i++;
		}
		result += ")";
		return result;
	}
	
	@Override
	public String toMeasureString() {
		return toString();
	}
	
	public static void main(String[] args) {
		Chord c = new Chord();
		
		c.addNote(new Note("A", 1, Type.QUARTER));
		c.addNote(new Note("A", 1, Type.QUARTER));
		c.addNote(new Note("A", 1, Type.QUARTER));
		c.addNote(new Note("A", 1, Type.QUARTER));
		
		System.out.println(c);
	}

	
}
