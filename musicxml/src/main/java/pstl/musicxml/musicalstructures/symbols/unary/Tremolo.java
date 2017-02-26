package pstl.musicxml.musicalstructures.symbols.unary;

public class Tremolo implements UnarySymbol{
	private int markNumber;
	private String type;
	
	private static Tremolo tremolo = new Tremolo();
	
	@Override
	public String toString() {
		return "tremolo";
	}

	public static String getTrigger() {
		return "tremolo";
	}
	
	public static Tremolo getTremolo() {
		return tremolo;
	}
	
	public void setNum(int markNumber){
		this.markNumber = markNumber;
	}
	
	public void setType(String type){
		this.type = type;
	}
}
