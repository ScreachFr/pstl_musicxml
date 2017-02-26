package pstl.musicxml.musicalstructures.symbols.binary;

public class Beam implements BinarySymbol {
	private BeamType type;
	private int number;
	
	public Beam(BeamType type, int number) {
		this.type = type;
		this.number = number;
	}
	
	public BeamType getType() {
		return type;
	}
	
	public int getNumber() {
		return number;
	}
	
	public enum BeamType {
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
		
		public String getMXLEquivalent() {
			switch (this) {
			case BEGIN:
				return "begin";
			case CONTINUE:
				return "continue";
			case END:
				return "end";

			}
			
			//Never reached.
			return "";
		}
	}
	
	@Override
	public String toString() {
		return "beam:" + number + ":" + type;
	}
	
	public static String getTrigger() {
		return "beam";
	}
}
