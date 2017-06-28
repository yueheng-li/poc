package com.elecom.crawler.pilot.batch.writer;

import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

public class UserMapperItemWriter implements ItemWriter<T> {
    private static final Logger logger = LoggerFactory.getLogger(UserMapperItemWriter.class);
//	@Autowired
//	private ArrivesWarehouseMapper arrivesWarehouseMapper;

	@Override
	public void write(List<? extends T> items) throws Exception {
		// TODO Auto-generated method stub
//		for (UserMaster userMaster : items) {
//			logger.info(">>>>>>>>>>>>>" + userMaster.getName());
//		}
	}
	

}
