package com.extractor.extractor_api.service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.stereotype.Component;

import com.extractor.extractor_api.model.ExtractJob;

@Component
public class JobQueueManager {
    private final BlockingQueue<ExtractJob> jobQueue = new LinkedBlockingQueue<>(100); 

    public void enqueueJob(ExtractJob job) throws InterruptedException {
        jobQueue.put(job);
    }   

     public ExtractJob take() throws InterruptedException {
        return jobQueue.take(); // blocks if empty
    }

     public int size() {
        return jobQueue.size();
    }
}
