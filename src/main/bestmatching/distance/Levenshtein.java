package main.bestmatching.distance;

public class Levenshtein implements SimilarityDistance {
	
	@Override
	public int calculate(String str1, String str2) {
		return leveinstein(str1, str2, str1.length(), str2.length());
	}
	
	private int leveinstein(String str1, String str2, int i, int j) {
		if(Math.min(i,j) == 0)
			return Math.max(i, j);
		
		return Math.min(
				Math.min(
						leveinstein(str1, str2, i-1, j)+1,
						leveinstein(str1, str2, i, j-1)+1),
				leveinstein(str1, str2, i-1, j-1) + (str1.charAt(i-1) != str2.charAt(j-1) ? 1 : 0));
	}
}
