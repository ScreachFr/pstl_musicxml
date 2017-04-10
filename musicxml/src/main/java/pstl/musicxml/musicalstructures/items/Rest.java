package pstl.musicxml.musicalstructures.items;

import pstl.musicxml.musicalstructures.Type;


public class Rest implements IMusicalItem {
	private Type type;
	private boolean wholeMeasure;
	
	
	public Rest(Type type) {
		this.type = type;
		wholeMeasure = false;
	}
	
	public Rest(boolean wholeMeasure) {
		this.wholeMeasure = wholeMeasure;
		type = Type.UNKNOWN;
	}

	@Override
	public Type getType() {
		return type;
	}
	
	public boolean isWholeMeasure() {
		return wholeMeasure;
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
