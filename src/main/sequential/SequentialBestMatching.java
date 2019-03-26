package main.sequential;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import main.bestmatching.BestMatching;
import main.bestmatching.SimilarityDistance;

public class SequentialBestMatching implements BestMatching {
	private List<String> words;
	private SimilarityDistance sd;
	
	public SequentialBestMatching(List<String> words, SimilarityDistance sd) {
		this.words = words;
		this.sd = sd;
	}
	
	private class Word {
		private String content;
		private int similarity;
		public Word(String content, int similarity) {
			this.content = content;
			this.similarity = similarity;
		}
		public String getContent() {
			return content;
		}
		public int getSimilarity() {
			return similarity;
		}
	}
	
	@Override
	public List<String> getMostSimilarWords(String word, int n_words) {		
		// Calculate distance for each word in dictionary
		ArrayList<Word> similarWords = new ArrayList<>();
		
		for(int i = 0; i < words.size(); i++)
			similarWords.add(
					new Word(words.get(i), sd.calculate(word, words.get(i))));		
		
		// Sort most similarWords
		similarWords.sort(new Comparator<Word>() {
			@Override
			public int compare(Word w1, Word w2) {
				if(w1.getSimilarity() < w2.getSimilarity())
					return -1;
				else if(w1.getSimilarity() == w2.getSimilarity())
					return 0;
				else
					return 1;
			}
		});
		
		// Return only n_words words
		ArrayList<String> result = new ArrayList<>();
		for(int i = 0; i < n_words && i < similarWords.size(); i++) 
			result.add(similarWords.get(i).getContent());
		return result;
	}
}
