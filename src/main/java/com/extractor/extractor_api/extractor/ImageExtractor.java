package com.extractor.extractor_api.extractor;

import org.springframework.stereotype.Component;

@Component
public class ImageExtractor implements Extractor {

    @Override
    public boolean supports(String source) {
        return source != null && (source.endsWith(".jpg") || source.endsWith(".png") || source.endsWith(".gif"));
    }

    @Override
    public String extract(String source) {
        // Simulate image extraction logic
        return "Extracted image data from " + source;
    }
    
}
