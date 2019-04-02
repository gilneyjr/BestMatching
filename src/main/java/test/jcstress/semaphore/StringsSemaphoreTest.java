package test.jcstress.semaphore;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Arbiter;
import org.openjdk.jcstress.annotations.Description;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.LL_Result;

import main.concurrent.semaphore.StringsSemaphore;

public class StringsSemaphoreTest {
	@State
	public static class StringsSemaphoreState extends StringsSemaphore {
		public StringsSemaphoreState() {
			add("c");
			add("a");
		}
	}
	
	@JCStressTest
	@Description("Stressing StringsSemaphore.")
	@Outcome(id="a, c a b ", expect=Expect.ACCEPTABLE)
	public static class StressStringsSemaphore {
		@Actor
		public void actor1(StringsSemaphoreState s) {
			s.add("b");
		}
		
		@Actor
		public void actor2(StringsSemaphoreState s, LL_Result r) {
			r.r1 = s.get(1);
		}
		
		@Arbiter
		public void arbiter1(StringsSemaphoreState s, LL_Result r) {
			String str = "";
			for(int i = 0; i < s.size(); i++)
				str +=  s.get(i) + " ";
			r.r2 = str;
		}
	}
}
