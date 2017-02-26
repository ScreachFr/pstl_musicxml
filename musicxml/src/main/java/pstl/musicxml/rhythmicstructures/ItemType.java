package pstl.musicxml.rhythmicstructures;

public enum ItemType {
	
	Note, Rest, Tie, Measure;
	
	public String toRTString(int value) {
		return toRTString(value + "");
	}
	
	public String toRTString(String value) {
		switch (this) {
		case Note :
			return value +"";
		case Rest :
			return "-" + value;
		case Tie :
			return value + ".0";
		case Measure :
			return value +"";
	}
	
	return "Something went wrong with ItemType!";
	}
	
}
