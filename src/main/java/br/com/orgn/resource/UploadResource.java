package br.com.orgn.resource;

import br.com.orgn.config.BatchConfiguration;
import br.com.orgn.entity.Transaction;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
@CrossOrigin(origins = "http://localhost:5173")
public class UploadResource {

  @Autowired
  private JobLauncher jobLauncher;

  @Autowired
  private Job importUserJob;

  @Autowired
  private BatchConfiguration batchConfiguration;

  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  public void upload(@RequestParam("file") MultipartFile file) throws Exception {
    Resource resource = new ByteArrayResource(file.getBytes());

    FlatFileItemReader<Transaction> reader = batchConfiguration.reader();
    reader.setResource(resource);

    jobLauncher.run(importUserJob, new JobParametersBuilder()
            .addLong("startAt", System.currentTimeMillis())
            .toJobParameters());
  }
}
