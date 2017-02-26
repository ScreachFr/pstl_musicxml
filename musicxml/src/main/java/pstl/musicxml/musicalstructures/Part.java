package pstl.musicxml.musicalstructures;

import java.util.TreeSet;

public class Part {
	private String id;
	private String name;
	private TreeSet<Measure> measures;
	
	
	public Part(String id, String name) {
		this.id = id;
		this.name = name;
		measures = new TreeSet<>();
		
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public TreeSet<Measure> getMeasures() {
		return measures;
	}
	
	public String getId() {
		return id;
	}
	
	public void convertBeams() {
		measures.forEach(item -> item.convertBeams());
	}
	
	
	@Override
	public String toString() {
		String result = "";
		
		result += "Part #" + id + ":" + name + " {\n";
		
		for (Measure m : measures) {
			result += "\t" + m + "\n";
		}
		
		
		return result + "}";
	}
}
