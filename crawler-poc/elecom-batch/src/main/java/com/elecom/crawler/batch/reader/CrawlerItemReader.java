package com.elecom.crawler.batch.reader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.elecom.crawler.common.util.CrawlerUtils;
import com.elecom.crawler.entity.CrawlerJob;
import com.elecom.crawler.entity.Product;
import com.elecom.crawler.mapper.CrawlerJobMapper;

public class CrawlerItemReader extends AbstractPagingItemReader<Product> {
    private static final Logger logger = LoggerFactory.getLogger(CrawlerItemReader.class);
	@Autowired
	private CrawlerJobMapper mapper;

	@Autowired
	private CrawlerUtils utils;

	public String getShopType() {
		// Do nothing by default
		// Sub-classed should override this to add their custom functionality
		return "";
	}
	

	public int getPageSize() {
		// Do nothing by default
		// Sub-classed should override this to add their custom functionality
		return 10;
	}
	
	@Override
	protected void doReadPage() {
		List<Product> list = new ArrayList<Product>();
		CrawlerJob job = this.getSearchUrl(this.getShopType()) ;
		// yodobashi search
		if (utils != null && !StringUtils.isEmpty(job.getCrawler_search_url())) {
			logger.info("url : " + job.getCrawler_search_url());
			list = utils.crawlerSearchPages(job.getCrawler_search_url(), job.getCrawler_job_id());
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

	public CrawlerUtils getUtils() {
		return utils;
	}

	public void setUtils(CrawlerUtils utils) {
		this.utils = utils;
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
