package main.sequential;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import main.bestmatching.BestMatching;
import main.bestmatching.SimilarityDistance;

public class SequentialBestMatching implements BestMatching {
	private BufferedReader bf;
	private SimilarityDistance sd;
	
	public SequentialBestMatching(BufferedReader bf, SimilarityDistance sd) {
		this.bf = bf;
		this.sd = sd;
	}
	
	@Override
	public List<String> getMostSimilarWords(String word) {
		List<String> similarWords = new ArrayList<>();
		int min = word.length();
		
		String str;
		
		try {
			while((str = bf.readLine()) != null) {
				int similarity = sd.calculate(word, str);
				if(similarity < min) {
					min = similarity;
					similarWords.clear();
					similarWords.add(str);
				}
				else if(similarity == min) {
					similarWords.add(str);
				}
			}
		} catch (IOException e) {
			// TODO Resolve it later
			e.printStackTrace();
		}
		
		return similarWords;
	}
}
