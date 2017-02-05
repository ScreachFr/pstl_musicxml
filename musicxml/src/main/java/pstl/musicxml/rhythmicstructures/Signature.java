package pstl.musicxml.rhythmicstructures;

public class Signature {
	private int beats;
	private int beatType;
	
	public Signature(int beats, int beatType) {
		this.beats = beats;
		this.beatType = beatType;
	}
	
	public int getBeats() {
		return beats;
	}
	
	public int getBeatType() {
		return beatType;
	}
	
	@Override
	public String toString() {
		return beats + "/" + beatType;
	}
}
