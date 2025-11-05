package com.extractor.extractor_api.service;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.extractor.extractor_api.audit.AuditService;
import com.extractor.extractor_api.config.ExtractorProperties;
import com.extractor.extractor_api.extractor.Extractor;
import com.extractor.extractor_api.model.CreateExtractJobRequest;
import com.extractor.extractor_api.model.ExtractJob;

@Service
public class ExtractJobService {

    private final Extractor extractor;
    //example for auto config prop
    private final ExtractorProperties extractorProperties;
    private final JobQueueManager queueManager;
    private final JobWorker worker;
    private final AuditService auditService;
    private final AtomicLong jobId = new AtomicLong(1);
    private final ConcurrentMap<Long, ExtractJob> jobStore = new ConcurrentHashMap<>();

    public ExtractJobService(ExtractorProperties extractorProperties,
     AuditService auditService,
      Extractor extractor,
       ExtractJobProcessor processor,
        JobQueueManager queueManager,
        JobWorker worker) {
        this.extractorProperties = extractorProperties;
        this.auditService = auditService;
        this.extractor = extractor;
        //this.processor = processor;
        this.queueManager = queueManager;
        this.worker = worker;
        worker.startWorker(); 
    }

    public ExtractJob createJob(CreateExtractJobRequest request) {
        long id = jobId.getAndIncrement();

        ExtractJob job = ExtractJob.builder().jobId(id).Source(request.getSource()).status(extractorProperties.getDefaultStatus()).createdAt(Instant.now()).completedAt(null).build();
        auditService.log("CREATE_JOB", "Job created for source=" + request.getSource());

        //example for adaptor pattern, decorator pattern
        String result = extractor.extract(request.getSource());
        System.out.println(result);

        jobStore.put(id, job);


        // processor.processJob(job, () -> {
        //     // optional: if you wanted to persist or send event
        //     // here we already updated the job object, and store has reference
        // });

        try {
            queueManager.enqueueJob(job);
        } catch (Exception e) {
             Thread.currentThread().interrupt();
            job.setStatus("FAILED");
        }

        return job;
    }

    public List<ExtractJob> getAllJobs() {
        return new ArrayList<>(jobStore.values());
    }

    public Optional<ExtractJob> getJobById(long id) {
        return Optional.ofNullable(jobStore.get(id));
    }
}
