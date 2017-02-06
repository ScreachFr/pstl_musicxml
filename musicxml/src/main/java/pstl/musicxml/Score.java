package pstl.musicxml;

import java.util.ArrayList;

public class Score {
	
	private ArrayList<Part> parts;
	
	public Score() {
		this.parts = new ArrayList<>();
	}
	
	public ArrayList<Part> getParts() {
		return parts;
	}
	
	public Part getPartById(String id) {
		for (Part p : parts) {
			if (p.getId().equals(id))
				return p;
		}
		
		return null;
	}
	
}
