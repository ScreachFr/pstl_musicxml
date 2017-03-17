package pstl.musicxml.musicalstructures.symbols.unary;

public class Mordent implements UnarySymbol {
	private boolean isLong;
	private static Mordent mordent = new Mordent();
	
	@Override
	public String toString() {
		return "mordent";
	}

	public static String getTrigger() {
		return "mordent";
	}
	
	public static Mordent getMordent() {
		return mordent;
	}

	public void setLong(boolean b) {
		this.isLong = b;
	}
	
	
}
