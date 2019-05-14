package test.jmh.mutex;

import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import main.concurrent.mutex.PairsMutex;
import main.concurrent.mutex.StringsMutex;
import main.forkjoinApproach.BestMatchingForkJoin;
import main.util.Strings;

@State(Scope.Benchmark)
public class StateMutex extends BestMatchingForkJoin {
	public Strings dic;
	public Strings input;
	
	public StateMutex() {
		super(PairsMutex.class);
	}
	
	@Setup
	public void init() {
		dic = new StringsMutex();
		input = new StringsMutex();
		read("data/dictionary.txt", dic, "data/input_words.txt", input);
	}
}
