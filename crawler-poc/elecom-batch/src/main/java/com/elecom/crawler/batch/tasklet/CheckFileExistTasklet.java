package com.elecom.crawler.batch.tasklet;

import java.io.File;
import java.util.List;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.UnexpectedJobExecutionException;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;

public class CheckFileExistTasklet extends BaseFileTasklet {
    public List<String> fileNameList;

    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        File dir = directory.getFile();
        File[] files = dir.listFiles();

        // no file
        if (files == null) {
            throw new UnexpectedJobExecutionException("Could not find system folder ");
        }

        boolean isExists = false;
        for (int i = 0; i < files.length; i++) {
        	
        	if (this.fileNameList != null && this.fileNameList.size() > 0) {
            	boolean exists = this.fileNameList.contains(files[i].getName());
            	if (exists) {
            		isExists = true;
            	}
        	}
        }
        
        // no file
        if (!isExists) {
            throw new UnexpectedJobExecutionException("Could not find file ");
        }
        
        return RepeatStatus.FINISHED;
    }

	public List<String> getFileNameList() {
		return fileNameList;
	}

	public void setFileNameList(List<String> fileNameList) {
		this.fileNameList = fileNameList;
	}

}
