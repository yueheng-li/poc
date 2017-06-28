package com.elecom.crawler.batch.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.InitializingBean;

import com.elecom.crawler.entity.Product;

public class YodobashiItemProcessor implements ItemProcessor<Product, Product>, InitializingBean {

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public Product process(Product item) throws Exception {
		return item;
	}

}
