package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import main.bestmatching.BestMatching;
import main.bestmatching.distance.Levenshtein;
import main.mutex.MutexBestMatching;
import main.mutex.MutexDictionary;
import main.sequential.SequentialBestMatching;
import main.sequential.SequentialDictionary;

public class Main {
	private final static String FILENAME = "data/english_words.txt";
	private final static String WORD = "caty";
	private final static int N_WORDS = 40;
	
	@SuppressWarnings("unused")
	private static BestMatching bestMatchingSeq() throws IOException {
		return new SequentialBestMatching(
				new SequentialDictionary(Files.readAllLines(Paths.get(FILENAME, ""))),
				new Levenshtein());
	}
	
	@SuppressWarnings("unused")
	private static BestMatching bestMatchingMutex() throws IOException {
		return new MutexBestMatching(
				new MutexDictionary(Files.readAllLines(Paths.get(FILENAME, ""))),
				new Levenshtein());
	}
	
	public static void main(String[] args) {
		try {
			System.out.print("Loading dictionary... ");
			BestMatching bm = bestMatchingMutex();
			System.out.println("Ok!");
			
			System.out.print("Computing similarity distance... ");
			long start = System.currentTimeMillis();
			List<String> words = bm.getMostSimilarWords(WORD, N_WORDS);
			long end = System.currentTimeMillis();
			System.out.println("Ok! Duration: "
					+ ((end-start)/60000) + "min "
					+ (((end-start)%60000)/1000) + "."
					+ (((end-start)%60000)%1000) + "s");
			
			System.out.print(words.size() + " most similar words: { ");
			for(String word : words) {
				System.out.print(word + " ");
			}
			System.out.println("}");
		} catch (IOException e) {
			System.err.println("Could not open file \""+ FILENAME + "\"! Exiting...");
		}
	}
}
