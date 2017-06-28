package com.elecom.crawler.batch.writer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.elecom.crawler.entity.CrawlerJob;
import com.elecom.crawler.entity.Product;
import com.elecom.crawler.mapper.CrawlerJobMapper;
import com.elecom.crawler.mapper.ProductMapper;
import com.elecom.crawler.pilot.batch.writer.StoreUpdateItemWriter;

public class CrawlerItemWriter implements ItemWriter<Product> {
	private static final Logger logger = LoggerFactory.getLogger(StoreUpdateItemWriter.class);

	@Autowired
	ProductMapper mapper;
	@Autowired
	private CrawlerJobMapper crawlerJobMapper;

	public String getShopId() {
		// Do nothing by default
		// Sub-classed should override this to add their custom functionality
		return "";
	}

	@Override
	public void write(List<? extends Product> items) throws Exception {
		// TODO Auto-generated method stub
		int jobId = 0;
		for (Product product : items) {
			Product p = mapper.selectByKey(product);
			product.setShop_id(this.getShopId());
			if (p != null && !StringUtils.isEmpty(p.getProduct_no())) {
				mapper.updateProductByKey(product);
			} else {
				mapper.insertProduct(product);
			}
			jobId = product.getCrawler_job_id();
			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>" + product.getProduct_no());
		}
		this.updateSearchUrl(jobId);

	}

	private void updateSearchUrl(int jobId) {
		if (jobId > 0) {
			CrawlerJob job = new CrawlerJob();
			job.setCrawler_job_id(jobId);
			crawlerJobMapper.updateByJobId(job);
		}
	}

}
