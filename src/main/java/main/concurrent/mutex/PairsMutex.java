package main.concurrent.mutex;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import main.util.Pair;
import main.util.Pairs;

public class PairsMutex implements Pairs {
	private final List<Pair> list;
	
	public PairsMutex() {
		list = new ArrayList<>();
	}

	@Override
	public void sort() {
		synchronized (this) {
			list.sort(new Comparator<Pair>() {
				@Override
				public int compare(Pair p1, Pair p2) {
					if(p1.getFirst() == p2.getFirst())
						return p1.getSecond().compareTo(p2.getSecond());
					return p1.getFirst() - p2.getFirst();
				}
			});
		}
	}

	@Override
	public void add(Pair e) {
		synchronized (this) {
			list.add(e);
		}
	}

	@Override
	public Pair get(int index) {
		synchronized (this) {
			return list.get(index);
		}
	}

	@Override
	public int size() {
		synchronized (this) {
			return list.size();
		}
	}
}
