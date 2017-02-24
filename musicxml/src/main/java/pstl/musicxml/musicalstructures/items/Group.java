package pstl.musicxml.musicalstructures.items;

import java.util.ArrayList;

import pstl.musicxml.musicalstructures.Type;

public class Group implements IMusicalItem {
	private Type type;//XXX Is it really necessary ?
	private ArrayList<IMusicalItem> items;
	private int number;
	
	public Group(int number, Type type) {
		this.number = number;
		this.type = type;
		items = new ArrayList<>();
	}
	
	
	public ArrayList<IMusicalItem> getItems() {
		return items;
	}
	
	public void AddItem(IMusicalItem item) {
		items.add(item);
	}
	
	@Override
	public Type getType() {
		return type;
	}

	@Override
	public String toMeasureString() {
		String result = "(Group#" + number + " ";
		
		for (IMusicalItem item : items) {
			result += item;
		}
		
		result += ")";
		
		return result;
	}
	
}
