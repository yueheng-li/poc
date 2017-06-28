package com.elecom.crawler.pilot.batch.tasklet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.elecom.crawler.pilot.batch.writer.UserMapperItemWriter;

public class StoreUpdateIsJudgeDayTasklet implements Tasklet {
    private static final Logger logger = LoggerFactory.getLogger(UserMapperItemWriter.class);

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

//	@Autowired
//	private CmnX01JudgeDayProcess cmnX01JudgeDayProcess;

//    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
//    	if (cmnX01JudgeDayProcess != null) {
//    		// 日時締め処理中か判定する
//    		boolean isJudgeDay = cmnX01JudgeDayProcess.isJudgeDayProcess();
//    		
//    		// 日時締め処理中、処理終了
//    		if (isJudgeDay) {
//    			logger.error("is not Judge Day");
//    			throw new UnexpectedJobExecutionException("is not Judge Day");
//    		}
//    	}
//
//        return RepeatStatus.FINISHED;
//    }
}
