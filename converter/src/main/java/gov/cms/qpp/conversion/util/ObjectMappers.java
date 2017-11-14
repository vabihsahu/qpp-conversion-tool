package gov.cms.qpp.conversion.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;

public class ObjectMappers {

	private static final ObjectMapper INSTANCE = new ObjectMapper();
	public static final ObjectReader READER = INSTANCE.reader();
	public static final ObjectWriter WRITER = INSTANCE.writer();

}
