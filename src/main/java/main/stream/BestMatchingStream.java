package main.stream;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import main.util.Pair;

public class BestMatchingStream {
	
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
	
	private List<String> read(String path) {
		List<String> list = null;
		try {
			list = Files.lines(Paths.get(path)).collect(Collectors.toList());
		} catch (IOException e) {
			System.out.println("Não foi possível abrir arquivo \"" + path + "\".");
			System.exit(-1);
		}
		return list;
	}
	
	public void run(String dicPath, String inputPath) {
		final List<String> dic = read(dicPath);
		
		try(BufferedReader br = new BufferedReader(new FileReader(new File(inputPath)))) {
			Stream<String> input = br.lines();
			input.parallel()
				.forEach(inputStr -> {
					try(BufferedWriter bw = new BufferedWriter(new FileWriter(new File("data/output/"+inputStr+".csv")))) {
						List<Pair> result = dic.parallelStream()
							// calculate similarity
							.map(dicStr -> new Pair(similarityDistance(inputStr, dicStr), dicStr))
							// sort
							.sorted((p1, p2) -> {
								if(p1.getFirst() == p2.getFirst())
									return p1.getSecond().compareTo(p2.getSecond());
								return p1.getFirst() - p2.getFirst();
							})
							// collect to list
							.collect(Collectors.toList());
						
						// Write to a file
						bw.write("Words;Similarity to \""+inputStr+"\"\n");
						for(Pair p : result)
							bw.write(p.getSecond() + ";" + p.getFirst().toString() + "\n");
					}
					catch(IOException e) {
						System.err.println("Could not write \"data/output/" + inputStr + ".csv\".");
						System.exit(-1);
					}
				});
		} catch (IOException e) {
			System.out.println("Não foi possível abrir arquivo \"" + inputPath + "\".");
			System.exit(-1);
		}
	}

}
