package pstl.musicxml.rhythmicstructures;

import pstl.musicxml.musicalstructures.Type;

public class Fraction {
	private Type baseType;
	private int value;
	
	public Fraction(int value, Type baseType) {
		this.value = value;
		this.baseType = baseType;
	}
	
	
	public Type getBaseType() {
		return baseType;
	}
	
	
	public String toRTString() {
		return "TODO";
	}
}
