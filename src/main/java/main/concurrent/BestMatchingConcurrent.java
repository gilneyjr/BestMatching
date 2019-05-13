package main.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
		ExecutorService exec = Executors.newFixedThreadPool(2);
		
		Runnable r1 = new ReaderRunnable(dicPath, dic);
		Runnable r2 = new ReaderRunnable(inputPath, input);
		
		exec.execute(r1);
		exec.execute(r2);
		
		exec.shutdown();
		try {
			exec.awaitTermination(24, TimeUnit.HOURS);
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.err.println("Falha em sincronizar executor no método read!");
			System.exit(-1);
		}
	}

	@Override
	public void calculateAndWrite(Strings dic, Strings input) {
		ExecutorService exec = Executors.newFixedThreadPool(4);
		
		try {
			Counter count = countClass.newInstance();
			for(int i = 0; i < 2; i++) {
				Runnable r = new CalculateAndWriteRunnable(dic, input, count, pairsClass);
				exec.execute(r);
			}
			
			exec.shutdown();
			try {
				exec.awaitTermination(24, TimeUnit.HOURS);
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.err.println("Falha em sincronizar executor no método calculateAndWrite!");
				System.exit(-1);
			}
			
		} catch (InstantiationException | IllegalAccessException e1) {
			System.err.println("Invalid class: " + countClass.getName());
			System.exit(-1);
		}
	}
}
