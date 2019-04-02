package main.sequential.util;

import java.util.ArrayList;
import java.util.List;

import main.util.Strings;

public class StringsSequential implements Strings {
	private final List<String> list;
	
	public StringsSequential() {
		list = new ArrayList<>();
	}
	
	@Override
	public void add(String e) {
		list.add(e);
	}

	@Override
	public String get(int index) {
		return list.get(index);
	}

	@Override
	public int size() {
		return list.size();
	}
}
