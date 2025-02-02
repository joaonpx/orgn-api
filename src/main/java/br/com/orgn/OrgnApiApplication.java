package br.com.orgn;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class OrgnApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(OrgnApiApplication.class, args);
  }

}
