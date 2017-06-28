package com.elecom.crawler.entity;

import org.springframework.util.StringUtils;

public class Product extends Entity {

	private String product_no;
	private String product_name;
	private String shop_id;
	private String product_price;
	private String product_point;
	private String product_image_url;
	private String product_link_url;
	private int crawler_job_id;

	public String getProduct_no() {
		return product_no;
	}

	public void setProduct_no(String product_no) {
		this.product_no = product_no;
	}

	public String getShop_id() {
		return shop_id;
	}

	public void setShop_id(String shop_id) {
		this.shop_id = shop_id;
	}

	public String getProduct_price() {
		return product_price;
	}

	public void setProduct_price(String product_price) {
		if (StringUtils.isEmpty(product_price)) {
			product_price = "0";
		}
		this.product_price = product_price;
	}

	public String getProduct_image_url() {
		return product_image_url;
	}

	public void setProduct_image_url(String product_image_url) {
		this.product_image_url = product_image_url;
	}

	public String getProduct_link_url() {
		return product_link_url;
	}

	public void setProduct_link_url(String product_link_url) {
		this.product_link_url = product_link_url;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getProduct_point() {
		return product_point;
	}

	public void setProduct_point(String product_point) {
		if (StringUtils.isEmpty(product_point)) {
			product_point = "0";
		}
		this.product_point = product_point;
	}

	public int getCrawler_job_id() {
		return crawler_job_id;
	}

	public void setCrawler_job_id(int crawler_job_id) {
		this.crawler_job_id = crawler_job_id;
	}

}