package pstl.musicxml.musicalstructures.items;

import pstl.musicxml.musicalstructures.Type;

public class Tie extends Note implements IMusicalItem{

	
	
	public Tie(String step, int pitch, Type type) {
		super(step, pitch, type);
	}


	@Override
	public String toMeasureString() {
		return (type.getNumber() * 1.0) + "";
	}

}
