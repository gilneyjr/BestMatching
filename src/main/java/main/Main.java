package main;

import main.concurrent.BestMatchingConcurrent;
import main.concurrent.mutex.CounterMutex;
import main.concurrent.mutex.PairsMutex;
import main.concurrent.mutex.StringsMutex;
import main.concurrent.semaphore.CounterSemaphore;
import main.concurrent.semaphore.PairsSemaphore;
import main.concurrent.semaphore.StringsSemaphore;
import main.sequential.BestMatchingSequential;
import main.sequential.util.StringsSequential;
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
		System.out.println("Sequential Algorithm!");
		test(new BestMatchingSequential(StringsSequential.class));
		System.out.println("Mutex Algorithm!");
		test(new BestMatchingConcurrent(StringsMutex.class, CounterMutex.class, PairsMutex.class));
		System.out.println("Semaphore Algorithm!");
		test(new BestMatchingConcurrent(StringsSemaphore.class, CounterSemaphore.class, PairsSemaphore.class));
	}
}
