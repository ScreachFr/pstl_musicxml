package pstl.musicxml.musicalstructures.items;

import pstl.musicxml.musicalstructures.Type;

public interface IMusicalItem {
	public Type getType();
	public String toMeasureString();
}
