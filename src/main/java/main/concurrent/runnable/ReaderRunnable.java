package main.concurrent.runnable;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import main.util.Strings;

public class ReaderRunnable implements Runnable {
	private String path;
	private Strings content;
	
	public ReaderRunnable(String path, Strings content) {
		this.path = path;
		this.content = content;
	}

	@Override
	public void run() {
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
	}
}
