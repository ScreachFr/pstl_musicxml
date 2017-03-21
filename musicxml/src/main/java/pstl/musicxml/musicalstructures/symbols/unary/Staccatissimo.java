package pstl.musicxml.musicalstructures.symbols.unary;

public class Staccatissimo implements UnarySymbol{
	private static Staccatissimo staccatissimo = new Staccatissimo();
	
	@Override
	public String toString() {
		return "staccatissimo";
	}

	public static String getTrigger() {
		return "staccatissimo";
	}
	
	public static Staccatissimo getStaccatissimo() {
		return staccatissimo;
	}
}
