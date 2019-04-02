package main.concurrent.runnable;

import main.util.Counter;
import main.util.Pair;
import main.util.Pairs;
import main.util.Strings;

public class CalculateSimilarityRunnable implements Runnable {

	private final Strings dic;
	private final Pairs sim;
	private final String input;
	private Counter count;
	
	public CalculateSimilarityRunnable(Strings dic, Pairs sim, String input, Counter count) {
		this.dic = dic;
		this.sim = sim;
		this.input = input;
		this.count = count;
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
	public void run() {
		int i_dic;
		
		while((i_dic = count.next()) < dic.size()) {
			String dic_word = dic.get(i_dic);
			sim.add(new Pair(similarityDistance(dic_word, input), dic_word));
		}
	}
}
