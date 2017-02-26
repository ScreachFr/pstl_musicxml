package pstl.musicxml.musicalstructures.items;

import java.util.ArrayList;

import pstl.musicxml.musicalstructures.Type;
import pstl.musicxml.musicalstructures.symbols.ExtraSymbol;
import pstl.musicxml.musicalstructures.symbols.binary.Beam;


public class Note implements IMusicalItem {
	protected String step;
	protected int octave;
	protected int voice;
	protected Type type;
	protected ArrayList<ExtraSymbol> extraSymbols;

	public Note(String step, int octave, Type type) {
		this.step = step;
		this.octave = octave;
		this.type = type;
		voice = 1;
	}

	public Note(String pitch, int octave, Type type, int voice) {
		this(pitch, octave, type);
		this.voice = voice;
	}

	public Type getType() {
		return type;
	}
	
	public String getPitch() {
		return step;
	}

	public int getVoice() {
		return voice;
	}

	public void setVoice(int voice) {
		this.voice = voice;
	}
	
	public void addExtraSymbol(ExtraSymbol e) {
		if (extraSymbols == null)
			extraSymbols = new ArrayList<>();
		
		extraSymbols.add(e);
	}
	
	public ArrayList<ExtraSymbol> getExtraSymbols() {
		return extraSymbols;
	}
	
	public ArrayList<Beam> getBeams() {
		ArrayList<Beam> beams =  new ArrayList<>();
		
		if (extraSymbols == null)
			return beams;
		
		for (ExtraSymbol e : extraSymbols) {
			if (e instanceof Beam)
				beams.add((Beam) e);
		}
		
		return beams;
	}
	
	public void removeBeam(int number) {
		ExtraSymbol e;
		Beam b;
		ArrayList<Beam> toRemove = new ArrayList<>();
		for (int i = 0; i < extraSymbols.size(); i++) {
			e = extraSymbols.get(i);
			
			if (e instanceof Beam) {
				b = (Beam) e;
				
				if (b.getNumber() == number) {
					toRemove.add(b);
				}
			}
		}
		
		toRemove.forEach(item -> extraSymbols.remove(item));
	}
	
	@Override
	public String toMeasureString() {
		return toString();
	}
	
	@Override
	public String toString() {
		String extraSymbolsString = "";
		
		if (extraSymbols != null && !extraSymbols.isEmpty()) {
			extraSymbolsString += "(";
			
			for (int i = 0; i < extraSymbols.size(); i++) {
				extraSymbolsString += extraSymbols.get(i);
				
				if (i < extraSymbols.size() -1)
					extraSymbolsString += " ";
			}
			
			extraSymbolsString += ")";
			
		}
		
		return step + "" +  octave + ":" + type.getNumber() + extraSymbolsString;


	}
}
