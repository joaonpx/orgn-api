package br.com.orgn.config.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JobCompletionNotificationListener implements JobExecutionListener {

  @Override
  public void afterJob(JobExecution jobExecution) {
    if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
      log.info("c=JobCompletionNotificationListener m=afterJob msg=JOB COMPLETED!");
    }
  }

  @Override
  public void beforeJob(JobExecution jobExecution) {
    log.info("c=JobCompletionNotificationListener m=beforeJob msg=JOB STARTING!");
  }
}
