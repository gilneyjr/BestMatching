package test.jmh.stream;

import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import main.stream.BestMatchingStream;

@State(Scope.Benchmark)
public class StateParallelStream extends BestMatchingStream {
	@Setup
	public void init() {
		read("data/dictionary.txt", "data/input_words.txt");
	}
}
