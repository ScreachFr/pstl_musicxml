package pstl.musicxml.musicalstructures.symbols.binary;

public class Beam implements BinarySymbol {
	private Type type;
	private int number;
	
	public Beam(Type type, int number) {
		this.type = type;
		this.number = number;
	}
	
	public Type getType() {
		return type;
	}
	
	public int getNumber() {
		return number;
	}
	
	public enum Type {
		BEGIN, CONTINUE, END;

		public String toString() {
			switch (this) {
			case BEGIN:
				return "begin";
			case CONTINUE:
				return "cont";
			case END:
				return "end";
			}
			
			return "null";
		}
	}
	
	@Override
	public String toString() {
		return "beam:" + number + ":" + type;
	}
}
