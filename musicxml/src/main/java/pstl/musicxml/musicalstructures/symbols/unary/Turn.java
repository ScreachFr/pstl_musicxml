package pstl.musicxml.musicalstructures.symbols.unary;

public class Turn implements UnarySymbol {
	private static Turn turn = new Turn();
	
	@Override
	public String toString() {
		return "turn";
	}

	public static String getTrigger() {
		return "turn";
	}
	
	public static Turn getTurn() {
		return turn;
	}
}
