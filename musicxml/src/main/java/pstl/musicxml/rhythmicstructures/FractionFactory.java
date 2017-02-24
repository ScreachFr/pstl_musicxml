package pstl.musicxml.rhythmicstructures;

import pstl.musicxml.musicalstructures.Type;

public class FractionFactory {
	
	public static Fraction newFraction(int value, Type type) {
		return new Fraction(value, type);
	}
	
	public static Fraction newQuarter(int value) {
		return newFraction(value, Type.QUARTER);
	}
	
	public static Fraction newQuarter() {
		return newQuarter(1);
	}
	
	public static Fraction newHalf(int value) {
		return newFraction(value, Type.HALF);
	}
	
	public static Fraction newHalf() {
		return newHalf(1);
	}
}
