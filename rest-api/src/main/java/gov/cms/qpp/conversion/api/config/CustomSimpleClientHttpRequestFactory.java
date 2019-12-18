package gov.cms.qpp.conversion.api.config;

import org.springframework.http.client.SimpleClientHttpRequestFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.HttpURLConnection;

public class CustomSimpleClientHttpRequestFactory extends SimpleClientHttpRequestFactory {

	private final HostnameVerifier verifier;

	public CustomSimpleClientHttpRequestFactory(HostnameVerifier verifier) {
		this.verifier = verifier;
	}

	@Override
	protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
		if (connection instanceof HttpsURLConnection) {
			((HttpsURLConnection) connection).setHostnameVerifier(verifier);
		}
		super.prepareConnection(connection, httpMethod);
	}

}
