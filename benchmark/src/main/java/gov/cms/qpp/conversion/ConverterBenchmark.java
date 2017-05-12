package gov.cms.qpp.conversion;

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
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

/**
 * Performance test harness.
 */
@Warmup(iterations = 0, time = 1, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 1, time = 1, timeUnit = TimeUnit.MILLISECONDS)
@Fork(value = 1, jvmArgsPrepend = {"-Xmx50m", "-Xms50m"})
@Threads(1)
public class ConverterBenchmark {
	private static String filePath = "src/main/resources/qrda-files/valid-QRDA-III.xml";
//	private static String output = "valid-QRDA-III.qpp.json";
	//private static String filePath = "src/main/resources/qrda-files/gpro-biggest.xml";
	private static String output = "gpro-biggest.qpp.json";
	private static String schemaPath = "src/main/resources/cda/infrastructure/cda/CDA_SDTC.xsd";

	/**
	 * State management for tests.
	 */
	@State(Scope.Thread)
	public static class Cleaner {
		@TearDown(Level.Trial)
		public void doTearDown() throws IOException {
			Path fileToDeletePath = Paths.get(output);
			Files.delete(fileToDeletePath);
		}
	}

	/**
	 * Benchmark qrda file conversion.
	 *
	 * @param cleaner State management for conversion runs, ensures that output files are deleted.
	 */
//	@Benchmark
//	@BenchmarkMode({Mode.Throughput, Mode.AverageTime})
//	public void benchmarkMain(Cleaner cleaner) {
//		ConversionEntry.main(filePath);
//	}

	@Benchmark
	@BenchmarkMode({Mode.Throughput, Mode.AverageTime})
	public void benchmarkStreamingParser() throws IOException, ParserConfigurationException, SAXException {
		ThreadMXBean mxBean = ManagementFactory.getThreadMXBean();
		long time = mxBean.getCurrentThreadCpuTime();

		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser parser = spf.newSAXParser();
		Path path = Paths.get(filePath);
		InputStream historical = new BufferedInputStream(Files.newInputStream(path));

		parser.parse(historical, new ConversionHandler());
		//System.out.println("After: " + (mxBean.getCurrentThreadCpuTime() - time));
	}

	@Benchmark
	@BenchmarkMode({Mode.Throughput, Mode.AverageTime})
	public void benchmarkValidatingStreamingParser() throws IOException, ParserConfigurationException, SAXException {
		ThreadMXBean mxBean = ManagementFactory.getThreadMXBean();
		long time = mxBean.getCurrentThreadCpuTime();

		Source xmlFile = new StreamSource(new File(filePath));
		SchemaFactory schemaFactory = SchemaFactory
				.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(new File(schemaPath));

		SAXParserFactory spf = SAXParserFactory.newInstance();
		spf.setNamespaceAware(true);
		spf.setValidating(false);
		spf.setSchema(schema);
		SAXParser parser = spf.newSAXParser();
		Path path = Paths.get(filePath);
		InputStream historical = new BufferedInputStream(Files.newInputStream(path));

		parser.parse(historical, new ConversionHandler());
		//System.out.println("After: " + (mxBean.getCurrentThreadCpuTime() - time));
	}

	@Benchmark
	@BenchmarkMode({Mode.Throughput, Mode.AverageTime})
	public void benchmarkValidation() throws IOException, ParserConfigurationException, SAXException {
		Source xmlFile = new StreamSource(new File(filePath));
		SchemaFactory schemaFactory = SchemaFactory
				.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(new File(schemaPath));
		Validator validator = schema.newValidator();
		validator.validate(xmlFile);
	}

	@Benchmark
	@BenchmarkMode({Mode.Throughput, Mode.AverageTime})
	public void benchmarkPullParse() throws FileNotFoundException, XMLStreamException {
		Path path = Paths.get(filePath);
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader r =
				factory.createXMLStreamReader(
						new BufferedInputStream(
								new FileInputStream(path.toFile())));
		while(r.hasNext()) {
			int eventType = r.next();
			if (eventType == XMLStreamReader.START_ELEMENT) {
				String name = r.getLocalName();
				if ("templateId".equals(name)) {
					String templateId = r.getAttributeValue("", "root");
					//System.out.println("template Id: " + templateId);
				}
			}
		}
	}


}
