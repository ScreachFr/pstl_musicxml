package pstl.musicxml.rhythmicstructures.items;

import pstl.musicxml.Type;

public interface IMusicalItem {
	public Type getType();
	public String toMeasureString();
}
