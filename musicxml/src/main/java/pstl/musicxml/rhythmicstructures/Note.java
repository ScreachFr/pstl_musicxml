package pstl.musicxml.rhythmicstructures;

public class Note {
	private int pitch;
	private int duration;
	
	
	public Note(int pitch, int duration) {
		this.pitch = pitch;
		this.duration = duration;
	}
	
	public int getDuration() {
		return duration;
	}
	
	@Override
	public String toString() {
		return pitch + "";
	}
}
