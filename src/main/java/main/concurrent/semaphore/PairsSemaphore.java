package main.concurrent.semaphore;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Semaphore;

import main.util.Pair;
import main.util.Pairs;

public class PairsSemaphore implements Pairs {
	private final Semaphore semaphore;
	private final List<Pair> list;
	public PairsSemaphore() {
		this.list = new ArrayList<>();
		this.semaphore = new Semaphore(1);
	}
	
	@Override
	public void sort() {
		try {
			semaphore.acquire();
			list.sort(new Comparator<Pair>() {
				@Override
				public int compare(Pair p1, Pair p2) {
					if(p1.getFirst() == p2.getFirst())
						return p1.getSecond().compareTo(p2.getSecond());
					return p1.getFirst() - p2.getFirst();
				}
			});
		} catch (InterruptedException e) {
			System.err.println("Thread \"" + Thread.currentThread().getName() + "\" was interrupted!");
		} finally {
			semaphore.release();
		}
	}

	@Override
	public void add(Pair e) {
		try {
			semaphore.acquire();
			list.add(e);
		} catch (InterruptedException e1) {
			System.err.println("Thread \"" + Thread.currentThread().getName() + "\" was interrupted!");
		} finally {
			semaphore.release();
		}
	}

	@Override
	public Pair get(int index) {
		Pair p = null;
		try {
			semaphore.acquire();
			p = list.get(index);
		} catch (InterruptedException e1) {
			System.err.println("Thread \"" + Thread.currentThread().getName() + "\" was interrupted!");
		} finally {
			semaphore.release();
		}
		return p;
	}

	@Override
	public int size() {
		int _size = -1;
		try {
			semaphore.acquire();
			_size = list.size();
		} catch (InterruptedException e1) {
			System.err.println("Thread \"" + Thread.currentThread().getName() + "\" was interrupted!");
		} finally {
			semaphore.release();
		}
		return _size;
	}

}
