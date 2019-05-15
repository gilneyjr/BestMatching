package main.reactive;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.SubmissionPublisher;

public class Producer implements Runnable {
	private SubmissionPublisher<Similarity> publisher;
	private List<String> input;
	private List<String> dic;
	
	public Producer(SubmissionPublisher<Similarity> publisher) {
		this.publisher = publisher;
	}

	public void init(Path dicPath, Path inputPath) throws IOException {
		input = Files.readAllLines(dicPath);
		dic = Files.readAllLines(inputPath);
	}
	
	@Override
	public void run() {
		dic.stream()
			.flatMap(dicWord -> input.parallelStream().map(inputWord -> new Similarity(dicWord,inputWord)))
			.forEach(publisher::submit);
		publisher.close();
	}
}
