package main.forkjoinApproach;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ForkJoinPool;

import main.forkjoinApproach.forkjoin.CalculateAndWriteForkJoin;
import main.sequential.util.StringsSequential;
import main.util.BestMatching;
import main.util.Pairs;
import main.util.Strings;

public class BestMatchingForkJoin extends BestMatching {
	private Class<? extends Pairs> pairsClass;
	
	public BestMatchingForkJoin(Class<? extends Pairs> pairsClass) {
		super(StringsSequential.class);
		this.pairsClass = pairsClass;
	}
	
	private void read(String path, Strings content) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			
			String line;
            while((line = br.readLine()) != null) {
                content.add(line);
            }
            br.close();
			
		} catch (FileNotFoundException e) {
			System.err.println("Could not read \""+path+"\" file.");
		} catch (IOException e) {
			System.err.println("Error while reading \""+path+"\" file.");
		}
	}

	@Override
	public void read(String dicPath, Strings dic, String inputPath, Strings input) {
		read(dicPath, dic);
		read(inputPath, input);
	}

	@Override
	public void calculateAndWrite(Strings dic, Strings input) {
		ForkJoinPool pool = new ForkJoinPool();
		CalculateAndWriteForkJoin task = new CalculateAndWriteForkJoin(dic, input, pairsClass);
		pool.invoke(task);
	}
}
