package main.concurrent.mutex;

import java.util.ArrayList;
import java.util.List;

import main.util.Strings;

public class StringsMutex implements Strings {
	private final List<String> list;
	public StringsMutex() {
		list = new ArrayList<>();
	}
	
	@Override
	public void add(String e) {
		synchronized(this) {
			list.add(e);
		}
	}

	@Override
	public String get(int index) {
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
