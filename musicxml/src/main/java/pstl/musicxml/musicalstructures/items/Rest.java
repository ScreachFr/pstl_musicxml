package pstl.musicxml.musicalstructures.items;

import pstl.musicxml.musicalstructures.Type;


public class Rest implements IMusicalItem {
	private Type type;
	
	public Rest(Type type) {
		this.type = type;
	}

	@Override
	public Type getType() {
		return type;
	}
	
	@Override
	public String toMeasureString() {
		return "-" + type.getNumber();
	}
	
	@Override
	public String toString() {
		return toMeasureString();
	}
	
}
