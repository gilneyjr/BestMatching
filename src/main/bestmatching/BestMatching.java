package main.bestmatching;

import java.util.List;

public interface BestMatching {
	List<String> getMostSimilarWords(String word, int n_words);
}