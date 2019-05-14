package main.forkjoinApproach.forkjoin;

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import main.util.Pair;
import main.util.Pairs;
import main.util.Strings;

public class CalculateAndWriteForkJoin extends RecursiveAction {
	private static final long serialVersionUID = -8538233646697951020L;
	private int lo;
	private int hi;
	private Strings dic;
	private Strings input;
	private Class<? extends Pairs> pairsClass;
	
	private CalculateAndWriteForkJoin(int lo, int hi, Strings dic, Strings input, Class<? extends Pairs> pairsClass) {
		this.dic = dic;
		this.input = input;
		this.pairsClass = pairsClass;
		this.lo = lo;
		this.hi = hi;
	}
	
	public CalculateAndWriteForkJoin(Strings dic, Strings input, Class<? extends Pairs> pairsClass) {
		this.dic = dic;
		this.input = input;
		this.pairsClass = pairsClass;
		this.lo = 0;
		this.hi = input.size()-1;
	}
	
	private void computation(int index) {
		String input_word = input.get(index);
		try {
			Pairs sim = pairsClass.newInstance();
			
			ForkJoinPool pool = new ForkJoinPool();
			CalculateSimilarityForkJoin task = new CalculateSimilarityForkJoin(0, dic.size()-1, dic, sim, input_word);
			pool.invoke(task);
			
			sim.sort();
			
			
			FileWriter fw = new FileWriter("data/output/" + input_word + ".csv");
			fw.write("Words;Similarity to \""+input_word+"\"\n");
			
			for(int i = 0; i < sim.size(); i++) {
				Pair p = sim.get(i);
				String str = p.getSecond() + ";" + p.getFirst().toString() + "\n";
				fw.write(str);
			}
				
			fw.close();
			
		} catch (InstantiationException | IllegalAccessException e) {
			System.err.println("Invalid class: " + pairsClass.getName());
			System.exit(-1);
		} catch (IOException e) {
			System.err.println("Could not write \"data/output/" + input_word + ".csv\".");
		}
	}

	@Override
	protected void compute() {
		if (lo == hi) { 
			computation(lo);
		}
		else {
			int mid = (lo+hi)/2;
			CalculateAndWriteForkJoin firstAction = new CalculateAndWriteForkJoin(lo, mid, dic, input, pairsClass);
			CalculateAndWriteForkJoin secondAction = new CalculateAndWriteForkJoin(mid+1, hi, dic, input, pairsClass);
			invokeAll(firstAction, secondAction);
		}
	}
}