package com.elecom.crawler.common.util;

import java.util.List;

import com.elecom.crawler.entity.Product;

public interface CrawlerUtils {

	public List<Product> crawlerSearchPages(String url, int JobId);
	public Product crawlerProduct(String productNo);
}
