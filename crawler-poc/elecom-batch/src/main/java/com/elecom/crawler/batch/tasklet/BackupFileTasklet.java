package com.elecom.crawler.batch.tasklet;

import java.io.File;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.UnexpectedJobExecutionException;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;

import com.google.common.io.Files;


public class BackupFileTasklet extends BaseFileTasklet {

    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        File dir = directory.getFile();
        File[] files = dir.listFiles();

        // no file
        if (files == null) {
            throw new UnexpectedJobExecutionException("Could not find system folder ");
        }

        for (int i = 0; i < files.length; i++) {
            boolean exists = "crawler_job.csv".equals(files[i].getName());
            // no userMaster.csv file
            if (exists) {
                Files.move(files[i], new File("C:/PMI/Workspace-1/pmi-batch/fileback/crawler_job_bak.csv"));
            }
        }
        return RepeatStatus.FINISHED;
    }
}
