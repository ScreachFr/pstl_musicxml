package pstl.musicxml;

import pstl.musicxml.rhythmicstructures.RythmicTree;

public class Measure implements Comparable<Measure>{
	private int number;
	private RythmicTree rt;
	
	public Measure(int number) {
		this.number = number;
	}
	
	
	public void setRt(RythmicTree rt) {
		this.rt = rt;
	}
	
	@Override
	public int compareTo(Measure o) {
		return Integer.compare(number, o.number);
	}
}
