package pstl.musicxml.rhythmicstructures.items;

import pstl.musicxml.Type;


public class Rest implements IMusicalItem {
	private Type type;
	
	public Rest(Type type) {
		this.type = type;
	}

	@Override
	public Type getType() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String toMeasureString() {
		return "R" + type.getNumber();
	}
	
	@Override
	public String toString() {
		return toMeasureString();
	}
	
}
