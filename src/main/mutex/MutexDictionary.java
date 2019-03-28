package main.mutex;

import java.util.List;

import main.bestmatching.dictionary.Dictionary;

public class MutexDictionary implements Dictionary {
	private List<String> words;
	private int count;
	
	// Problema discutido na última aula sobre a referência poder ser acessada por agentes externos à classe
	public MutexDictionary(List<String> words) {
		this.words = words;
		this.count = 0;
	}
	
	private synchronized String aux_getNextWord() {
		if(count < words.size())
			return words.get(count++);
		return null;
	}
	
	@Override
	public String getNextWord() {
		return aux_getNextWord();
	}
	
	// Cuidado com possível race condition entre este método e o método getNextWord.
	@Override
	public void resetCount() {
		this.count = 0;
	}
}
