package pstl.musicxml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import pstl.musicxml.musicalstructures.Score;
import pstl.musicxml.parsing.ParseException;
import pstl.musicxml.parsing.XMLParser;
import pstl.musicxml.rhythmicstructures.RhythmicTree;
import pstl.musicxml.rhythmicstructures.RhythmicTreeFactory;
import pstl.musicxml.tools.FileUtils;import pstl.musicxml.tools.ScoreUtils;


public class Demo {
	public final static int ALL = 0, RT_ONLY = 1, SCORE_ONLY = 2;

	public static XMLParser parser;
	public static int mode = ALL;
	public static boolean rec = false;
	
	public static void main(String[] args) {
		
		
		if (args.length < 1) {
			System.out.println("Too few args, data path needed in args[0].");
			System.out.println("usage : <path> <rec> <display option>");
			System.out.println("path : can be a file or a directory");
			System.out.println("rec : r = recurcive, empty = not recurcive");
			System.out.println("display option : empty = display rt and score, rt = display rt only, s = display score only");
			System.exit(1);
		}
		
		
		for (int i = 1; i < args.length; i++) {
			String arg = args[i];
			
			switch (arg) {
			case "r":
				rec = true;
				break;
			case "rt":
				mode = RT_ONLY;
				break;
			case "s":
				mode = SCORE_ONLY;
				break;
			}
		}

		System.out.println("mode : " + mode + ", rec : " + rec);
		
		File path = new File(args[0]);
		
		if (!path.exists()) {
			System.out.println("No file or directory at " + args[0] + ".");
			System.exit(1);
		}
		
		
		File rng = new File("grammars/rng/musicXML.rng");
		
		
		parser = new XMLParser();
		try {
			parser.setRng(rng);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		ArrayList<Score> parsedScores = new ArrayList<>();
		ArrayList<RhythmicTree> rts = new ArrayList<>();
		
		parsedScores.addAll(parseScores(path));
		parsedScores.forEach(score -> rts.addAll(RhythmicTreeFactory.buildRtFromScore(score)));
		
		if (mode == RT_ONLY || mode == ALL) {
			System.out.println("RTS");
			System.out.println("-------------------------");
			rts.forEach(System.out::println);
		}
		if (mode == SCORE_ONLY || mode == ALL) {
			System.out.println("SCORES");
			System.out.println("-------------------------");
			parsedScores.forEach(System.out::println);
		}
		
	} 
	
	private static ArrayList<Score> parseScores(File f) {
		ArrayList<Score> result = new ArrayList<>();
		
		
		if (!f.isDirectory()) { // Single file.
			parser.setInput(f.getAbsolutePath());
			System.out.println("Parsing " + f + ".");
			try {
				result.add(ScoreUtils.loadFromDom(parser.getDocument()));
			} catch (ParseException e) {
				System.out.println("Can't parse " + f);
				e.printStackTrace();
			}
		} else if (rec) { // Directory, launch recursion.
			try {
				Collection<File> files = FileUtils.getFileList(f.getAbsolutePath());
				
				files.forEach(crtFile -> result.addAll(parseScores(crtFile)));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		return result;
	}
	
}
