package pstl.musicxml.musicalstructures.items;

import java.util.ArrayList;

import pstl.musicxml.musicalstructures.Type;

public class Chord implements IMusicalItem {
	private ArrayList<IMusicalItem> items;
	private Type type;
	public Chord() {
		items = new ArrayList<>();
	}
	
	public Chord(Type type) {
		items = new ArrayList<>();
		this.type = type;
	}
	
	@Override
	public Type getType() {
		return type;
	}
	
	public void addItem(IMusicalItem n) {
		items.add(n);
		
		if (type == null)
			type = n.getType();
	}
	

	public void setType(Type type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		String result = "";
		
		if (items.size() == 1)
			return items.get(0) + "";
		else if (items.size() == 0)
			return "";
		
		int i = 0;
		result += type.getNumber() + "(";
		
		for (IMusicalItem item : items) {
			result += item.toMeasureString() + "";
			if (i < items.size()-1)
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
		
		c.addItem(new Note("A", 1, Type.QUARTER));
		c.addItem(new Note("A", 1, Type.QUARTER));
		c.addItem(new Note("A", 1, Type.QUARTER));
		c.addItem(new Note("A", 1, Type.QUARTER));
		
		System.out.println(c);
	}

	
}
