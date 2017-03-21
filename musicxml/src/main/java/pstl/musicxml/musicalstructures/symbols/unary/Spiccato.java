package pstl.musicxml.musicalstructures.symbols.unary;

public class Spiccato implements UnarySymbol{
	private static Spiccato spiccato = new Spiccato();
	
	@Override
	public String toString() {
		return "spiccato";
	}

	public static String getTrigger() {
		return "spiccato";
	}
	
	public static Spiccato getSpiccato() {
		return spiccato;
	}
}
