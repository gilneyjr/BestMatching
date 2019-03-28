package main.sequential;

import java.util.Vector;

import main.bestmatching.BestMatching;
import main.bestmatching.dictionary.Dictionary;
import main.bestmatching.dictionary.Word;
import main.bestmatching.distance.SimilarityDistance;

public class SequentialBestMatching extends BestMatching {
	private Dictionary dictionary;
	private SimilarityDistance sd;
	
	public SequentialBestMatching(Dictionary dicionary, SimilarityDistance sd) {
		super();
		this.dictionary = dicionary;
		this.sd = sd;
	}

	@Override
	protected void calculateDistance(Vector<Word> similarWords, String word) {
		String dic_word;
		while((dic_word = dictionary.getNextWord()) != null)
			similarWords.add(
					new Word(dic_word, sd.calculate(word, dic_word)));
	}
}
