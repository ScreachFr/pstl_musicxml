package pstl.musicxml.musicalstructures.symbols.unary;

public class TrillMark  implements UnarySymbol {
	private static TrillMark trillMark = new TrillMark();
	
	@Override
	public String toString() {
		return "trill-mark";
	}

	public static String getTrigger() {
		return "trill-mark";
	}
	
	public static TrillMark getTrillMark() {
		return trillMark;
	}
}
