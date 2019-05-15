package main.reactive;

public class Similarity {
	private String dicWord;
	private String inputWord;
	private Integer similarity;
	
	public Similarity(String dicWord, String inputWord, Integer similarity) {
		this.dicWord = dicWord;
		this.inputWord = inputWord;
		this.similarity = similarity;
	}
	public Similarity(String dicWord, String inputWord) {
		this.dicWord = dicWord;
		this.inputWord = inputWord;
		this.similarity = -1;
	}
	
	public Integer getSimilarity() {
		return similarity;
	}
	public void setSimilarity(Integer similarity) {
		this.similarity = similarity;
	}
	public String getDicWord() {
		return dicWord;
	}
	public String getInputWord() {
		return inputWord;
	}
}
