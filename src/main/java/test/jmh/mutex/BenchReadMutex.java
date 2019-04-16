package test.jmh.mutex;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;

import main.concurrent.mutex.StringsMutex;
import test.jmh.Test;

public class BenchReadMutex {
	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	@Fork(value=Test.FORK_VALUE, warmups=Test.FORK_WARMUP)
	@Warmup(iterations=Test.WARMUP_ITERATIONS)
	@Measurement(iterations=Test.MEASUREMENT_ITERATIONS)
	@OutputTimeUnit(TimeUnit.MILLISECONDS)
	public void test(StateMutex ms) {
		StringsMutex dic = new StringsMutex();
		StringsMutex input = new StringsMutex();
		ms.read("data/dictionary.txt", dic, "data/input_words.txt", input);
	}
}
