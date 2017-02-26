package pstl.musicxml.musicalstructures.symbols.unary;

public class InvertedMordent implements UnarySymbol {
	private boolean isLong;
	private static InvertedMordent invertedMordent = new InvertedMordent();
	
	@Override
	public String toString() {
		return "inverted-mordent";
	}

	public static String getTrigger() {
		return "inverted-mordent";
	}
	
	public static InvertedMordent getInvertedMordent() {
		return invertedMordent;
	}

	public void setLong(boolean b) {
		this.isLong = b;
	}
}
