package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import main.bestmatching.BestMatching;
import main.bestmatching.Levenshtein;
import main.sequential.SequentialBestMatching;

public class Main {
	private final static String FILENAME = "data/english_words.txt";
	
	public static void main(String[] args) {
		try {
			BestMatching bm = new SequentialBestMatching(
					new BufferedReader(new FileReader(FILENAME)),
					new Levenshtein());
			
			String word = "caty";
			
			List<String> words = bm.getMostSimilarWords(word);
			
			System.out.print(words.size() + " most similar words: { ");
			for(String _word : words) {
				System.out.print(_word + " ");
			}
			System.out.println("}");
		} catch (FileNotFoundException e) {
			System.err.println("Could not open file \""+ FILENAME + "\"! Exiting...");
		}
	}
}
