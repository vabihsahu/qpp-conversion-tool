package gov.cms.qpp.conversion;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl;
import com.sun.org.apache.xerces.internal.jaxp.validation.XMLSchemaFactory;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

/**
 * Performance test harness.
 */
@Warmup(iterations = 0, time = 1, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 1, time = 1, timeUnit = TimeUnit.MILLISECONDS)
@Fork(value = 15, jvmArgsPrepend = {"-Xmx50m", "-Xms50m"})
@Threads(1)
public class ConverterBenchmark {

	/**
	 * State management for tests.
	 */
//	@State(Scope.Thread)
//	public static class Cleaner {
//		@TearDown(Level.Trial)
//		public void doTearDown() throws IOException {
//			Path fileToDeletePath = Paths.get("valid-QRDA-III.qpp.json");
////			Path fileToDeletePath = Paths.get("gpro-biggest.qpp.json");
//			Files.delete(fileToDeletePath);
//		}
//	}

	/**
	 * Benchmark qrda file conversion.
	 *
	 * @param cleaner State management for conversion runs, ensures that output files are deleted.
	 */
//	@Benchmark
//	@BenchmarkMode({Mode.Throughput, Mode.AverageTime})
//	public void benchmarkMain(Cleaner cleaner) {
//		ConversionEntry.main("src/main/resources/qrda-files/valid-QRDA-III.xml");
//		//ConversionEntry.main("src/main/resources/qrda-files/gpro-biggest.xml", "-d");
//	}

	@Benchmark
	@BenchmarkMode({Mode.Throughput, Mode.AverageTime})
	public void benchmarkValidate() throws IOException, ParserConfigurationException, SAXException {
		ThreadMXBean mxBean = ManagementFactory.getThreadMXBean();
		long time = mxBean.getCurrentThreadCpuTime();
		String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;


		//SchemaFactory factory = SchemaFactory.newInstance(language);
		SchemaFactory factory = new XMLSchemaFactory();
		//System.out.println("After SchemaFactory.newInstance: " + (mxBean.getCurrentThreadCpuTime() - time));

		//Schema schema = factory.newSchema(new File("src/main/resources/qrda-conversion.xsd"));
		Schema schema = factory.newSchema(new File("src/main/resources/cda/infrastructure/cda/CDA_SDTC.xsd"));

		//time = mxBean.getCurrentThreadCpuTime();
		//SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParserFactory spf = new SAXParserFactoryImpl();
		//System.out.println("After SAXParserFactory.newInstance: " + (mxBean.getCurrentThreadCpuTime() - time));

		spf.setNamespaceAware(true);
		spf.setValidating(false);
		spf.setSchema(schema);

		SAXParser parser = spf.newSAXParser();

		Path path = Paths.get("src/main/resources/qrda-files/valid-QRDA-III.xml");
		InputStream historical = new BufferedInputStream(Files.newInputStream(path));

		parser.parse(historical, new ConversionHandler());
		System.out.println("After: " + (mxBean.getCurrentThreadCpuTime() - time));
	}





}
