package pstl.musicxml.musicalstructures.symbols.unary;

public class Alter implements UnarySymbol {
	private int value;
	
	public Alter(int value) {
		this.value = value;
	}
	
	public static String getTrigger() {
		return "alter";
	}
	
	@Override
	public String toString() {
		return "Alter:" + value;
	}
}
