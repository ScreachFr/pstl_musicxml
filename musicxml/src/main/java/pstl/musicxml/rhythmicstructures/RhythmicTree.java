package pstl.musicxml.rhythmicstructures;

import java.util.ArrayList;

import pstl.musicxml.musicalstructures.Signature;
import pstl.musicxml.musicalstructures.Type;

public class RhythmicTree {
	private Signature signture;
	private Fraction fraction;
	
	private ArrayList<RhythmicTree> children;
	public RhythmicTree(Signature signature) {
		this();
		this.signture = signature;
		fraction = new Fraction(0, Type.UNDEFINED);
	}
	
	public RhythmicTree(Fraction f) {
		this();
		this.fraction = f;
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

	public Fraction getFraction() {
		return fraction;
	}
	
	public void setSignture(Signature signture) {
		this.signture = signture;
	}
	
	public void setFraction(Fraction fraction) {
		this.fraction = fraction;
	}
	
	@Override
	public String toString() {
		if (children.size() == 0) {
			if (fraction != null && (!fraction.getBaseType().equals(Type.UNKNOWN) || !fraction.getBaseType().equals(Type.UNDEFINED)))
				return fraction.toRTString() + "";
			else if (signture != null)
				return signture + "";
			else 
				return "";
		}
		
		String result = "(";
		
		
		
		if (fraction != null && (!fraction.getBaseType().equals(Type.UNKNOWN) || !fraction.getBaseType().equals(Type.UNDEFINED)))
			result += fraction.toRTString() + " ";
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
		RhythmicTree root = new RhythmicTree(FractionFactory.newQuarter());
		
		root.addChild(new RhythmicTree(FractionFactory.newQuarter()));
		root.addChild(new RhythmicTree(FractionFactory.newQuarter()));
		root.addChild(new RhythmicTree(FractionFactory.newQuarter()));
		root.addChild(new RhythmicTree(FractionFactory.newQuarter()));
		
		System.out.println(root);
		
		
		RhythmicTree root2 = new RhythmicTree(FractionFactory.newHalf());
		
		RhythmicTree c1 = new RhythmicTree(FractionFactory.newQuarter());
		RhythmicTree c2 = new RhythmicTree(FractionFactory.newQuarter());
		
		root2.addChild(c1);
		root2.addChild(c2);
		
		c1.addChild(new RhythmicTree(FractionFactory.newQuarter()));
		c1.addChild(new RhythmicTree(FractionFactory.newQuarter()));
		c1.addChild(new RhythmicTree(FractionFactory.newQuarter()));
		c1.addChild(new RhythmicTree(FractionFactory.newQuarter()));
		
		c2.addChild(new RhythmicTree(FractionFactory.newQuarter()));
		c2.addChild(new RhythmicTree(FractionFactory.newQuarter()));
		c2.addChild(new RhythmicTree(FractionFactory.newQuarter()));
		c2.addChild(new RhythmicTree(FractionFactory.newQuarter()));
		
		System.out.println(root2);
		
		RhythmicTree root3 = new RhythmicTree(new Signature(3, 4));
		
		root3.addChild(new RhythmicTree(FractionFactory.newQuarter()));
		root3.addChild(new RhythmicTree(FractionFactory.newQuarter()));
		root3.addChild(new RhythmicTree(FractionFactory.newQuarter()));
		
		System.out.println(root3);
		
		
		
	}

}
