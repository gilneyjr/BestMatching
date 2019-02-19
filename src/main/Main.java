package main;

import java.util.Scanner;

public class Main { 
	private static String[] database = {"pato", "gato", "sapato", "chato"};
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		SimilarityDistance dist = new Levenshtein();
			
		System.out.print("Input: ");
		String str = in.next();
		
		System.out.println("> " + str);
		
		for (int i = 0; i < database.length; i++)
			System.out.println("[" + database[i] + "] = " + dist.calculate(str, database[i]));
		
		in.close();
	}
}
