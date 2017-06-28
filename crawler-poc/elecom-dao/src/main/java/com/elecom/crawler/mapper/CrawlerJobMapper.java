package com.elecom.crawler.mapper;

import java.util.List;

import com.elecom.crawler.entity.CrawlerJob;

public interface CrawlerJobMapper {

	List<CrawlerJob> selectAll();

	int insertCrawlerJob(CrawlerJob record);

	List<CrawlerJob> selectByJobType(CrawlerJob record);

	int updateByJobId(CrawlerJob record);
}
