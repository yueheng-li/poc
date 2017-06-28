package com.elecom.crawler.pilot.batch.listener;

import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemReadListener;

public class UserStep1JobExecutionListener implements ItemReadListener<T>{
    private static final Logger logger = LoggerFactory.getLogger(UserStep1JobExecutionListener.class);

	@Override
	public void beforeRead() {
		// TODO Auto-generated method stub
        logger.info("UserStep1JobExecutionListener.beforeRead !!!");
		
	}

	@Override
	public void afterRead(T item) {
		// TODO Auto-generated method stub
        logger.info("UserStep1JobExecutionListener.afterRead !!!");
		
	}

	@Override
	public void onReadError(Exception ex) {
		// TODO Auto-generated method stub
        logger.info("UserStep1JobExecutionListener.onReadError !!!");
		
	}

}
