package com.extractor.extractor_api.service;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.extractor.extractor_api.audit.AuditService;
import com.extractor.extractor_api.config.ExtractorProperties;
import com.extractor.extractor_api.model.CreateExtractJobRequest;
import com.extractor.extractor_api.model.ExtractJob;

@Service
public class ExtractJobService {

    private final ExtractorProperties extractorProperties;
    private final AuditService auditService;
    private final AtomicLong jobId = new AtomicLong(1);
    private final ConcurrentMap<Long, ExtractJob> jobStore = new ConcurrentHashMap<>();

    public ExtractJobService(ExtractorProperties extractorProperties, AuditService auditService) {
        this.extractorProperties = extractorProperties;
        this.auditService = auditService;
    }

    public ExtractJob createJob(CreateExtractJobRequest request) {
        long id = jobId.getAndIncrement();

        ExtractJob job = ExtractJob.builder().jobId(id).Source(request.getSource()).status(extractorProperties.getDefaultStatus()).createdAt(Instant.now()).completedAt(null).build();
        auditService.log("CREATE_JOB", "Job created for source=" + request.getSource());

        jobStore.put(id, job);
        return job;
    }

    public List<ExtractJob> getAllJobs() {
        return new ArrayList<>(jobStore.values());
    }

    public Optional<ExtractJob> getJobById(long id) {
        return Optional.ofNullable(jobStore.get(id));
    }
}
