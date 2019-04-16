package test.jmh.sequential;

import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import main.sequential.BestMatchingSequential;
import main.sequential.util.StringsSequential;
import main.util.Strings;

@State(Scope.Benchmark)
public class StateSequential extends BestMatchingSequential {
	public Strings dic;
	public Strings input;
	
	@Setup
	public void init() {
		dic = new StringsSequential();
		input = new StringsSequential();
		read("data/dictionary.txt", dic, "data/input_words.txt", input);
	}
	
	public StateSequential() {
		super(StringsSequential.class);
	}
	
}
