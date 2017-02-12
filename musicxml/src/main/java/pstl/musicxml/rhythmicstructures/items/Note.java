package pstl.musicxml.rhythmicstructures.items;


public class Note implements IMusicalItem {
	protected String step;
	protected int octave;
	protected int duration;
	protected int voice;

	public Note(String step, int octave, int duration) {
		this.step = step;
		this.octave = octave;
		this.duration = duration;
		voice = 1;
	}

	public Note(String pitch, int octave, int duration, int voice) {
		this(pitch, octave, duration);
		this.voice = voice;
	}

	public int getDuration() {
		return duration;
	}

	public String getPitch() {
		return step;
	}

	public int getVoice() {
		return voice;
	}

	public void setVoice(int voice) {
		this.voice = voice;
	}

	@Override
	public String toMeasureString() {
		return toString();
	}
	
	@Override
	public String toString() {
		return step + "" +  octave + ":" + duration;


	}
}
