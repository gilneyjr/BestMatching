package main.mutex;


import java.util.Vector;

import main.bestmatching.dictionary.Dictionary;
import main.bestmatching.dictionary.Word;
import main.bestmatching.distance.SimilarityDistance;

public class MutexRunnable implements Runnable {
	private Dictionary dictionary;
	private Vector<Word> similarWords;
	private SimilarityDistance sd;
	private String word;
	
	public MutexRunnable(Dictionary dictionary, Vector<Word> similarWords, SimilarityDistance sd, String word) {
		this.dictionary = dictionary;
		this.similarWords = similarWords;
		this.sd = sd;
		this.word = word;
	}
	
	@Override
	public void run() {
		String dic_word;
		while((dic_word = dictionary.getNextWord()) != null) {
			similarWords.add(
					new Word(dic_word, sd.calculate(word, dic_word)));
		}
	}
}
