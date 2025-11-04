package com.extractor.extractor_api.extractor;

public interface Extractor {
   boolean supports(String source);
   String extract(String source); 
}
