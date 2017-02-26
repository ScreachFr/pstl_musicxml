package pstl.musicxml.musicalstructures.symbols.unary;

public class FermataNotation implements UnarySymbol {
	private static FermataNotation fermataNotation = new FermataNotation();
	
	@Override
	public String toString() {
		return "fermataNotation";
	}

	public static String getTrigger() {
		return "fermataNotation";
	}
	
	public static FermataNotation getFermataNotation() {
		return fermataNotation;
	}
}
