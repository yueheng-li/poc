package com.elecom.crawler.batch.writer;

import com.elecom.crawler.common.Contants;

public class YodoboshiItemWriter extends CrawlerItemWriter {


	public String getShopId() {
		// Do nothing by default
		// Sub-classed should override this to add their custom functionality
		return Contants.YODOBASHI_TYPE;
	}
}
