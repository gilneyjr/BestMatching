package main.util;

public abstract class BestMatching {
	private final Class<? extends Strings> stringsClass;
	public BestMatching(Class<? extends Strings> stringsClass) {
		this.stringsClass = stringsClass;
	}
	
	public void calculate(
			String dicPath,
			String inputPath)
	{
		try {
			Strings dic = stringsClass.newInstance();
			Strings input = stringsClass.newInstance();
			
			read(dicPath, dic, inputPath, input);
			calculateAndWrite(dic, input);
		} catch (InstantiationException | IllegalAccessException e) {
			System.err.println("Invalid Strings subclass: " + stringsClass.getName());
		}
	}
	
	public abstract void read(
			String dicPath,
			Strings dic,
			String inputPath, 
			Strings input
	);
	
	public abstract void calculateAndWrite(
			Strings dic,
			Strings input
	);
}
