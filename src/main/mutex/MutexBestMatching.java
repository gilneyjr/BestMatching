package main.mutex;

import java.util.ArrayList;
import java.util.Vector;

import main.bestmatching.BestMatching;
import main.bestmatching.dictionary.Dictionary;
import main.bestmatching.dictionary.Word;
import main.bestmatching.distance.SimilarityDistance;

public class MutexBestMatching extends BestMatching {
	private static int N_THREADS = 10;
	private Dictionary dictionary;
	private SimilarityDistance sd;
	
	public MutexBestMatching(Dictionary dictionary, SimilarityDistance sd) {
		this.dictionary = dictionary;
		this.sd = sd;
	}

	@Override
	protected void calculateDistance(Vector<Word> similarWords, String word) {
		// Create N_THREADS threads and execute them
		ArrayList<Thread> thrs = new ArrayList<>();
		for(int i = 0; i < N_THREADS; i++) {
			Thread t = new Thread(new MutexRunnable(dictionary, similarWords, sd, word));
			thrs.add(t);
			t.start();
		}
		
		// Join
		try {
			for(Thread t : thrs)
				t.join();
		} catch (InterruptedException e) {}
	}

}
