package main.bestmatching;

public class LevenshteinOld implements SimilarityDistance {

	@Override
	public int calculate(String str1, String str2) {
		if(Math.min(str1.length(), str2.length()) == 0)
			return Math.max(str1.length(), str2.length());
		
		String str1_sub = str1.substring(0, str1.length()-1);
		String str2_sub = str2.substring(0, str2.length()-1);
		
		return Math.min(
			Math.min(
				calculate(str1_sub, str2) + 1, 
				calculate(str1, str2_sub) + 1
			),
			calculate(str1_sub, str2_sub)
				+ (str1.charAt(str1.length()-1) != str2.charAt(str2.length()-1) ? 1 : 0)
		);
	}
}
