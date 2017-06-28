package com.elecom.crawler.batch.reader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.StringUtils;

import com.elecom.crawler.common.Contants;
import com.elecom.crawler.common.util.CrawlerUtils;
import com.elecom.crawler.entity.CrawlerJob;
import com.elecom.crawler.entity.Product;
import com.elecom.crawler.mapper.CrawlerJobMapper;

public class AmazonItemReader extends AbstractPagingItemReader<Product> {

	private static final Logger logger = LoggerFactory.getLogger(CrawlerItemReader.class);
	@Autowired
	private CrawlerJobMapper mapper;

	@Autowired
	@Qualifier("amazonUtils")  
	private CrawlerUtils aUtils;

	public String getShopType() {
		// Do nothing by default
		// Sub-classed should override this to add their custom functionality
		return Contants.AMAZON_TYPE;
	}

	public int getPageSize() {
		// Do nothing by default
		// Sub-classed should override this to add their custom functionality
		return 18;
	}

	@Override
	protected void doReadPage() {
		List<Product> list = new ArrayList<Product>();
		CrawlerJob job = this.getSearchUrl(this.getShopType());
		// yodobashi search
		if (aUtils != null && !StringUtils.isEmpty(job.getCrawler_search_url())) {
			logger.info("url : " + job.getCrawler_search_url());
			list = aUtils.crawlerSearchPages(job.getCrawler_search_url(), job.getCrawler_job_id());
		}
		super.setPageSize(this.getPageSize());
		if (results == null) {
			results = new CopyOnWriteArrayList<Product>();
		} else {
			results.clear();
		}
		results.addAll(list);
	}

	@Override
	protected void doJumpToPage(int itemIndex) {
		// TODO Auto-generated method stub

	}

	public CrawlerUtils getaUtils() {
		return aUtils;
	}

	public void setaUtils(CrawlerUtils aUtils) {
		this.aUtils = aUtils;
	}

	private CrawlerJob getSearchUrl(String jobType) {
		CrawlerJob record = new CrawlerJob();
		record.setCrawler_job_type(jobType);
		List<CrawlerJob> crawlerJobList = mapper.selectByJobType(record);
		if (crawlerJobList != null && crawlerJobList.size() > 0) {
			record = crawlerJobList.get(0);
		}
		return record;
	}
}
