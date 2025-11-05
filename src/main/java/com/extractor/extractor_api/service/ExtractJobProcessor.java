package com.extractor.extractor_api.service;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.extractor.extractor_api.model.ExtractJob;

@Component
public class ExtractJobProcessor {
    private static final Logger log = LoggerFactory.getLogger(ExtractJobProcessor.class);

    public void processJob(ExtractJob job, Runnable onComplete) {
        try {
            log.info("Started processing job");

            Thread.sleep(3000);
            
            job.setStatus("COMPLETED");
            job.setCompletedAt(Instant.now());

            if(onComplete != null) {
                onComplete.run();
            }

            log.info("Finished processing job ");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Job {} was interrupted", job.getJobId());
            job.setStatus("FAILED");
        } catch (Exception e) {
            log.error("Job {} failed: {}", job.getJobId(), e.getMessage());
            job.setStatus("FAILED");
        }
    }
}
