package com.extractor.extractor_api.audit;

import java.time.Instant;

public class AuditService {

    public void log(String action, String message) {
        System.out.printf("[%s] [AUDIT] action=%s message=%s%n",
                Instant.now(), action, message);
    }
}
