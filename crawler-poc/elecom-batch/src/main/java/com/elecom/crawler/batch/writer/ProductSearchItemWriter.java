package com.elecom.crawler.batch.writer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.elecom.crawler.common.util.CommonUtils;
import com.elecom.crawler.entity.CrawlerProductJob;
import com.elecom.crawler.entity.Product;
import com.elecom.crawler.mapper.CrawlerProductJobMapper;
import com.elecom.crawler.mapper.ProductMapper;
import com.elecom.crawler.pilot.batch.writer.StoreUpdateItemWriter;

public class ProductSearchItemWriter implements ItemWriter<Product> {
	private static final Logger logger = LoggerFactory.getLogger(StoreUpdateItemWriter.class);

	@Autowired
	ProductMapper mapper;

	@Autowired
	private CrawlerProductJobMapper crawlerProductJobMapper;

	@Override
	public void write(List<? extends Product> items) throws Exception {
		// TODO Auto-generated method stub
		String productNo = "";
		for (Product product : items) {
			Product p = mapper.selectByKey(product);
			if (p != null && !StringUtils.isEmpty(p.getProduct_no())) {
				mapper.updateProductByKey(product);
			} else {
				mapper.insertProduct(product);
			}
			productNo = product.getProduct_no();
			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>" + product.getProduct_no() + " ## " + product.getShop_id());
		}
		this.updateCrawlerProduct(productNo);

	}

	private void updateCrawlerProduct(String productNo) {
		if (CommonUtils.isProductNoNotEmpty(productNo)) {
			CrawlerProductJob job = new CrawlerProductJob();
			job.setCrawler_product_no(productNo);
			crawlerProductJobMapper.updateByKey(job);
		}
	}

}
