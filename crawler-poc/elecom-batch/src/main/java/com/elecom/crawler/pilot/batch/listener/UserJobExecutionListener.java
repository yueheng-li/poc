package com.elecom.crawler.pilot.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class UserJobExecutionListener implements JobExecutionListener{
    private static final Logger logger = LoggerFactory.getLogger(UserJobExecutionListener.class);

	@Override
	public void beforeJob(JobExecution jobExecution) {
        logger.info("UserJobExecutionListener.beforeJob !!!");
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
        logger.info("UserJobExecutionListener.afterJob !!!");
        
        
        logger.info("jobExecution.getStatus() = " + jobExecution.getStatus() );
		
	}

}
