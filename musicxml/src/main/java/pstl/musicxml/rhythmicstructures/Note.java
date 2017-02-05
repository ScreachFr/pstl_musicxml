package pstl.musicxml.rhythmicstructures;

public class Note {
	private int pitch;
	
	public Note(int pitch) {
		this.pitch = pitch;
	}
	
	@Override
	public String toString() {
		return pitch + "";
	}
}
