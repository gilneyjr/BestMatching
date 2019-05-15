package main;

import main.stream.BestMatchingStream;

public class Main {
	private static final String DICTIONARY = "data/dictionary.txt";
	private static final String INPUT_WORDS = "data/input_words.txt";
	
	public static void main(String[] args) {
		for (int i = 0; i < 8; i++) {
			System.out.println("Parallel Stream Algorithm!");
			System.out.println("Running...");
			long start = System.currentTimeMillis();
			new BestMatchingStream().run(DICTIONARY, INPUT_WORDS);
			long end = System.currentTimeMillis();
			System.out.println("Duration: "
					+ ((end-start)/60000) + "min "
					+ (((end-start)%60000)/1000) + "."
					+ (((end-start)%60000)%1000) + "s");
		} 
	}
}
