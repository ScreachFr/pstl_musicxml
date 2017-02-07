package pstl.musicxml.rhythmicstructures.items;

public class Tie extends Note implements IMusicalItem{

	
	
	public Tie(int pitch, int duration) {
		super(pitch, duration);
	}


	@Override
	public String toRythmicTreeString() {
		return (duration * 1.0) + "";
	}

}
