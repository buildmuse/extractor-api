package com.extractor.extractor_api.extractor;

import org.springframework.stereotype.Component;

@Component
public class PdfExtractor implements Extractor {
    @Override
    public boolean supports(String source) {
        return source != null && source.toLowerCase().endsWith(".pdf");
    }

    @Override
    public String extract(String source) {
        // Dummy implementation for PDF extraction
        return "Extracted content from PDF: " + source;
    }
    
}
