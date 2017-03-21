package pstl.musicxml.musicalstructures.symbols.unary;

public class Grace implements UnarySymbol {
	private boolean isSlashed;
	private static Grace grace = new Grace();
	
	@Override
	public String toString() {
		return "grace";
	}

	public static String getTrigger() {
		return "grace";
	}
	
	public static Grace getGrace() {
		return grace;
	}
	
	public void setSlash(boolean slash){
		isSlashed = slash;
	}
}
