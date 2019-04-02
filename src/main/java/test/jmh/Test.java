package test.jmh;

import java.io.IOException;

import org.openjdk.jmh.Main;
import org.openjdk.jmh.runner.RunnerException;

public class Test {
	public static final int FORK_VALUE = 3;
	public static final int FORK_WARMUP = 2;
	public static final int WARMUP_ITERATIONS = 5;
	public static final int MEASUREMENT_ITERATIONS = 10;
	
	public static void main(String[] args) throws RunnerException, IOException {
		Main.main(args);
	}
}
