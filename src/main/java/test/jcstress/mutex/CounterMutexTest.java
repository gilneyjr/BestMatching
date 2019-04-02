package test.jcstress.mutex;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Description;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.II_Result;

import main.concurrent.mutex.CounterMutex;

public class CounterMutexTest {
	@State
	public static class CounterMutexState extends CounterMutex {}
	
	@JCStressTest
	@Description("Stressing CounterMutex.")
	@Outcome(id="0, 1", expect=Expect.ACCEPTABLE, desc="get 0, 1")
	@Outcome(id="1, 0", expect=Expect.ACCEPTABLE, desc="get 1, 0")
	public static class StressCounterMutex {
		@Actor
		public void actor1(CounterMutexState cm, II_Result r) {
			r.r1 = cm.next();
		}
		
		@Actor
		public void actor2(CounterMutexState cm, II_Result r) {
			r.r2 = cm.next();
		}
	}
}
