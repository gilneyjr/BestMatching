package main.reactive;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

public class Consumer implements Subscriber<Similarity> {
	private Subscription subscription;
	private Map<String, List<Similarity>> map = new HashMap<>();
	private String outputDir;
	
	public Consumer(String outputDir) {
		this.outputDir = outputDir;
	}
	
	@Override
	public void onSubscribe(Subscription subscription) {
		this.subscription = subscription;
		this.subscription.request(1);
	}

	@Override
	public void onNext(Similarity item) {
		List<Similarity> list;
		if((list = map.get(item.getInputWord())) != null) {
			list.add(item);
		} else {
			list = new ArrayList<>();
			list.add(item);
			map.put(item.getInputWord(), list);
		}
		this.subscription.request(1);
	}

	@Override
	public void onError(Throwable throwable) {
		throwable.printStackTrace();
	}

	@Override
	public void onComplete() {
		map.forEach((key, value) -> {
			try(BufferedWriter bw = new BufferedWriter(new FileWriter(new File(outputDir+key+".csv")))) {
				String str = value.stream()
					.map(sim -> sim.getDicWord() + ";" + sim.getSimilarity()+"\n")
					.reduce("Words;Similarity to \""+key+"\"\n", (str1, str2) -> str1+str2);
				bw.write(str);
			} catch (IOException e) {
				
			}
		});
	}
}
