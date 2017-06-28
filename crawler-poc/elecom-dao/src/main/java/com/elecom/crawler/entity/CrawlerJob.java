package com.elecom.crawler.entity;

public class CrawlerJob extends Entity {

	private int crawler_job_id;
	private String crawler_job_type;
	private String crawler_search_url;
	private String crawler_flag;

	public int getCrawler_job_id() {
		return crawler_job_id;
	}

	public void setCrawler_job_id(int crawler_job_id) {
		this.crawler_job_id = crawler_job_id;
	}

	public String getCrawler_job_type() {
		return crawler_job_type;
	}

	public void setCrawler_job_type(String crawler_job_type) {
		this.crawler_job_type = crawler_job_type;
	}

	public String getCrawler_search_url() {
		return crawler_search_url;
	}

	public void setCrawler_search_url(String crawler_search_url) {
		this.crawler_search_url = crawler_search_url;
	}

	public String getCrawler_flag() {
		return crawler_flag;
	}

	public void setCrawler_flag(String crawler_flag) {
		this.crawler_flag = crawler_flag;
	}

}