package pstl.musicxml.rhythmicstructures.items;

public class Tie extends Note implements IMusicalItem{

	
	
	public Tie(String step, int pitch, int duration) {
		super(step, pitch, duration);
	}


	@Override
	public String toMeasureString() {
		return (duration * 1.0) + "";
	}

}
