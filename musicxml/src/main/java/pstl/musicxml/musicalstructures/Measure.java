package pstl.musicxml.musicalstructures;

import java.util.ArrayList;


import pstl.musicxml.musicalstructures.items.Chord;
import pstl.musicxml.musicalstructures.items.Group;
import pstl.musicxml.musicalstructures.items.IMusicalItem;
import pstl.musicxml.musicalstructures.symbols.binary.Beam;
import pstl.musicxml.rhythmicstructures.Fraction;


public class Measure extends Group implements Comparable<Measure>{
	private int number;
	private ArrayList<IMusicalItem> items;
	private Signature signature;
	private Metronome metronome;

	public Measure(int number) {
		super(number, null);
		this.number = number;
		items = new ArrayList<>();
	}

	public ArrayList<IMusicalItem> getItems() {
		return items;
	}

	public void setSignature(Signature signature) {
		this.signature = signature;
	}

	public Signature getSignature() {
		return signature;
	}

	public void addItem(IMusicalItem i) {
		items.add(i);
	}

	public void setItems(ArrayList<IMusicalItem> items) {
		this.items = items;
	}

	public Metronome getMetronome() {
		return metronome;
	}
	
	public void setMetronome(Metronome metronome) {
		this.metronome = metronome;
	}
	
	@Override
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
			
			Fraction sum = null;
			
			
			for (Chord chord : beamMembers) {
				if (sum == null)
					sum = new Fraction(1, chord.getType());
				else
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

	@Override
	public int compareTo(Measure o) {
		return Integer.compare(number, o.number);
	}

	@Override
	public String toString() {
		String result = "";

		result += "#" + number + "(" ;

		if (signature == null)
			result += "NS" + " ";
		else
			result += signature + " ";

		if (items.isEmpty()) {
			result += "Empty";
		} else {
			for (IMusicalItem c : items) {
				result += c.toMeasureString() + " ";
			}
		}

		result += ")";
		return result;
	}
}









