package pstl.musicxml.musicalstructures.symbols.binary;

public class Beam implements BinarySymbol {
	private BinarySymbolType type;
	private int number;
	
	public Beam(BinarySymbolType type, int number) {
		this.type = type;
		this.number = number;
	}
	
	public BinarySymbolType getType() {
		return type;
	}
	
	public int getNumber() {
		return number;
	}
	
	
	@Override
	public String toString() {
		return "beam:" + number + ":" + type;
	}
	
	public static String getTrigger() {
		return "beam";
	}
}
