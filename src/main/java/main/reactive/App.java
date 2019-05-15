package main.reactive;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.SubmissionPublisher;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App {
	private static final String DICTIONARY = "data/dictionary.txt";
	private static final String INPUT_WORDS = "data/input_words.txt";
	private static final String OUTPUT = "data/output/";
	
	public static void main(String[] args) throws IOException {
		SubmissionPublisher<Similarity> publisher = new SubmissionPublisher<>(); 
		Producer producer = new Producer(publisher);
		SimilarityCalculator calculator = new SimilarityCalculator();
		Consumer consumer = new Consumer(OUTPUT);
		
		publisher.subscribe(calculator);
		calculator.subscribe(consumer);
		
		System.out.println("Running...");
		long start = System.currentTimeMillis();
		producer.init(Paths.get(DICTIONARY), Paths.get(INPUT_WORDS));
		long endRead = System.currentTimeMillis();
		producer.run();
		long endRun = System.currentTimeMillis();
		System.out.println("Duration (Read): "
				+ ((endRead-start)/60000) + "min "
				+ (((endRead-start)%60000)/1000) + "."
				+ (((endRead-start)%60000)%1000) + "s");
		System.out.println("Duration (Run): "
				+ ((endRun-endRead)/60000) + "min "
				+ (((endRun-endRead)%60000)/1000) + "."
				+ (((endRun-endRead)%60000)%1000) + "s");
		System.out.println("Duration (Total): "
				+ ((endRun-start)/60000) + "min "
				+ (((endRun-start)%60000)/1000) + "."
				+ (((endRun-start)%60000)%1000) + "s");
	}
}
