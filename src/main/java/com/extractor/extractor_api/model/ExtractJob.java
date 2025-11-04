package com.extractor.extractor_api.model;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExtractJob {
    private long jobId;
    private String Source;
    private String status;
    private Instant createdAt;
    private Instant completedAt;

}