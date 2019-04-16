package test.jmeter;

import java.io.Serializable;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import main.concurrent.BestMatchingConcurrent;
import main.concurrent.mutex.CounterMutex;
import main.concurrent.mutex.PairsMutex;
import main.concurrent.mutex.StringsMutex;
import main.util.BestMatching;

public class BestMatchingMutex extends AbstractJavaSamplerClient implements Serializable {	
	private static final long serialVersionUID = -6045110927370080515L;

	@Override
	public SampleResult runTest(JavaSamplerContext context) {
		SampleResult result = new SampleResult();
		result.setSampleLabel("Mutex Algorithm Benchmark");
		String dictionary = context.getParameter("dictionary");
		String input_words = context.getParameter("input_words");
		
		result.sampleStart();
		
		BestMatching bm = new BestMatchingConcurrent(StringsMutex.class, CounterMutex.class, PairsMutex.class);
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