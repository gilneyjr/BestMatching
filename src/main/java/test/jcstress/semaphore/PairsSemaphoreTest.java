package test.jcstress.semaphore;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Arbiter;
import org.openjdk.jcstress.annotations.Description;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.LL_Result;

import main.concurrent.semaphore.PairsSemaphore;
import main.util.Pair;

public class PairsSemaphoreTest {
	@State
	public static class PairsSemaphoreState extends PairsSemaphore {
		public PairsSemaphoreState() {
			add(new Pair(3,"a"));
			add(new Pair(1,"a"));
		}
	}
	
	@JCStressTest
	@Description("Stressing PairsSemaphore.")
	@Outcome(id="(1,a), (1,a) (2,a) (3,a) ", expect=Expect.ACCEPTABLE)
	@Outcome(id="(2,a), (1,a) (2,a) (3,a) ", expect=Expect.ACCEPTABLE)
	@Outcome(id="(1,a), (1,a) (3,a) (2,a) ", expect=Expect.ACCEPTABLE)
	@Outcome(id="(3,a), (1,a) (3,a) (2,a) ", expect=Expect.ACCEPTABLE)
	public static class StressPairsSemaphore {
		
		@Actor
		public void actor1(PairsSemaphoreState s) {
			s.add(new Pair(2,"a"));
		}
		
		@Actor
		public void actor2(PairsSemaphoreState s, LL_Result r) {
			Pair p = s.get(1);
			r.r1 = "(" + p.getFirst() + "," +p.getSecond() + ")"; 
		}
		
		@Actor
		public void actor3(PairsSemaphoreState s) {
			s.sort(); 
		}
		
		@Arbiter
		public void arbiter1(PairsSemaphoreState s, LL_Result r) {
			String str = "";
			for(int i = 0; i < s.size(); i++)
				str += "(" + s.get(i).getFirst() + "," + s.get(i).getSecond() + ") ";
			r.r2 = str;
		}
	}
}
