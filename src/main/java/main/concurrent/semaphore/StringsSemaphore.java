package main.concurrent.semaphore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import main.util.Strings;

public class StringsSemaphore implements Strings {
	private Semaphore semaphore;
	private final List<String> list;
	
	public StringsSemaphore() {
		this.semaphore = new Semaphore(1);
		this.list = new ArrayList<>();
	}
	
	@Override
	public void add(String e) {
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
	public String get(int index) {
		String str = null;
		try {
			semaphore.acquire();
			str = list.get(index);
		} catch (InterruptedException e1) {
			System.err.println("Thread \"" + Thread.currentThread().getName() + "\" was interrupted!");
		} finally {
			semaphore.release();
		}
		return str;
	}

	@Override
	public int size() {
		int i = -1;
		try {
			semaphore.acquire();
			i = list.size();
		} catch (InterruptedException e1) {
			System.err.println("Thread \"" + Thread.currentThread().getName() + "\" was interrupted!");
		} finally {
			semaphore.release();
		}
		return i;
	}

}
