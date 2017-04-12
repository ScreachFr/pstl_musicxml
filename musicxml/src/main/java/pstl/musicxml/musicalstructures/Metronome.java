package pstl.musicxml.musicalstructures;

public class Metronome {
	private Type beatUnit;
	private int perMinute;
	private boolean isDoted;
	
	
	
	public Metronome(Type beatUnit, int perMinute, boolean isDoted) {
		this.beatUnit = beatUnit;
		this.perMinute = perMinute;
		this.isDoted = isDoted;
	}

	public Metronome(Type beatUnit, int perMinute) {
		this(beatUnit, perMinute, false);
	}
	
	public Type getBeatUnit() {
		return beatUnit;
	}
	
	public int getPerMinute() {
		return perMinute;
	}
	
	public boolean isDoted() {
		return isDoted;
	}
	
	@Override
	public String toString() {
		return "Metronome[" + perMinute + " " + beatUnit + "/60s]";
	}
	
	/*
	  <direction-type>
          <metronome font-family="EngraverTextT" font-size="6.1" parentheses="yes">
            <beat-unit>quarter</beat-unit>
            <per-minute>85</per-minute>
          </metronome>
        </direction-type>
	 */
	
	
}
