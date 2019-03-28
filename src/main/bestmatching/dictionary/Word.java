package main.bestmatching.dictionary;

public class Word {
	private String content;
	private int similarity;
	public Word(String content, int similarity) {
		this.content = content;
		this.similarity = similarity;
	}
	public Word(String content) {
		this.content = content;
		this.similarity = Integer.MAX_VALUE;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getSimilarity() {
		return similarity;
	}
	public void setSimilarity(int similarity) {
		this.similarity = similarity;
	}
}