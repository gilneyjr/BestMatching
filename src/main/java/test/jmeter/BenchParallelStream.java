package test.jmeter;

import java.io.Serializable;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import main.stream.BestMatchingStream;

public class BenchParallelStream extends AbstractJavaSamplerClient implements Serializable {
	private static final long serialVersionUID = 4151854298674996428L;

	@Override
	public SampleResult runTest(JavaSamplerContext context) {
		SampleResult result = new SampleResult();
		result.setSampleLabel("Parallel Stream Algorithm Benchmark");
		String dictionary = context.getParameter("dictionary");
		String input_words = context.getParameter("input_words");
		String output = context.getParameter("output");
		
		BestMatchingStream bm = new BestMatchingStream();
		
		result.sampleStart();
		bm.calculate(dictionary, input_words, output);
		result.sampleEnd();
		
		result.setResponseCode("200");
		result.setResponseMessage("OK");
		result.setSuccessful(true);
		
		return result;
	}
	
	@Override
	public Arguments getDefaultParameters() {
		Arguments defaultParameters = new Arguments();
		defaultParameters.addArgument("dictionary",  "/home/junior/Documentos/GitHub/BestMatching/data/dictionary.txt");
		defaultParameters.addArgument("input_words", "/home/junior/Documentos/GitHub/BestMatching/data/input_words.txt");
		defaultParameters.addArgument("output", "/home/junior/Documentos/Test/");
		return defaultParameters;
	}
	
}
