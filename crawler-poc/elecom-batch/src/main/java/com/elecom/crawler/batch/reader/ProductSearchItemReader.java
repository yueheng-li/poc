package com.elecom.crawler.batch.reader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.elecom.crawler.common.util.CommonUtils;
import com.elecom.crawler.common.util.CrawlerUtils;
import com.elecom.crawler.entity.CrawlerProductJob;
import com.elecom.crawler.entity.Product;
import com.elecom.crawler.mapper.CrawlerProductJobMapper;

public class ProductSearchItemReader extends AbstractPagingItemReader<Product> {

	private static final Logger logger = LoggerFactory.getLogger(CrawlerItemReader.class);
	@Autowired
	private CrawlerProductJobMapper mapper;

	@Autowired
	@Qualifier("yodobashiUtils")  
	private CrawlerUtils yodobashiUtils;

	@Autowired
	@Qualifier("yamadaUtils")  
	private CrawlerUtils yamadaUtils;

	@Autowired
	@Qualifier("amazonUtils")  
	private CrawlerUtils amazonUtils;

	@Autowired
	@Qualifier("rakutenUtils")  
	private CrawlerUtils rakutenUtils;

	@Override
	protected void doReadPage() {
		List<Product> list = new ArrayList<Product>();
		CrawlerProductJob job = this.getCrawlerProductNo();
		if (job != null && CommonUtils.isProductNoNotEmpty(job.getCrawler_product_no())) {
			String productNo = job.getCrawler_product_no();
			Product product = yodobashiUtils.crawlerProduct(productNo);
			if (product != null) {
				list.add(product);
			}
			product = yamadaUtils.crawlerProduct(productNo);
			if (product != null) {
				list.add(product);
			}
			product = amazonUtils.crawlerProduct(productNo);
			if (product != null) {
				list.add(product);
			}
			product = rakutenUtils.crawlerProduct(productNo);
			if (product != null) {
				list.add(product);
			}
		}
		super.setPageSize(list.size());
		
		if (results == null) {
			results = new CopyOnWriteArrayList<Product>();
		} else {
			results.clear();
		}
		results.addAll(list);
	}

	@Override
	protected void doJumpToPage(int itemIndex) {
	}

	private CrawlerProductJob getCrawlerProductNo() {
		CrawlerProductJob record = null;
		List<CrawlerProductJob> crawlerJobList = mapper.selectAll();
		if (crawlerJobList != null && crawlerJobList.size() > 0) {
			record = new CrawlerProductJob();
			record = crawlerJobList.get(0);
		}
		return record;
	}

	public CrawlerUtils getYodobashiUtils() {
		return yodobashiUtils;
	}

	public void setYodobashiUtils(CrawlerUtils yodobashiUtils) {
		this.yodobashiUtils = yodobashiUtils;
	}

	public CrawlerUtils getYamadaUtils() {
		return yamadaUtils;
	}

	public void setYamadaUtils(CrawlerUtils yamadaUtils) {
		this.yamadaUtils = yamadaUtils;
	}

	public CrawlerUtils getAmazonUtils() {
		return amazonUtils;
	}

	public void setAmazonUtils(CrawlerUtils amazonUtils) {
		this.amazonUtils = amazonUtils;
	}

	public CrawlerUtils getRakutenUtils() {
		return rakutenUtils;
	}

	public void setRakutenUtils(CrawlerUtils rakutenUtils) {
		this.rakutenUtils = rakutenUtils;
	}
	
	

}