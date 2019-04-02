package main.concurrent.runnable;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import main.util.Counter;
import main.util.Pair;
import main.util.Pairs;
import main.util.Strings;

public class CalculateAndWriteRunnable implements Runnable {
	private Strings dic;
	private Strings input;
	private Counter count;
	private Class<? extends Pairs> pairsClass;
	
	public CalculateAndWriteRunnable(Strings dic, Strings input, Counter count, Class<? extends Pairs> pairsClass) {
		this.dic = dic;
		this.input = input;
		this.count = count;
		this.pairsClass = pairsClass;
	}
	
	@Override
	public void run() {
		int i_input_words;
		while((i_input_words = count.next()) < input.size()) {
			String input_word = input.get(i_input_words);
			try {
				List<Thread> thrs = new ArrayList<>();
				Pairs sim = pairsClass.newInstance();
				Counter dicCounter = count.getClass().newInstance();
				for(int j = 0; j < 4; j++) {
					Thread t = new Thread(new CalculateSimilarityRunnable(dic, sim, input_word, dicCounter));
					thrs.add(t);
					t.start();
				}
				
				try {
					for(Thread t : thrs)
						t.join();
				} catch (InterruptedException e) {
					System.err.println("Thread \"" + Thread.currentThread().getName() + "\" was interrupted!");
				}
				
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
	}
}
