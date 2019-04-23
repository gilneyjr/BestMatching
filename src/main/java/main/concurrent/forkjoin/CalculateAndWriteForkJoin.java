package main.concurrent.forkjoin;

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;

import main.util.Counter;
import main.util.Pair;
import main.util.Pairs;
import main.util.Strings;

public class CalculateAndWriteForkJoin implements Callable<Void> {

	private Strings dic;
	private Strings input;
	private Counter count;
	private Class<? extends Pairs> pairsClass;
	
	public CalculateAndWriteForkJoin(Strings dic, Strings input, Counter count, Class<? extends Pairs> pairsClass) {
		this.dic = dic;
		this.input = input;
		this.count = count;
		this.pairsClass = pairsClass;
	}
	
	@Override
	public Void call() throws Exception {
		int i_input_words;
		while((i_input_words = count.next()) < input.size()) {
			String input_word = input.get(i_input_words);
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
		return null;
	}
}