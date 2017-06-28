package com.elecom.crawler.pilot.batch.processor;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component("csvItemProcessor")
public class CsvItemProcessor implements ItemProcessor<T, T>, InitializingBean {

	/**
	 * 読み込みデータを処理する
	 * 
	 * @param userMaster
	 *            処理前のデータ
	 * @return 処理後のデータ
	 * @exception Exception
	 *                異常
	 */
	// @Override
	// public UserMaster process(UserMaster userMaster) throws
	// ValidationException {
	// super.process(userMaster);
	// if (cmnX01JudgeDayProcess != null) {
	// cmnX01JudgeDayProcess.isJudgeDayProcess();
	// }
	// System.out.println(userMaster.getName());
	// System.out.println(userMaster.getAccount());
	// System.out.println(userMaster.getPassword());
	// return userMaster;
	// }

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public T process(T item) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
