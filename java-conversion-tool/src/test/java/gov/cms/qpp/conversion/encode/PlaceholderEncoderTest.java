package gov.cms.qpp.conversion.encode;

import com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl;
import com.sun.org.apache.xerces.internal.jaxp.validation.XMLSchemaFactory;
import gov.cms.qpp.conversion.encode.helper.RegistryHelper;
import gov.cms.qpp.conversion.model.Node;
import gov.cms.qpp.conversion.model.Registry;
import gov.cms.qpp.conversion.model.TemplateId;
import gov.cms.qpp.conversion.model.error.ValidationError;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.xml.XMLConstants;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.SchemaFactory;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests the Placeholder adds coverage for CircleCI
 */
public class PlaceholderEncoderTest extends PlaceholderEncoder {


//	@Test
//	public void internalEncodeMissingEncoderTest() throws Exception {
//
//		Registry<String, JsonOutputEncoder> invalidRegistry =
//				RegistryHelper.makeInvalidRegistry(PlaceholderEncoder.class.getName());
//		Registry<String, JsonOutputEncoder> validRegistry = QppOutputEncoder.ENCODERS;
//
//		Node root = new Node(TemplateId.DEFAULT.getTemplateId());
//		Node placeHolderNode = new Node(root, TemplateId.PLACEHOLDER.getTemplateId());
//		root.addChildNode(placeHolderNode);
//		JsonWrapper testJsonWrapper = new JsonWrapper();
//		PlaceholderEncoder placeHolderEncoder = new PlaceholderEncoder();
//
//		RegistryHelper.setEncoderRegistry(invalidRegistry); //Set Registry with missing class
//
//		placeHolderEncoder.internalEncode(testJsonWrapper, root);
//		List<ValidationError> errors = placeHolderEncoder.getValidationErrors();
//		assertThat("Expecting Encode Exception", errors.size(), is(1));
//
//		RegistryHelper.setEncoderRegistry(validRegistry); //Restore Registry
//	}

	@Test
	public void testTest() {
		String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
		ThreadMXBean mxBean = ManagementFactory.getThreadMXBean();

		long time = mxBean.getCurrentThreadCpuTime();
//		System.out.println("Before SchemaFactory.newInstance: " + time);

		SchemaFactory factory = SchemaFactory.newInstance(language);
		System.out.println("After general: " + (mxBean.getCurrentThreadCpuTime() - time));
		time = mxBean.getCurrentThreadCpuTime();
		factory = XMLSchemaFactory.newInstance(language);
		System.out.println("After specific: " + (mxBean.getCurrentThreadCpuTime() - time));

		time = mxBean.getCurrentThreadCpuTime();
		SAXParserFactory spf = SAXParserFactory.newInstance();
		System.out.println("After general: " + (mxBean.getCurrentThreadCpuTime() - time));

		time = mxBean.getCurrentThreadCpuTime();
		SAXParserFactoryImpl.newInstance();
		System.out.println("After specific: " + (mxBean.getCurrentThreadCpuTime() - time));
		time = mxBean.getCurrentThreadCpuTime();
	}

}

