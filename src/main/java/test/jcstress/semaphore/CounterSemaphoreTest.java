package test.jcstress.semaphore;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Description;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.II_Result;

import main.concurrent.semaphore.CounterSemaphore;

public class CounterSemaphoreTest {
	@State
	public static class CounterSemaphoreState extends CounterSemaphore {}
	
	@JCStressTest
	@Description("Stressing CounterSemaphore.")
	@Outcome(id="0, 1", expect=Expect.ACCEPTABLE, desc="get 0, 1")
	@Outcome(id="1, 0", expect=Expect.ACCEPTABLE, desc="get 1, 0")
	public static class StressCounterSemaphore {
		@Actor
		public void actor1(CounterSemaphoreState cm, II_Result r) {
			r.r1 = cm.next();
		}
		
		@Actor
		public void actor2(CounterSemaphoreState cm, II_Result r) {
			r.r2 = cm.next();
		}
	}
}
