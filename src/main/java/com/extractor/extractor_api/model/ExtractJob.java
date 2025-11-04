package com.extractor.extractor_api.model;

import java.time.Instant;

import lombok.Data;

@Data
public class ExtractJob {
    private long jobId;
    private String status;
    private Instant createdAt;
    private Instant completedAt;

}