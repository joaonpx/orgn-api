package br.com.orgn.config;

import br.com.orgn.config.batch.JobCompletionNotificationListener;
import br.com.orgn.config.batch.TransactionFieldSetMapper;
import br.com.orgn.config.batch.TransactionItemProcessor;
import br.com.orgn.entity.Transaction;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Slf4j
@Configuration
@Data
public class BatchConfiguration {

  @Bean
  public PlatformTransactionManager transactionManager(DataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
  }

  @Bean
  public FlatFileItemReader<Transaction> reader() {
    FlatFileItemReader<Transaction> reader = new FlatFileItemReader<>();
    reader.setLineMapper(new DefaultLineMapper<>() {{
      setLineTokenizer(new DelimitedLineTokenizer() {{
        setNames("date", "type", "description", "amount");
        setDelimiter(";");
      }});
      setFieldSetMapper(new TransactionFieldSetMapper());
    }});
    reader.setLinesToSkip(1);
    return reader;
  }

  @Bean
  public TransactionItemProcessor processor() {
    return new TransactionItemProcessor();
  }

  @Bean
  public JdbcBatchItemWriter<Transaction> writer(DataSource dataSource) {
    return new JdbcBatchItemWriterBuilder<Transaction>()
            .sql("INSERT INTO transaction (date, type, description, amount) VALUES (:date, :type, :description, :amount)")
            .dataSource(dataSource)
            .beanMapped()
            .build();
  }

  @Bean
  public Job importUserJob(JobRepository jobRepository, Step firstStep, JobCompletionNotificationListener listener) {
    return new JobBuilder("importUserJob", jobRepository)
            .listener(listener)
            .start(firstStep)
            .build();
  }

  @Bean
  public Step firstStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                        FlatFileItemReader<Transaction> reader, TransactionItemProcessor processor, JdbcBatchItemWriter<Transaction> writer) {
    return new StepBuilder("firstStep", jobRepository)
            .<Transaction, Transaction>chunk(10, transactionManager)
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .build();
  }
}
