package pstl.musicxml.rhythmicstructures.items;

import java.util.ArrayList;

public class Chord implements IMusicalItem {
	private ArrayList<IMusicalItem> items;
	private int duration;
	
	public Chord() {
		items = new ArrayList<>();
	}
	
	public Chord(int duration) {
		items = new ArrayList<>();
		this.duration = duration;
	}
	
	public void addItem(IMusicalItem n) {
		items.add(n);
		
		if (duration < n.getDuration())
			duration = n.getDuration();
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
		
		if (items.size() == 1)
			return items.get(0) + "";
		else if (items.size() == 0)
			return "";
		
		int i = 0;
		result += duration + "(";
		
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
		Chord c = new Chord(1);
		
		c.addItem(new Note("A", 1, 1));
		c.addItem(new Note("A", 1, 1));
		c.addItem(new Note("A", 1, 1));
		c.addItem(new Note("A", 1, 1));
		
		System.out.println(c);
	}

	
}
