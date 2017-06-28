package com.elecom.crawler.common.fetcher;

import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import edu.uci.ics.crawler4j.url.URLCanonicalizer;
import edu.uci.ics.crawler4j.url.WebURL;

public class PageFetcher {

	public PageFetchResult fetchPage(WebURL curURL) {
		PageFetchResult fetchResult = new PageFetchResult();

		String toFetchURL = curURL.getURL();
		HttpUriRequest request = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			request = newHttpUriRequest(toFetchURL);
			CloseableHttpResponse response = httpClient.execute(request);
			fetchResult.setEntity(response.getEntity());
			fetchResult.setResponseHeaders(response.getAllHeaders());

			// Setting HttpStatus
			int statusCode = response.getStatusLine().getStatusCode();

			// If Redirect ( 3xx )
			if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY || statusCode == HttpStatus.SC_MOVED_TEMPORARILY
					|| statusCode == HttpStatus.SC_MULTIPLE_CHOICES || statusCode == HttpStatus.SC_SEE_OTHER
					|| statusCode == HttpStatus.SC_TEMPORARY_REDIRECT || statusCode == 308) { // todo
																								// follow
																								// https://issues.apache.org/jira/browse/HTTPCORE-389

				Header header = response.getFirstHeader("Location");
				if (header != null) {
					String movedToUrl = URLCanonicalizer.getCanonicalURL(header.getValue(), toFetchURL);
					fetchResult.setMovedToUrl(movedToUrl);
				}
			} else if (statusCode >= 200 && statusCode <= 299) { // is 2XX,
																	// everything
																	// looks ok
				fetchResult.setFetchedUrl(toFetchURL);
				String uri = request.getURI().toString();
				if (!uri.equals(toFetchURL)) {
					if (!URLCanonicalizer.getCanonicalURL(uri).equals(toFetchURL)) {
						fetchResult.setFetchedUrl(uri);
					}
				}

				// Checking maximum size
				if (fetchResult.getEntity() != null) {
					long size = fetchResult.getEntity().getContentLength();
					if (size == -1) {
						Header length = response.getLastHeader("Content-Length");
						if (length == null) {
							length = response.getLastHeader("Content-length");
						}
						if (length != null) {
							size = Integer.parseInt(length.getValue());
						}
					}
				}
			}

			fetchResult.setStatusCode(statusCode);
			return fetchResult;
		}catch (Exception e) {
			e.printStackTrace();
		} finally { // occurs also with thrown exceptions
			if ((fetchResult.getEntity() == null) && (request != null)) {
				request.abort();
			}
		}
		return null;
	}

	/**
	 * Creates a new HttpUriRequest for the given url. The default is to create
	 * a HttpGet without any further configuration. Subclasses may override this
	 * method and provide their own logic.
	 *
	 * @param url
	 *            the url to be fetched
	 * @return the HttpUriRequest for the given url
	 */
	protected HttpUriRequest newHttpUriRequest(String url) {
		return new HttpGet(url);
	}
}
