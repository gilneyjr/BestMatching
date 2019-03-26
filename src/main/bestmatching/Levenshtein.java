package main.bestmatching;

public class Levenshtein implements SimilarityDistance {
	private String str1;
	private String str2;
	
	@Override
	public int calculate(String str1, String str2) {
		this.str1 = str1;
		this.str2 = str2;
		
		return leveinstein(str1.length(), str2.length());
	}
	
	private int leveinstein(int i, int j) {
		if(Math.min(i,j) == 0)
			return Math.max(i, j);
		
		return Math.min(
				Math.min(
						leveinstein(i-1, j)+1,
						leveinstein(i, j-1)+1),
				leveinstein(i-1, j-1) + (str1.charAt(i-1) != str2.charAt(j-1) ? 1 : 0));
	}
}
