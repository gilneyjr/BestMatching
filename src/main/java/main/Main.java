package main;

import main.concurrent.mutex.PairsMutex;
import main.forkjoinApproach.BestMatchingForkJoin;
import main.util.BestMatching;

public class Main {
	private static final String DICTIONARY = "data/dictionary.txt";
	private static final String INPUT_WORDS = "data/input_words.txt";
	
	public static void test(BestMatching bm) {
		System.out.println("Running...");
		long start = System.currentTimeMillis();
		bm.calculate(DICTIONARY, INPUT_WORDS);
		long end = System.currentTimeMillis();
		System.out.println("Duration: "
				+ ((end-start)/60000) + "min "
				+ (((end-start)%60000)/1000) + "."
				+ (((end-start)%60000)%1000) + "s");
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 4; i++) {
			System.out.println("Mutex Algorithm!");
			test(new BestMatchingForkJoin(PairsMutex.class));
		}
	}
}
