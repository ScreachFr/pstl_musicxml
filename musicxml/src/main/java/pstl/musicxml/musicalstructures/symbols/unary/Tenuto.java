package pstl.musicxml.musicalstructures.symbols.unary;

public class Tenuto implements UnarySymbol{
	private static Tenuto tenuto = new Tenuto();
	
	@Override
	public String toString() {
		return "tenuto";
	}

	public static String getTrigger() {
		return "tenuto";
	}
	
	public static Tenuto getTenuto() {
		return tenuto;
	}
}
