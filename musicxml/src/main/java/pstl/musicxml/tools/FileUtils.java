package pstl.musicxml.tools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class FileUtils {
	public static String getTextFromFile(String filePath) throws IOException {
		String result = "";
		File f = new File(filePath);
		Scanner sc;
		if (!f.exists()) {
			throw new IOException(f + " does not exist !");
		}
		
		sc = new Scanner(f);
		
		while (sc.hasNextLine()) {
			result += sc.nextLine() + "\n";
		}
		
		sc.close();
		
		return result;
	}
	
	
	public static ArrayList<File> getFilesByExt(File dir, String ext) throws IOException {
		if (!dir.exists())
			throw new IOException(dir + " does not exist!");
		else if (!dir.isDirectory())
			throw new IOException(dir + " is not a directory!");
		
		ArrayList<File> result = new ArrayList<File>();
		File[] files = dir.listFiles();
		String[] tmp;

		for (File f : files) {
			tmp = f.getName().split(".");
			
			if (tmp.length > 1) {
				
				if (tmp[tmp.length-1].equals(ext)) {
					result.add(f);
				}
			}
		}
		
		return result;
	}
	
	public static Collection<File> getFileList(String dir) throws IOException {
		Collection<File> result = new ArrayList<File>();
		
		File d = new File(dir);
		
		System.out.println(d.getAbsolutePath());
		
		if (!d.isDirectory())
			throw new IOException(dir + " is not a directory");
		
		String[] files = d.list();
		
		for (String s : files) {
			result.add(new File(d.getAbsolutePath() + "/" + s));
		}
		
		return result;
	}
	
}
