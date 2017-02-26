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
	
	
	public int getRelativeValue(Type type) {
		if (baseType.compareNumber(type) > 0)
			throw new FractionException();
		
		return value * (type.getNumber() / baseType.getNumber());
	}
	
	public void setRelativeValue(Type type) {
		value = getRelativeValue(type);
		baseType = type;
	}
	
	public int getValue() {
		return value;
	}
	
	public String toRTString(Type type) throws FractionException {
		
		return getRelativeValue(type) + "";
	}
	
	public String toRTString() {
		return value + "";
	}
	
	public Fraction add(Fraction f) {
		if (f.getBaseType().equals(baseType)) {
			return new Fraction(this.value + f.getValue(), baseType);
		}

		int value;
		Type type;
		
		
		//if this = 1/8 and f = 1/16 then max = f and min = this.
		Fraction min, max;
		
		if (this.baseType.getNumber() < f.getBaseType().getNumber()) {
			max = this;
			min  = f;
		} else {
			max = f;
			min = this;
		}
		
		type = min.baseType;
		value = max.getRelativeValue(type);
		
		return new Fraction(value + min.getValue(), type);
	}
	
	@Override
	public String toString() {
		return value + "/" + baseType.getNumber();
	}
	
}
