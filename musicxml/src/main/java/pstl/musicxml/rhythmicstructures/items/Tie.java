package pstl.musicxml.rhythmicstructures.items;

import pstl.musicxml.Type;

public class Tie extends Note implements IMusicalItem{

	
	
	public Tie(String step, int pitch, Type type) {
		super(step, pitch, type);
	}


	@Override
	public String toMeasureString() {
		return (type.getNumber() * 1.0) + "";
	}

}
