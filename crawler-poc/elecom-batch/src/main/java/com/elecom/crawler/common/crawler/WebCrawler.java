package com.elecom.crawler.common.crawler;


import com.elecom.crawler.common.fetcher.PageFetchResult;
import com.elecom.crawler.common.fetcher.PageFetcher;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.exceptions.ContentFetchException;
import edu.uci.ics.crawler4j.parser.Parser;
import edu.uci.ics.crawler4j.url.WebURL;

public class WebCrawler {

	public Page processPage(WebURL curURL) throws Exception {
		PageFetchResult fetchResult = null;

		PageFetcher pageFetcher = new PageFetcher();

		Parser parser = new Parser(new CrawlConfig());
		if (curURL == null) {
			throw new Exception("Failed processing a NULL url !?");
		}

		fetchResult = pageFetcher.fetchPage(curURL);
		if (fetchResult == null) {
			return null;
		}
		int statusCode = fetchResult.getStatusCode();

		Page page = new Page(curURL);
		page.setFetchResponseHeaders(fetchResult.getResponseHeaders());
		page.setStatusCode(statusCode);
		if (statusCode < 200 || statusCode > 299) { // Not 2XX: 2XX status codes
													// indicate success

			System.out.println("http status code is " + statusCode);
			return null;

		} else { // if status code is 200

			if (!fetchResult.fetchContent(page)) {
				throw new ContentFetchException();
			}

			parser.parse(page, curURL.getURL());

		}

		return page;
	}

}
