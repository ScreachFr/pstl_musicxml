package pstl.musicxml.musicalstructures.symbols.binary;

import pstl.musicxml.musicalstructures.symbols.binary.Slur.SlurType;

public class BinarySymbolFactory {
	
	public static BinarySymbolType getTypeFromString(String s) {
		
		for (BinarySymbolType t : BinarySymbolType.values()) {
			if (t.getMXLEquivalent().equals(s))
				return t;
		}

		//Shouldn't be reached
		return null;
	}
	
	public static SlurType getSlurTypeFromString(String s) {
		
		for (SlurType t : SlurType.values()) {
			if (t.getMXLEquivalent().equals(s))
				return t;
		}

		//Shouldn't be reached
		return null;
	}
}
