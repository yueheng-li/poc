package com.elecom.crawler.pilot.batch.validator;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;

import com.elecom.crawler.pilot.batch.listener.UserJobExecutionListener;

public class UserMasterJobParametersValidator implements JobParametersValidator {
    private static final Logger logger = LoggerFactory.getLogger(UserJobExecutionListener.class);

	@Override
	public void validate(JobParameters parameters) throws JobParametersInvalidException { 
		logger.info("UserMasterJobParametersValidator.validate!!!");
		if (parameters == null) {
			throw new JobParametersInvalidException("The JobParameters can not be null");
		}

		Set<String> keys = parameters.getParameters().keySet();
		for (String key : keys) {
			logger.info("key = " + key);
		}

	}

}
