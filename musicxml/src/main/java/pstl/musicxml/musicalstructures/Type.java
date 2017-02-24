package pstl.musicxml.musicalstructures;


public enum Type {
	WHOLE(1, "whole"), HALF(2, "half"), QUARTER(4, "quarter"), EIGHTH(8, "eighth"),
	SIXTEENTH(16, "16th"), THIRTY_SECOND(32, "32nd"), SIXTY_FOURTH(64, "64th"), HUNDRED_TWENTY_EIGHT(128, "128th"),
	UNKNOWN(-1, ""), UNDEFINED(-1, "UNDIFIENED"); 
	//TWO_HUNDRED_AND_FIFTY_SIXTH(, "")
	//BREVE(0.5 , "breve") gotta be considered as two wholes.
	
	private int number;
	private String mxlEquivalent;
	
	private Type(int number, String mxlEquivalent) {
		this.number = number;
		this.mxlEquivalent = mxlEquivalent;
	}
	
	public int getNumber() {
		return number;
	}
	
	public String getMxlEquivalent() {
		return mxlEquivalent;
	}
	
	public boolean equals(Type o) {
		return number == o.number;
	}
	
	public boolean equals(String mxlType) {
		return mxlEquivalent.equals(mxlType);
	}
	
	@Override
	public String toString() {
		return mxlEquivalent + ":" + number;
	}
	
}
