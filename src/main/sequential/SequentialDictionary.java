package main.sequential;

import java.util.List;

import main.bestmatching.dictionary.Dictionary;

public class SequentialDictionary implements Dictionary {
	private List<String> words;
	private int count;
	
	// Problema discutido na última aula sobre a referência poder ser acessada por agentes externos à classe
	public SequentialDictionary(List<String> words) {
		this.words = words;
		this.count = 0;
	}
	
	@Override
	public String getNextWord() {
		if(count < words.size())
			return words.get(count++);
		return null;
	}
	
	// Cuidado com possível race condition entre este método e o método getNextWord.
	@Override
	public void resetCount() {
		this.count = 0;
	}
}
