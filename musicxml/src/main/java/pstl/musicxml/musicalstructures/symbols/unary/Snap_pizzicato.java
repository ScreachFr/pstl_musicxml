package pstl.musicxml.musicalstructures.symbols.unary;

public class Snap_pizzicato implements UnarySymbol {
	private static Snap_pizzicato snap_pizzicato = new Snap_pizzicato();
	
	@Override
	public String toString() {
		return "snap_pizzicato";
	}

	public static String getTrigger() {
		return "snap_pizzicato";
	}
	
	public static Snap_pizzicato getSnap_pizzicato() {
		return snap_pizzicato;
	}

}
