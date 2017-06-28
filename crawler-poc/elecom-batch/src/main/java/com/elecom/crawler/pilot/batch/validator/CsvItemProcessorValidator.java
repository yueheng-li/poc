package com.elecom.crawler.pilot.batch.validator;

import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;
import org.springframework.beans.factory.InitializingBean;

public class CsvItemProcessorValidator implements Validator<T>, InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(CsvItemProcessorValidator.class);

	@Override
	public void afterPropertiesSet() throws Exception {
		
		
	}

	@Override
	public void validate(T value) throws ValidationException {
		// TODO Auto-generated method stub
		logger.info("CsvItemProcessorValidator.validate!!!");
	}

}
