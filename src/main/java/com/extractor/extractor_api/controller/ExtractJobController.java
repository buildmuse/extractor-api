package com.extractor.extractor_api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.extractor.extractor_api.model.CreateExtractJobRequest;
import com.extractor.extractor_api.model.ExtractJob;
import com.extractor.extractor_api.service.ExtractJobService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/jobs")
public class ExtractJobController {

    private final ExtractJobService extractJobService;

    public ExtractJobController(ExtractJobService extractJobService) {
        this.extractJobService = extractJobService;
    }

    @PostMapping
    public ResponseEntity<ExtractJob> create(@RequestBody CreateExtractJobRequest request) {
        ExtractJob job = extractJobService.createJob(request);
        return ResponseEntity.ok(job);
    }

    @GetMapping
    public ResponseEntity<List<ExtractJob>> getAll() {
        return ResponseEntity.ok(extractJobService.getAllJobs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExtractJob> getOne(@PathVariable long id) {
        return extractJobService.getJobById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
