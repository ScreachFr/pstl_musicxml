package pstl.musicxml.rhythmicstructures;

import java.util.ArrayList;

import pstl.musicxml.musicalstructures.Signature;
import pstl.musicxml.musicalstructures.Type;
import pstl.musicxml.musicalstructures.symbols.ExtraSymbol;

public class RhythmicTree {
	private Signature signature;
	private Fraction fraction;
	private ItemType itemType;
	private ArrayList<ExtraSymbol> extraSymbols;
	
	private ArrayList<RhythmicTree> children;
	
	
	public RhythmicTree(ItemType itemType, Signature signature) {
		this(itemType);
		this.signature = signature;
		fraction = new Fraction(0, Type.UNDEFINED);
	}
	
	public RhythmicTree(ItemType itemType, Fraction f) {
		this(itemType);
		this.fraction = f;
	}
	
	public RhythmicTree(ItemType itemType) {
		this.extraSymbols = new ArrayList<>();
		this.children = new ArrayList<>();
		this.itemType = itemType;
		
	}

	public void calculateFractions() {
		if (children.isEmpty())
			return;
		
//		if (signature == null && itemType == null) {
//			
//		}
		
		
		Type minType = children.get(0).fraction.getBaseType();
		
		for (RhythmicTree rt : children) {
			if (rt.getFraction() != null && minType.compareNumber(rt.fraction.getBaseType()) < 0) {
				minType = rt.fraction.getBaseType();
			}
		}
		
		final Type min = minType;
		
		
		children.forEach(item -> {
				if (item.getFraction() != null)
					item.getFraction().setRelativeValue(min);
			}
		);
		
//		for (RhythmicTree rt : children) {
//			rt.getFraction().setRelativeValue(minType);
//		}
		
//		children.forEach(item -> System.out.println(item.fraction));
		
		children.forEach(item -> item.calculateFractions());
		
	}
	
	
	public ArrayList<RhythmicTree> getChildren() {
		return children;
	}
	
	public RhythmicTree addChild(RhythmicTree rt) {
		children.add(rt);
		
		return rt;
	}
	
	public Signature getSignature() {
		return signature;
	}

	public Fraction getFraction() {
		return fraction;
	}
	
	public void setSignature(Signature signature) {
		this.signature = signature;
	}
	
	public void setFraction(Fraction fraction) {
		this.fraction = fraction;
	}
	
	public void addExtraSymbol(ExtraSymbol es) {
		extraSymbols.add(es);
	}
	
	public void addExtraSymbol(ArrayList<ExtraSymbol> es) {
		extraSymbols.addAll(es);
	}
	
	public ArrayList<ExtraSymbol> getExtraSymbols() {
		return extraSymbols;
	}
	
	// For debug purpose only.
	public ArrayList<ExtraSymbol> getAllExtraSymbols() {
		ArrayList<ExtraSymbol> result = new ArrayList<>();
		
		result.addAll(extraSymbols);
		
		children.forEach(child -> result.addAll(child.getAllExtraSymbols()));
		
		return result;
	}
	
	@Override
	public String toString() {
		if (children.size() == 0) {
			if (signature != null)
				return signature + "";
			else if (fraction != null && (!fraction.getBaseType().equals(Type.UNKNOWN) || !fraction.getBaseType().equals(Type.UNDEFINED))) 
				return itemType.toRTString(fraction.getValue()) + "";
			else 
				return "";
		}
		
		String result = "(";
		
		
		
		if (signature != null) {
			result += signature + " ";
		} else if (fraction != null && (!fraction.getBaseType().equals(Type.UNKNOWN) || !fraction.getBaseType().equals(Type.UNDEFINED)))  
			result += itemType.toRTString(fraction.getValue()) + " ";
			
		result += "(";
		
		
		for (int i = 0; i < children.size(); i++) {
			result += children.get(i);
			if (i < children.size()-1)
				result += " ";
		}
		
		return result + "))";
	}
	
	
	public static void main(String[] args) {
		RhythmicTree root = new RhythmicTree(ItemType.Measure, new Signature(4, 4));
		RhythmicTree rt = new RhythmicTree(ItemType.Note, FractionFactory.newQuarter());

		rt.addChild(new RhythmicTree(ItemType.Note, FractionFactory.newFraction(1, Type.EIGHTH)));
		rt.addChild(new RhythmicTree(ItemType.Note, FractionFactory.newFraction(1, Type.EIGHTH)));
		
		root.addChild(new RhythmicTree(ItemType.Note, FractionFactory.newQuarter()));
		root.addChild(rt);
		root.addChild(new RhythmicTree(ItemType.Rest, FractionFactory.newQuarter()));
		root.addChild(new RhythmicTree(ItemType.Note, FractionFactory.newFraction(1, Type.HALF)));
		root.addChild(new RhythmicTree(ItemType.Note, FractionFactory.newFraction(1, Type.SIXTY_FOURTH)));
		
		root.calculateFractions();
		
		System.out.println(root);
		
		
		
		
	}

}
