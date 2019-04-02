package main.sequential;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import main.util.BestMatching;
import main.util.Pair;
import main.util.Strings;

public class BestMatchingSequential extends BestMatching {

	public BestMatchingSequential(Class<? extends Strings> stringsClass) {
		super(stringsClass);
	}
	
	private void read(String path, Strings content) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			
			String line;
            while((line = br.readLine()) != null) {
                content.add(line);
            }
            br.close();
			
		} catch (FileNotFoundException e) {
			System.err.println("Could not read \""+path+"\" file.");
		} catch (IOException e) {
			System.err.println("Error while reading \""+path+"\" file.");
		}
	}
	
	private int levenshtein(int[][] memo, String str1, String str2, int i, int j) {
		if(Math.min(i,j) == 0)
			return Math.max(i, j);
		
		if(memo[i-1][j-1] != -1)
			return memo[i-1][j-1];
		
		memo[i-1][j-1] = Math.min(
				Math.min(
						levenshtein(memo, str1, str2, i-1, j)+1,
						levenshtein(memo, str1, str2, i, j-1)+1),
				levenshtein(memo, str1, str2, i-1, j-1) + (str1.charAt(i-1) != str2.charAt(j-1) ? 1 : 0));
		return memo[i-1][j-1];
	}
	
	private int similarityDistance(String str1, String str2) {
		int [][] memo = new int[str1.length()][str2.length()];
		for (int i = 0; i < str1.length(); i++)
			for (int j = 0; j < str2.length(); j++)
				memo[i][j] = -1;
		return levenshtein(memo, str1, str2, str1.length(), str2.length());
	}

	@Override
	public void read(String dicPath, Strings dic, String inputPath, Strings input) {
		read(dicPath, dic);
		read(inputPath, input);
	}

	@Override
	public void calculateAndWrite(Strings dic, Strings input) {
		for(int i = 0; i < input.size(); i++) {
			List<Pair> sim = new ArrayList<>();
			for(int j = 0; j < dic.size(); j++) {
				sim.add(new Pair(similarityDistance(input.get(i), dic.get(j)), dic.get(j)));
			}
			
			sim.sort(new Comparator<Pair>() {
				@Override
				public int compare(Pair p1, Pair p2) {
					if(p1.getFirst() == p2.getFirst())
						return p1.getSecond().compareTo(p2.getSecond());
					return p1.getFirst() - p2.getFirst();
				}
			});
			
			try {
				FileWriter fw = new FileWriter("data/output/" + input.get(i) + ".csv");
				fw.write("Words;Similarity to \""+input.get(i)+"\"\n");
				for(Pair p : sim) {
					String str = p.getSecond() + ";" + p.getFirst().toString() + "\n";
					fw.write(str);
				}
					
				fw.close();
			} catch (IOException e) {
				System.err.println("Could not write \"data/output/" + input.get(i) + ".csv\".");
			}
		}
	}
}
