package test.jmeter;

import java.io.Serializable;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import main.sequential.BestMatchingSequential;
import main.sequential.util.StringsSequential;
import main.util.BestMatching;

public class BenchSequential extends AbstractJavaSamplerClient implements Serializable {
	
	private static final long serialVersionUID = 2365322079993712264L;

	@Override
	public SampleResult runTest(JavaSamplerContext context) {
		SampleResult result = new SampleResult();
		result.setSampleLabel("Sequential Algorithm Benchmark");
		String dictionary = context.getParameter("dictionary");
		String input_words = context.getParameter("input_words");
		
		result.sampleStart();
		
		BestMatching bm = new BestMatchingSequential(StringsSequential.class);
		bm.calculate(dictionary, input_words);
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
		return defaultParameters;
	}
}
