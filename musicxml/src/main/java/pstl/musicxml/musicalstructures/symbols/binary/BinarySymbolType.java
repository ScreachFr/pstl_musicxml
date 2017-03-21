package pstl.musicxml.musicalstructures.symbols.binary;

public enum BinarySymbolType {
	BEGIN, CONTINUE, END;

	public String toString() {
		switch (this) {
		case BEGIN:
			return "begin";
		case CONTINUE:
			return "cont";
		case END:
			return "end";
		}
		
		return "null";
	}
	
	public String getMXLEquivalent() {
		switch (this) {
		case BEGIN:
			return "begin";
		case CONTINUE:
			return "continue";
		case END:
			return "end";

		}
		
		//Never reached.
		return "";
	}
}
