package main.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

import main.concurrent.callable.ReaderCallable;
import main.forkjoinApproach.forkjoin.CalculateAndWriteForkJoin;
import main.util.BestMatching;
import main.util.Pairs;
import main.util.Strings;

public class BestMatchingConcurrent extends BestMatching {
	private Class<? extends Pairs> pairsClass;
	
	public BestMatchingConcurrent(
			Class<? extends Strings> stringsClass,
			Class<? extends Pairs> pairsClass) {
		super(stringsClass);
		this.pairsClass = pairsClass;
	}

	@Override
	public void read(String dicPath, Strings dic, String inputPath, Strings input) {
		ExecutorService exec = Executors.newFixedThreadPool(2);
		
		Callable<Void> c1 = new ReaderCallable(dicPath, dic);
		Callable<Void> c2 = new ReaderCallable(inputPath, input);
		
		exec.submit(c1);
		exec.submit(c2);
		
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
		ForkJoinPool pool = new ForkJoinPool();
		CalculateAndWriteForkJoin task = new CalculateAndWriteForkJoin(dic, input, pairsClass);
		pool.invoke(task);
	}
}
