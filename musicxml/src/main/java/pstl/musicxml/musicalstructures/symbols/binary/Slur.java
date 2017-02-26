package pstl.musicxml.musicalstructures.symbols.binary;

public class Slur implements BinarySymbol {
	private Type type;
	private int number;
	
	public Slur(Type type, int number) {
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
		START, CONTINUE, STOP;

		public String toString() {
			switch (this) {
			case START:
				return "start";
			case CONTINUE:
				return "continue";
			case STOP:
				return "stop";
			}
			
			return "null";
		}
	}

	@Override
	public String toString() {
		return "slur:" + type + ":" + number;
	}
	
	
}
