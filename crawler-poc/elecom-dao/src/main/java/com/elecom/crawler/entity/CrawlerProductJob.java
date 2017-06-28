package com.elecom.crawler.entity;

public class CrawlerProductJob extends Entity {

	private String crawler_product_no;
	private String crawler_product_flag;

	public String getCrawler_product_no() {
		return crawler_product_no;
	}

	public void setCrawler_product_no(String crawler_product_no) {
		this.crawler_product_no = crawler_product_no;
	}

	public String getCrawler_product_flag() {
		return crawler_product_flag;
	}

	public void setCrawler_product_flag(String crawler_product_flag) {
		this.crawler_product_flag = crawler_product_flag;
	}

}