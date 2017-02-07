package pstl.musicxml.rhythmicstructures.items;


public class Rest implements IMusicalItem {
	private int duration;
	
	public Rest(int duration) {
		this.duration = duration;
	}

	@Override
	public int getDuration() {
		return duration;
	}

	@Override
	public String toRythmicTreeString() {
		return "-" + duration;
	}
	
}
