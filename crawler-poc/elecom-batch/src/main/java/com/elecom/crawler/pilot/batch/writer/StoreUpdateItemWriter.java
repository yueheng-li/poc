package com.elecom.crawler.pilot.batch.writer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;


public class StoreUpdateItemWriter implements ItemWriter<Object> {
    private static final Logger logger = LoggerFactory.getLogger(StoreUpdateItemWriter.class);

	@Override
	public void write(List<? extends Object> items) throws Exception {
		// TODO Auto-generated method stub
		
	}
    
//	@Autowired
//	private ArrivesWarehouseMapper arrivesWarehouseMapper;
//
//	@Override
//	public void write(List<? extends ArrivesWarehouse> items) throws Exception {
//		// TODO Auto-generated method stub
//		for (ArrivesWarehouse item : items) {
//			arrivesWarehouseMapper.insert(item);
//		}
//	}
	

}
