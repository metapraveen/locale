/*
 * Created on Sep 22, 2004
 *
 */
package com.tra;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.transerainc.tam.config.ServerMapping;
import com.transerainc.tam.util.IOUtil;
import com.transerainc.tam.util.NameValue;

/**
 * @author <a href="mailto:ramesh.bobba@transerainc.com">Ramesh Bobba </a>
 * @version $Revision: 1.28 $
 */
public class HttpUtil {
	private static Logger lgr = LoggerFactory
			.getLogger("com.transerainc.tam.util.HttpUtil");

	private static HttpConnectionParams hcParams = null;

	public static int HTTP_CONNECT_TIMEOUT = 8000;

	// 30 second default timeout
	public static int HTTP_SO_TIMEOUT = 30000;

	public static String HTTP_PROXY_HOST;

	public static int HTTP_PROXY_PORT;

	static {
		hcParams = new HttpConnectionParams();
		hcParams.setConnectionTimeout(HTTP_CONNECT_TIMEOUT);
		hcParams.setSendBufferSize(HTTP_SO_TIMEOUT);
	}

	/**
	 * 
	 */
	public HttpUtil() {
	}

	public static String doHttpGet(String url, String logIdentifier)
			throws Exception {
		return doHttpGet(url, HTTP_CONNECT_TIMEOUT, logIdentifier);
	}

	public static String doHttpGet(String url, int conTimeout,
			String logIdentifier) throws Exception {
		return doHttpGet(url, conTimeout, HTTP_SO_TIMEOUT, logIdentifier);
	}

	public static String doHttpGet(String url, int conTimeout, int soTimeout,
			String logIdentifier) throws Exception {
		if (lgr.isDebugEnabled()) {
			lgr.debug(logIdentifier + ": Executing url: " + url);
		}

		GetMethod get = new GetMethod(url);
		String response = null;
		HttpClient client = null;
		try {
			client = new HttpClient();

			if (conTimeout > 0) {
				client.getHttpConnectionManager().getParams()
						.setConnectionTimeout(conTimeout);
			}

			if (soTimeout > 0) {
				client.getHttpConnectionManager().getParams()
						.setSoTimeout(soTimeout);
			}

			if (HTTP_PROXY_HOST != null) {
				client.getHostConfiguration().setProxy(HTTP_PROXY_HOST,
					HTTP_PROXY_PORT);
			}

			int statusCode = -1;
			int attempt = 0;

			// We will retry up to 3 times.
			while ((statusCode == -1) && (attempt < 3)) {
				// execute the method.
				statusCode = client.executeMethod(get);

				attempt++;

				if (statusCode == -1) {
					lgr.warn(logIdentifier + ": Attempt " + attempt
						+ " to connect to url failed. "
						+ "Max number of tries 3");
				}
			}

			// Check that we didn't run out of retries.
			if (statusCode == -1) {
				lgr.error(logIdentifier + ": Failed to recover from exception.");
			} else {
				response =
						IOUtil.getInputAsString(get.getResponseBodyAsStream());
			}

			if (lgr.isDebugEnabled()) {
				lgr.debug(logIdentifier + ": server returned " + response);
			}
		} finally {
			releaseResources(get, client);
		}

		return response;
	}

	private static void releaseResources(HttpMethod method, HttpClient client) {
		if (method != null) {
			try {
				method.releaseConnection();
			} catch (Exception e) {
			}
		}
		if (client != null) {
			try {
				client.getHttpConnectionManager().closeIdleConnections(0);
			} catch (Exception e) {
			}
		}
	}

	public static HttpStatus doGet(String url, int conTimeout,
			String logIdentifier) throws IOException {
		return doGet(url, conTimeout, HTTP_SO_TIMEOUT, logIdentifier);
	}

	public static HttpStatus doGet(String url, int conTimeout, int soTimeout,
			String logIdentifier) throws IOException {
		if (lgr.isDebugEnabled()) {
			lgr.debug(logIdentifier + ": Executing url: " + url);
		}

		GetMethod get = new GetMethod(url);
		HttpStatus status = new HttpStatus();
		HttpClient client = null;
		try {
			client = new HttpClient();

			if (conTimeout > 0) {
				client.getHttpConnectionManager().getParams()
						.setConnectionTimeout(conTimeout);
			}

			if (soTimeout > 0) {
				client.getHttpConnectionManager().getParams()
						.setSoTimeout(soTimeout);
			}

			if (HTTP_PROXY_HOST != null) {
				client.getHostConfiguration().setProxy(HTTP_PROXY_HOST,
					HTTP_PROXY_PORT);
			}

			int statusCode = -1;
			int attempt = 0;

			// We will retry up to 3 times.
			while ((statusCode == -1) && (attempt < 3)) {
				// execute the method.
				statusCode = client.executeMethod(get);

				attempt++;

				if (statusCode == -1) {
					lgr.warn(logIdentifier + ": Attempt " + attempt
						+ " to connect to url failed. "
						+ "Max number of tries 3");
				}
			}

			status.setCode(get.getStatusCode());
			status.setStatusLine(get.getStatusLine().getReasonPhrase());
			status.setStatusText(get.getStatusText());
			status.setResponse(get.getResponseBodyAsString());

			// Check that we didn't run out of retries.
			if (lgr.isDebugEnabled()) {
				lgr.debug(logIdentifier + ": server returned " + status);
			}
		} finally {
			releaseResources(get, client);
		}

		return status;
	}

	public static String doHttpXMLPost(String url, String body)
			throws HttpException, IOException {
		return doHttpXMLPost(url, body, HTTP_SO_TIMEOUT);
	}

	public static String doHttpXMLPost(String url, String body, int soTimeout)
			throws HttpException, IOException {
		PostMethod post = new PostMethod(url);
		HttpClient httpclient = null;

		try {
			// Request content will be retrieved directly
			// from the input stream
			post.setRequestEntity(new StringRequestEntity(body));

			// Per default, the request content needs to be buffered
			// in order to determine its length.
			// Request body buffering can be avoided when
			// = content length is explicitly specified
			// = chunk-encoding is used
			// if (body.length() < Integer.MAX_VALUE) {
			// post.setRequestContentLength((int) body.length());
			// } else {
			// post
			// .setRequestContentLength(EntityEnclosingMethod.
			// CONTENT_LENGTH_CHUNKED);
			// }

			// Specify content type and encoding
			// If content encoding is not explicitly specified
			// ISO-8859-1 is assumed
			post.setRequestHeader("Content-type",
				"text/xml; charset=ISO-8859-1");

			// Get HTTP client
			httpclient = new HttpClient();
			httpclient.getHttpConnectionManager().getParams()
					.setConnectionTimeout(HTTP_CONNECT_TIMEOUT);
			if (soTimeout > 0) {
				httpclient.getHttpConnectionManager().getParams()
						.setSoTimeout(soTimeout);
			}

			if (HTTP_PROXY_HOST != null) {
				httpclient.getHostConfiguration().setProxy(HTTP_PROXY_HOST,
					HTTP_PROXY_PORT);
			}

			// Execute request
			httpclient.executeMethod(post);

			return post.getResponseBodyAsString();

		} finally {
			// Release current connection to the connection pool once you are
			// done
			releaseResources(post, httpclient);
		}
	}

	public static com.transerainc.tam.util.HttpStatus doHttpXMLPostReturningStatus(String url,
			String body) throws HttpException, IOException {
		return doHttpXMLPostReturningStatus(url, body, HTTP_SO_TIMEOUT);
	}

	public static com.transerainc.tam.util.HttpStatus doHttpXMLPostReturningStatus(String url,
			String body, int soTimeout) throws HttpException, IOException {
		PostMethod post = new PostMethod(url);
		com.transerainc.tam.util.HttpStatus status = new com.transerainc.tam.util.HttpStatus();
		HttpClient httpclient = null;
		try {
			// Request content will be retrieved directly
			// from the input stream
			post.setRequestEntity(new StringRequestEntity(body));

			// Specify content type and encoding
			// If content encoding is not explicitly specified
			// ISO-8859-1 is assumed
			post.setRequestHeader("Content-type",
				"text/xml; charset=ISO-8859-1");

			// Get HTTP client
			httpclient = new HttpClient();
			httpclient.getHttpConnectionManager().getParams()
					.setConnectionTimeout(HTTP_CONNECT_TIMEOUT);
			if (soTimeout > 0) {
				httpclient.getHttpConnectionManager().getParams()
						.setSoTimeout(soTimeout);
			}

			if (HTTP_PROXY_HOST != null) {
				httpclient.getHostConfiguration().setProxy(HTTP_PROXY_HOST,
					HTTP_PROXY_PORT);
			}

			// Execute request
			httpclient.executeMethod(post);

			status.setCode(post.getStatusCode());
			status.setStatusLine(post.getStatusLine().getReasonPhrase());
			status.setStatusText(post.getStatusText());
			status.setResponse(post.getResponseBodyAsString());
			return status;
		} finally {
			// Release current connection to the connection pool once you are
			// done
			releaseResources(post, httpclient);
		}
	}

	public static com.transerainc.tam.util.HttpStatus doHttpXMLPost(String url, File f)
			throws HttpException, IOException {
		return doHttpXMLPost(url, f, HTTP_SO_TIMEOUT);
	}

	public static com.transerainc.tam.util.HttpStatus doHttpXMLPost(String url, File f, int soTimeout)
			throws HttpException, IOException {
		PostMethod post = new PostMethod(url);

		com.transerainc.tam.util.HttpStatus status = new com.transerainc.tam.util.HttpStatus();
		HttpClient httpclient = null;
		try {
			// Request content will be retrieved directly
			// from the input stream
			post.setRequestEntity(new InputStreamRequestEntity(
																new FileInputStream(
																					f),
																f.length()));

			// Per default, the request content needs to be buffered
			// in order to determine its length.
			// Request body buffering can be avoided when
			// = content length is explicitly specified
			// = chunk-encoding is used
			// if (body.length() < Integer.MAX_VALUE) {
			// post.setRequestContentLength((int) body.length());
			// } else {
			// post
			// .setRequestContentLength(EntityEnclosingMethod.
			// CONTENT_LENGTH_CHUNKED);

			// }
			// Specify content type and encoding
			// If content encoding is not explicitly specified
			// ISO-8859-1 is assumed
			post.setRequestHeader("Content-type",
				"text/xml; charset=ISO-8859-1");

			// Get HTTP client
			httpclient = new HttpClient();
			httpclient.getHttpConnectionManager().getParams()
					.setConnectionTimeout(HTTP_CONNECT_TIMEOUT);
			if (soTimeout > 0) {
				httpclient.getHttpConnectionManager().getParams()
						.setSoTimeout(soTimeout);
			}

			if (HTTP_PROXY_HOST != null) {
				httpclient.getHostConfiguration().setProxy(HTTP_PROXY_HOST,
					HTTP_PROXY_PORT);
			}

			// Execute request
			httpclient.executeMethod(post);

			status.setCode(post.getStatusCode());
			status.setStatusLine(post.getStatusLine().getReasonPhrase());
			status.setStatusText(post.getStatusText());
			status.setResponse(post.getResponseBodyAsString());
			return status;
		} finally {
			// Release current connection to the connection pool once you are
			// done
			releaseResources(post, httpclient);
		}
	}

	public static HttpStatus doHttpXMLPost(String url, InputStream stream)
			throws HttpException, IOException {
		return doHttpXMLPost(url, stream, HTTP_SO_TIMEOUT);
	}

	public static HttpStatus doHttpXMLPost(String url, InputStream stream,
			int soTimeout) throws HttpException, IOException {
		PostMethod post = new PostMethod(url);

		HttpStatus status = new HttpStatus();
		HttpClient httpclient = null;
		try {
			// Request content will be retrieved directly
			// from the input stream
			post.setRequestEntity(new InputStreamRequestEntity(stream));

			// Per default, the request content needs to be buffered
			// in order to determine its length.
			// Request body buffering can be avoided when
			// = content length is explicitly specified
			// = chunk-encoding is used
			// if (body.length() < Integer.MAX_VALUE) {
			// post.setRequestContentLength((int) body.length());
			// } else {
			// post
			// .setRequestContentLength(EntityEnclosingMethod.
			// CONTENT_LENGTH_CHUNKED);

			// }
			// Specify content type and encoding
			// If content encoding is not explicitly specified
			// ISO-8859-1 is assumed
			post.setRequestHeader("Content-type",
				"text/xml; charset=ISO-8859-1");

			// Get HTTP client
			httpclient = new HttpClient();
			httpclient.getHttpConnectionManager().getParams()
					.setConnectionTimeout(HTTP_CONNECT_TIMEOUT);
			if (soTimeout > 0) {
				httpclient.getHttpConnectionManager().getParams()
						.setSoTimeout(soTimeout);
			}

			if (HTTP_PROXY_HOST != null) {
				httpclient.getHostConfiguration().setProxy(HTTP_PROXY_HOST,
					HTTP_PROXY_PORT);
			}

			// Execute request
			httpclient.executeMethod(post);

			status.setCode(post.getStatusCode());
			status.setStatusLine(post.getStatusLine().getReasonPhrase());
			status.setStatusText(post.getStatusText());
			status.setResponse(post.getResponseBodyAsString());
			return status;
		} finally {
			// Release current connection to the connection pool once you are
			// done
			releaseResources(post, httpclient);
		}
	}

	public static com.transerainc.tam.util.HttpStatus doHttpPost(String url, Map<String, String> kvParams)
			throws IOException {
		return doHttpPost(url, kvParams, HTTP_SO_TIMEOUT);
	}

	public static com.transerainc.tam.util.HttpStatus doHttpPost(String url,
			Map<String, String> kvParams, int soTimeout) throws IOException {

		PostMethod method = new PostMethod(url);
		method.addRequestHeader("Content-Type", PostMethod.FORM_URL_ENCODED_CONTENT_TYPE +"; charset=UTF-8");

		com.transerainc.tam.util.HttpStatus status = new com.transerainc.tam.util.HttpStatus();
		HttpClient client = null;
		try {
			NameValuePair[] data = new NameValuePair[kvParams.size()];
			int i = 0;
			for (Map.Entry<String, String> entry : kvParams.entrySet()) {
				data[i] = new NameValuePair(entry.getKey(), entry.getValue());
				i++;
			}
			method.setRequestBody(data);

			client = new HttpClient();
			client.getHttpConnectionManager().getParams()
					.setConnectionTimeout(HTTP_CONNECT_TIMEOUT);
			if (soTimeout > 0) {
				client.getHttpConnectionManager().getParams()
						.setSoTimeout(soTimeout);
			}

			if (HTTP_PROXY_HOST != null) {
				client.getHostConfiguration().setProxy(HTTP_PROXY_HOST,
					HTTP_PROXY_PORT);
			}

			client.executeMethod(method);

			status.setCode(method.getStatusCode());
			status.setStatusLine(method.getStatusLine().getReasonPhrase());
			status.setStatusText(method.getStatusText());
			status.setResponse(method.getResponseBodyAsString());
			return status;
		} finally {
			// Release current connection to the connection pool once you are
			// done
			releaseResources(method, client);
		}
	}

	public static InputStream doHttpPostReturningStream(String url,
			Map<String, String> kvParams) throws IOException {
		return doHttpPostReturningStream(url, kvParams, HTTP_SO_TIMEOUT);
	}

	public static InputStream doHttpPostReturningStream(String url,
			Map<String, String> kvParams, int soTimeout) throws IOException {

		PostMethod method = new PostMethod(url);
		// HttpStatus status = new HttpStatus();
		HttpClient client = null;
		try {
			NameValuePair[] data = new NameValuePair[kvParams.size()];
			int i = 0;
			for (Map.Entry<String, String> entry : kvParams.entrySet()) {
				data[i] = new NameValuePair(entry.getKey(), entry.getValue());
				i++;
			}
			method.setRequestBody(data);
			// Set the Connection header to close so that the HTTP connection is
			// immediately torn down
			method.setRequestHeader("Connection", "close");
			client = new HttpClient();

			if (HTTP_PROXY_HOST != null) {
				client.getHostConfiguration().setProxy(HTTP_PROXY_HOST,
					HTTP_PROXY_PORT);
			}

			client.getHttpConnectionManager().getParams()
					.setConnectionTimeout(HTTP_CONNECT_TIMEOUT);
			if (soTimeout > 0) {
				client.getHttpConnectionManager().getParams()
						.setSoTimeout(soTimeout);
			}
			client.executeMethod(method);
			return method.getResponseBodyAsStream();
		} finally {
			// Release current connection to the connection pool once you are
			// done
			// not closing because need to read the data from the stream
			// method.releaseConnection();
		}
	}

	public static boolean isHttpCall(String url) {
		if ((url != null)
			&& (url.startsWith("http://") || url.startsWith("https://"))) {
			return true;
		}

		return false;
	}

	public static String getBaseServerUrl(ServerMapping mapping) {
		return "http://" + mapping.getHost() + ":" + mapping.getPort();
	}

	public static HttpStatus doHttpPost(String url, NameValue[] data)
			throws IOException {
		return doHttpPost(url, data, HTTP_SO_TIMEOUT);
	}

	/*
	 * This api takes an array of NameValue object allowing passing of multipe
	 * values with same key.
	 */
	public static HttpStatus doHttpPost(String url, NameValue[] data,
			int soTimeout) throws IOException {

		PostMethod method = new PostMethod(url);

		HttpStatus status = new HttpStatus();
		HttpClient client = null;
		try {
			method.setRequestBody(convertToNameValuePair(data));

			client = new HttpClient();

			if (HTTP_PROXY_HOST != null) {
				client.getHostConfiguration().setProxy(HTTP_PROXY_HOST,
					HTTP_PROXY_PORT);
			}

			client.getHttpConnectionManager().getParams()
					.setConnectionTimeout(HTTP_CONNECT_TIMEOUT);
			if (soTimeout > 0) {
				client.getHttpConnectionManager().getParams()
						.setSoTimeout(soTimeout);
			}

			client.executeMethod(method);

			status.setCode(method.getStatusCode());
			status.setStatusLine(method.getStatusLine().getReasonPhrase());
			status.setStatusText(method.getStatusText());
			status.setResponse(method.getResponseBodyAsString());
			return status;
		} finally {
			// Release current connection to the connection pool once you are
			// done
			releaseResources(method, client);
		}
	}

	/*
	 * util to convert NameValue array to NameValuePair array
	 */
	private static NameValuePair[] convertToNameValuePair(NameValue[] data) {

		if (data != null && data.length > 0) {
			NameValuePair[] newData = new NameValuePair[data.length];
			for (int i = 0; i < data.length; i++) {
				newData[i] =
						new NameValuePair(data[i].getName(), data[i].getValue());
			}
			return newData;
		} else
			return new NameValuePair[0];

	}

}
