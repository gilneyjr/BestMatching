package test.jmh.semaphore;

import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import main.concurrent.BestMatchingConcurrent;
import main.concurrent.semaphore.CounterSemaphore;
import main.concurrent.semaphore.PairsSemaphore;
import main.concurrent.semaphore.StringsSemaphore;
import main.util.Strings;

@State(Scope.Benchmark)
public class StateSemaphore extends BestMatchingConcurrent {
	public Strings dic;
	public Strings input;
	
	@Setup
	public void init() {
		dic = new StringsSemaphore();
		input = new StringsSemaphore();
		read("data/dictionary.txt", dic, "data/input_words.txt", input);
	}
	
	public StateSemaphore() {
		super(StringsSemaphore.class, CounterSemaphore.class, PairsSemaphore.class);
	}
	
}
