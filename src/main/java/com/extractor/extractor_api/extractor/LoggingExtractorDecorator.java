package com.extractor.extractor_api.extractor;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class LoggingExtractorDecorator implements Extractor {
    private static final Logger log = LoggerFactory.getLogger(LoggingExtractorDecorator.class);
    private final List<Extractor> extractors;

    public LoggingExtractorDecorator(List<Extractor> extractors) {
        this.extractors = extractors;
    }


    @Override
    public boolean supports(String source) {
        return true;
    }

    @Override
    public String extract(String source) {
       Extractor delegate = extractors.stream().filter(e -> !(e instanceof LoggingExtractorDecorator)).filter(e-> e.supports(source)).findFirst()
                .orElseThrow(() -> new RuntimeException("No extractor found for " + source));

        long start = System.currentTimeMillis();
        log.info("Starting extraction with {}", delegate.getClass().getSimpleName());
        String result = delegate.extract(source);
        log.info("Completed extraction in {} ms", System.currentTimeMillis() - start);

        return result;
    }
    
}
