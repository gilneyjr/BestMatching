package main.bestmatching;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import main.bestmatching.dictionary.Word;

public abstract class BestMatching {
	public List<String> getMostSimilarWords(String word, int n_words) {		
		// Calculate distance for each word in dictionary
		Vector<Word> similarWords = new Vector<>();
		this.calculateDistance(similarWords, word);
		
		
		for(Word w : similarWords) {
			if(w == null)
				System.out.println("Null");
		}
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
	
	protected abstract void calculateDistance(Vector<Word> similarWords, String word);
}