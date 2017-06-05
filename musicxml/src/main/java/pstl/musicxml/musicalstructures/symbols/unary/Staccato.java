package pstl.musicxml.musicalstructures.symbols.unary;

public class Staccato implements UnarySymbol {
	private static Staccato staccato = new Staccato();
	
	@Override
	public String toString() {
		return "staccato";
	}

	public static String getTrigger() {
		return "staccato";
	}
	
	public static Staccato getStaccato() {
		return staccato;
	}
}
