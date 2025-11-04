package com.extractor.extractor_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test-jobs")
    public String test() {
        return "TEST OK";
    }
}