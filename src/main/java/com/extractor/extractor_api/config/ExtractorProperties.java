package com.extractor.extractor_api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "extractor")
@Data
public class ExtractorProperties {
    private String defaultStatus = "PENDING";
}
