package pstl.musicxml.rhythmicstructures.items;


public class Note implements IMusicalItem {
	protected int pitch;
	protected int duration;
	protected int voice;

	public Note(int pitch, int duration) {
		this.pitch = pitch;
		this.duration = duration;
		voice = 1;
	}

	public Note(int pitch, int duration, int voice) {
		this(pitch, duration);
		this.voice = voice;
	}

	public int getDuration() {
		return duration;
	}

	public int getPitch() {
		return pitch;
	}

	public int getVoice() {
		return voice;
	}

	public void setVoice(int voice) {
		this.voice = voice;
	}

	@Override
	public String toRythmicTreeString() {
		return toString();
	}
	
	@Override
	public String toString() {
		return pitch + "";


	}
}
