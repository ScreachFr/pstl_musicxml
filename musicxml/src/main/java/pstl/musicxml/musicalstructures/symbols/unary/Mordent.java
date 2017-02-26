package pstl.musicxml.musicalstructures.symbols.unary;

public class Mordent implements UnarySymbol {
	private static Mordent mordent = new Mordent();
	
	@Override
	public String toString() {
		return "turn";
	}

	public static String getTrigger() {
		return "turn";
	}
	
	public static Mordent getMordent() {
		return mordent;
	}
}
