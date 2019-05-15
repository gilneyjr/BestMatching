package main;

import main.stream.BestMatchingStream;

public class Main {
	private static final String DICTIONARY = "data/dictionary.txt";
	private static final String INPUT_WORDS = "data/input_words.txt";
	private static final String OUTPUT = "data/output/";
	
	public static void main(String[] args) {
		for (int i = 0; i < 8; i++) {
			System.out.println("Parallel Stream Algorithm!");
			System.out.println("Running...");
			long start = System.currentTimeMillis();
			new BestMatchingStream().calculate(DICTIONARY, INPUT_WORDS, OUTPUT);
			long end = System.currentTimeMillis();
			System.out.println("Duration: "
					+ ((end-start)/60000) + "min "
					+ (((end-start)%60000)/1000) + "."
					+ (((end-start)%60000)%1000) + "s");
		} 
	}
}
