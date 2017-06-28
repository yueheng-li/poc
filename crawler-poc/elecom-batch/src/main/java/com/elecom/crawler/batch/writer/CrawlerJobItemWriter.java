package com.elecom.crawler.batch.writer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.elecom.crawler.entity.CrawlerJob;
import com.elecom.crawler.mapper.CrawlerJobMapper;

public class CrawlerJobItemWriter implements ItemWriter<CrawlerJob> {
    private static final Logger logger = LoggerFactory.getLogger(CrawlerJobItemWriter.class);

	@Autowired
	private CrawlerJobMapper mapper;

	@Override
	public void write(List<? extends CrawlerJob> items) throws Exception {
		for (CrawlerJob crawlerJob : items) {
			if (mapper != null) {
				mapper.insertCrawlerJob(crawlerJob);
			}
		}
	}
	

}
