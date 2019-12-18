package gov.cms.qpp.conversion.api.model;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class NullHostnameVerifier implements HostnameVerifier{
	public boolean verify(String hostname, SSLSession session) {
		return true;
	}
}
