package pstl.musicxml.symboles.unary;

public class Dot extends UnarySymbol {
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
