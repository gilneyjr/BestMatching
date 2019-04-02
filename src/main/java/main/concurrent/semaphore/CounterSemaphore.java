package main.concurrent.semaphore;

import java.util.concurrent.Semaphore;

import main.util.Counter;

public class CounterSemaphore implements Counter {
	private final Semaphore semaphore;
	private Integer count;
	
	public CounterSemaphore() {
		this.semaphore = new Semaphore(1);
		this.count = 0;
	}
	
	@Override
	public int next() {
		int _count = Integer.MAX_VALUE;
		try {
			semaphore.acquire();
			_count = count++;
		} catch (InterruptedException e) {
			System.err.println("Thread \"" + Thread.currentThread().getName() + "\" was interrupted!");
		} finally {
			semaphore.release();
		}
		return _count;
	}

}
