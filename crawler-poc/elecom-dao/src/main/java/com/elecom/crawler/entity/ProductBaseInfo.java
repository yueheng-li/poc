package com.elecom.crawler.entity;

public class ProductBaseInfo {

	private String product_no;
	private String product_name;
	private String shop_id;
	private String shop_name;
	private String product_price;
	private String product_image_url;
	private String product_link_url;
	public String getProduct_no() {
		return product_no;
	}
	public void setProduct_no(String product_no) {
		this.product_no = product_no;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getShop_name() {
		return shop_name;
	}
	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}
	public String getProduct_price() {
		return product_price;
	}
	public void setProduct_price(String product_price) {
		this.product_price = product_price;
	}
	public String getShop_id() {
		return shop_id;
	}
	public void setShop_id(String shop_id) {
		this.shop_id = shop_id;
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
	
	
}
