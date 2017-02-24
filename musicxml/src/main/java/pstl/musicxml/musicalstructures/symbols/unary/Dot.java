package pstl.musicxml.musicalstructures.symbols.unary;

public class Dot implements UnarySymbol {
	private static Dot dot = new Dot();
	
	@Override
	public String toString() {
		return "dot";
	}

	public static String getTrigger() {
		return "dot";
	}
	
	public static Dot getDot() {
		return dot;
	}
}
