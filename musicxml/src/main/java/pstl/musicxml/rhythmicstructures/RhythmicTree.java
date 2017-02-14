package pstl.musicxml.rhythmicstructures;

import java.util.ArrayList;

public class RhythmicTree {
	private Signature signture;
	private int duration;
	
	private ArrayList<RhythmicTree> children;
	public RhythmicTree(Signature signature) {
		this();
		this.signture = signature;
		duration = -1;
	}
	
	public RhythmicTree(int duration) {
		this();
		this.duration = duration;
	}
	
	public RhythmicTree() {
		this.children = new ArrayList<>();
	}

	public ArrayList<RhythmicTree> getChildren() {
		return children;
	}
	
	public void addChild(RhythmicTree rt) {
		children.add(rt);
	}
	
	public Signature getSignture() {
		return signture;
	}

	public int getDuration() {
		return duration;
	}
	
	public void setSignture(Signature signture) {
		this.signture = signture;
	}
	
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	@Override
	public String toString() {
		if (children.size() == 0) {
			if (duration > 0)
				return duration + "";
			else if (signture != null)
				return signture + "";
			else 
				return "";
		}
		
		String result = "(";
		
		if (duration > 0)
			result += duration + " ";
		else if (signture != null)
			result += signture + " ";
			
		result += "(";
		
		
		for (int i = 0; i < children.size(); i++) {
			result += children.get(i);
			if (i < children.size()-1)
				result += " ";
		}
		
		return result + "))";
	}
	
	public static void main(String[] args) {
		RhythmicTree root = new RhythmicTree(1);
		
		root.addChild(new RhythmicTree(1));
		root.addChild(new RhythmicTree(1));
		root.addChild(new RhythmicTree(1));
		root.addChild(new RhythmicTree(1));
		
		System.out.println(root);
		
		
		RhythmicTree root2 = new RhythmicTree(2);
		
		RhythmicTree c1 = new RhythmicTree(1);
		RhythmicTree c2 = new RhythmicTree(1);
		
		root2.addChild(c1);
		root2.addChild(c2);
		
		c1.addChild(new RhythmicTree(1));
		c1.addChild(new RhythmicTree(1));
		c1.addChild(new RhythmicTree(1));
		c1.addChild(new RhythmicTree(1));
		
		c2.addChild(new RhythmicTree(1));
		c2.addChild(new RhythmicTree(1));
		c2.addChild(new RhythmicTree(1));
		c2.addChild(new RhythmicTree(1));
		
		System.out.println(root2);
		
		RhythmicTree root3 = new RhythmicTree(new Signature(3, 4));
		
		root3.addChild(new RhythmicTree(1));
		root3.addChild(new RhythmicTree(1));
		root3.addChild(new RhythmicTree(1));
		
		System.out.println(root3);
		
		
		
	}

}
