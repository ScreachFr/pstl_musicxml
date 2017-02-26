package pstl.musicxml.musicalstructures.symbols.unary;

public class Staccatissimo implements UnarySymbol{
	private static Staccatissimo staccatissimo = new Staccatissimo();
	
	@Override
	public String toString() {
		return "turn";
	}

	public static String getTrigger() {
		return "turn";
	}
	
	public static Staccatissimo getStaccatissimo() {
		return staccatissimo;
	}
}
