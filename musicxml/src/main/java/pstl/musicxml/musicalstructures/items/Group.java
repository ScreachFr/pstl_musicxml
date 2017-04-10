package pstl.musicxml.musicalstructures.items;

import java.util.ArrayList;

import pstl.musicxml.musicalstructures.Type;
import pstl.musicxml.musicalstructures.symbols.binary.Beam;
import pstl.musicxml.rhythmicstructures.Fraction;

public class Group implements IMusicalItem {
	private Fraction fraction;
	private ArrayList<IMusicalItem> items;
	private int number;
	
	public Group(int number, Fraction fraction) {
		this.number = number;
		this.fraction = fraction;
		items = new ArrayList<>();
	}
	
	
	public ArrayList<IMusicalItem> getItems() {
		return items;
	}
	
	public void addItem(IMusicalItem item) {
		items.add(item);
	}
	
	public Fraction getFraction() {
		return fraction;
	}
	
	
	public void checkGroups() {
		ArrayList<Chord> beamMembers = new ArrayList<>();
		Chord crtChord = null;
		ArrayList<Beam> crtBeams;

		int number = -1;
		int startIndice = -1;
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i) instanceof Chord) {
				crtChord = (Chord) items.get(i);
				crtBeams = crtChord.getBeams();
				

				if (crtBeams.isEmpty())//No beam
					continue;

				if (number == -1) {
					startIndice = i;
					number = crtBeams.get(0).getNumber();
					beamMembers.add(crtChord);
				} else {
					for (Beam beam : crtBeams) {
						if (beam.getNumber() == number) {
							beamMembers.add(crtChord);
							break;
						}

					}
				}
			}
		}
		
		if (!beamMembers.isEmpty()) {
			if (startIndice < 0) {
				throw new Error("startIndice < 0, this shouldn't happens");
			}
			
			crtChord = null;
			crtChord = beamMembers.get(0);
			Fraction sum = new Fraction(1, crtChord.getType());
			
			for (Chord chord : beamMembers) {
				sum = sum.add(new Fraction(1, chord.getType()));
			}
			
			Group g = new Group(number, sum);
			
			beamMembers.forEach(item -> g.addItem(item));
			
			items.set(startIndice, g);
			
			g.removeBeams(number);
			g.checkGroups();
			
			
			beamMembers.forEach(item -> items.remove(item));
		}
		
		//Convert until there's no more beam.
		if (number != -1)
			checkGroups();

	}
	
	
	public void removeBeams(int number) {
		if (items == null)
			return;
		
		items.forEach(item -> {
			if (item instanceof Chord) {
				Chord c = (Chord) item;
				c.removeBeam(number);
			}
		});
		
	}
	
	@Override
	public Type getType() {
		return fraction.getBaseType();
	}

	@Override
	public String toMeasureString() {
		String result = "(#" + number + " ";
		
		for (IMusicalItem item : items) {
			result += item;
		}
		
		result += ")";
		
		return result;
	}
	
	@Override
	public String toString() {
		return toMeasureString();
	}
	
}
