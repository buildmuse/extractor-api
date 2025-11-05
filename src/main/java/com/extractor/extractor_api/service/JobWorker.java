package com.extractor.extractor_api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.extractor.extractor_api.model.ExtractJob;

@Component
public class JobWorker {
    private static final Logger log = LoggerFactory.getLogger(JobWorker.class);
    private final ExtractJobProcessor processor;
    private final JobQueueManager queueManager;

    public JobWorker(ExtractJobProcessor processor, JobQueueManager queueManager) {
        this.processor = processor;
        this.queueManager = queueManager;
    }

    @Async("workerExecutor")
    public void startWorker() {
        log.info("Worker started: {}", Thread.currentThread().getName());
        while(true) {
            try {
                ExtractJob job = queueManager.take(); // blocks if empty
                log.info("Worker picked up job {}", job.getJobId());

                processor.processJob(job, () -> {
                    log.info("Job {} processing complete", job.getJobId());
                });
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("Job worker interrupted, shutting down.");
                break;
            } catch (Exception e) {
                log.error("Error processing job: {}", e.getMessage());
            }
        }
    }


}
