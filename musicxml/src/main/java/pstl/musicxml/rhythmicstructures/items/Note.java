package pstl.musicxml.rhythmicstructures.items;

import java.util.ArrayList;

import pstl.musicxml.Type;
import pstl.musicxml.symboles.ExtraSymbol;


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
