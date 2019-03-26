package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import main.bestmatching.BestMatching;
import main.bestmatching.Levenshtein;
import main.sequential.SequentialBestMatching;

public class Main {
	private final static String FILENAME = "data/english_words.txt";
	private final static String WORD = "caty";
	private final static int N_WORDS = 40;
	
	public static void main(String[] args) {
		BestMatching bm;
		try {
			System.out.print("Loading dictionary... ");
			bm = new SequentialBestMatching(
					Files.readAllLines(Paths.get(FILENAME, "")),
					new Levenshtein());
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
