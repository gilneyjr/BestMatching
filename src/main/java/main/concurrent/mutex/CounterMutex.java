package main.concurrent.mutex;

import main.util.Counter;

public class CounterMutex implements Counter {
	private Integer count;
	
	public CounterMutex() {
		count = 0;
	}
	
	@Override
	public int next() {
		synchronized (this) {
			return count++;
		}
	}
}
