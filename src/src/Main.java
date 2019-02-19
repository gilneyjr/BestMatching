package src;

import java.util.Scanner;

public class Main { 
	private static String[] database = {"pato", "gato", "sapato", "chato"};
	
	private static int min(int a, int b) {
		return a < b ? a : b;
	}
	
	private static int max(int a, int b) {
		return a > b ? a : b;
	}
	
	public static int leivenshtein(String str1, String str2) {
		if(min(str1.length(), str2.length()) == 0)
			return max(str1.length(), str2.length());
		
		return min(
			min(
				leivenshtein(str1.substring(0, str1.length()-1), str2), 
				leivenshtein(str1, str2.substring(0, str2.length()-1))
			),
			leivenshtein(str1.substring(0, str1.length()-1), str2.substring(0, str2.length()-1))
				+ (str1.charAt(str1.length()-1) != str2.charAt(str2.length()-1) ? 1 : 0)
		);
	}
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		System.out.print("Input: ");
		String str = in.next();
		
		System.out.println("> " + str);
		
		for (int i = 0; i < database.length; i++)
			System.out.println("[" + database[i] + "] = " + leivenshtein(str, database[i]));
		
		in.close();
	}
}
