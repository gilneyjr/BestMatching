package main.concurrent.forkjoin;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Callable;

import main.util.Strings;

public class ReaderForkJoin implements Callable<Void> {
	private String path;
	private Strings content;
	
	public ReaderForkJoin(String path, Strings content) {
		this.path = path;
		this.content = content;
	}

	@Override
	public Void call() throws Exception {
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
		return null;
	}
}
