package pstl.musicxml.musicalstructures.symbols.binary;

public class Slur implements BinarySymbol {
	private SlurType type;
	private int number;
	
	public Slur(SlurType type, int number) {
		this.type = type;
		this.number = number;
	}

	public SlurType getType() {
		return type;
	}

	public int getNumber() {
		return number;
	}

	@Override
	public String toString() {
		return "slur:" + type + ":" + number;
	}
	
	public enum SlurType {
		START, STOP;

		public String toString() {
			switch (this) {
			case START:
				return "start";
			case STOP:
				return "stop";
			}
			
			return "null";
		}
		
		public String getMXLEquivalent() {
			switch (this) {
			case START:
				return "start";
			case STOP:
				return "stop";

			}
			
			//Never reached.
			return "";
		}
	}
}
