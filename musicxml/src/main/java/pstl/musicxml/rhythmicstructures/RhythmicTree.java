package pstl.musicxml.rhythmicstructures;

import java.util.ArrayList;

import pstl.musicxml.Type;

public class RhythmicTree {
	private Signature signture;
	private Type type;
	
	private ArrayList<RhythmicTree> children;
	public RhythmicTree(Signature signature) {
		this();
		this.signture = signature;
		type = Type.UNDEFINED;
	}
	
	public RhythmicTree(Type type) {
		this();
		this.type = type;
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

	public Type getType() {
		return type;
	}
	
	public void setSignture(Signature signture) {
		this.signture = signture;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		if (children.size() == 0) {
			if (type != null && (!type.equals(Type.UNKNOWN) || !type.equals(Type.UNDEFINED)))
				return type.getNumber() + "";
			else if (signture != null)
				return signture + "";
			else 
				return "";
		}
		
		String result = "(";
		
		
		
		if (type != null && (!type.equals(Type.UNKNOWN) || !type.equals(Type.UNDEFINED)))
			result += type.getNumber() + " ";
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
		RhythmicTree root = new RhythmicTree(Type.QUARTER);
		
		root.addChild(new RhythmicTree(Type.QUARTER));
		root.addChild(new RhythmicTree(Type.QUARTER));
		root.addChild(new RhythmicTree(Type.QUARTER));
		root.addChild(new RhythmicTree(Type.QUARTER));
		
		System.out.println(root);
		
		
		RhythmicTree root2 = new RhythmicTree(Type.HALF);
		
		RhythmicTree c1 = new RhythmicTree(Type.QUARTER);
		RhythmicTree c2 = new RhythmicTree(Type.QUARTER);
		
		root2.addChild(c1);
		root2.addChild(c2);
		
		c1.addChild(new RhythmicTree(Type.QUARTER));
		c1.addChild(new RhythmicTree(Type.QUARTER));
		c1.addChild(new RhythmicTree(Type.QUARTER));
		c1.addChild(new RhythmicTree(Type.QUARTER));
		
		c2.addChild(new RhythmicTree(Type.QUARTER));
		c2.addChild(new RhythmicTree(Type.QUARTER));
		c2.addChild(new RhythmicTree(Type.QUARTER));
		c2.addChild(new RhythmicTree(Type.QUARTER));
		
		System.out.println(root2);
		
		RhythmicTree root3 = new RhythmicTree(new Signature(3, 4));
		
		root3.addChild(new RhythmicTree(Type.QUARTER));
		root3.addChild(new RhythmicTree(Type.QUARTER));
		root3.addChild(new RhythmicTree(Type.QUARTER));
		
		System.out.println(root3);
		
		
		
	}

}
