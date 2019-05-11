package main.concurrent;

import java.util.ArrayList;
import java.util.List;

import main.concurrent.runnable.CalculateAndWriteRunnable;
import main.concurrent.runnable.ReaderRunnable;
import main.util.BestMatching;
import main.util.Counter;
import main.util.Pairs;
import main.util.Strings;

public class BestMatchingConcurrent extends BestMatching {
	private Class<? extends Counter> countClass;
	private Class<? extends Pairs> pairsClass;
	
	public BestMatchingConcurrent(
			Class<? extends Strings> stringsClass,
			Class<? extends Counter> countClass,
			Class<? extends Pairs> pairsClass) {
		super(stringsClass);
		this.countClass = countClass;
		this.pairsClass = pairsClass;
	}

	@Override
	public void read(String dicPath, Strings dic, String inputPath, Strings input) {
		Thread t1 = new Thread(new ReaderRunnable(dicPath, dic));
		Thread t2 = new Thread(new ReaderRunnable(inputPath, input));
		
		t1.start();
		t2.start();
		
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {}
	}

	@Override
	public void calculateAndWrite(Strings dic, Strings input) {
		List<Thread> thrs = new ArrayList<>();
		try {
			Counter count = countClass.newInstance();
			for(int i = 0; i < 2; i++) {
				Thread t = new Thread(new CalculateAndWriteRunnable(dic, input, count, pairsClass));
				thrs.add(t);
				t.start();
			}
			
			try {
				for(Thread t : thrs)
					t.join();
			} catch (InterruptedException e) {
				System.err.println("Thread \"" + Thread.currentThread().getName() + "\" was interrupted!");
			}
			
		} catch (InstantiationException | IllegalAccessException e1) {
			System.err.println("Invalid class: " + countClass.getName());
			System.exit(-1);
		}
	}

}
