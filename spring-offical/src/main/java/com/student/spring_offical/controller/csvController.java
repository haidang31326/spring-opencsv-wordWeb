package com.student.spring_offical.controller;

import com.student.spring_offical.service.CsvProcessingService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/csv")
public class csvController {
    private final CsvProcessingService csvService;

    public csvController(CsvProcessingService csvService) {
        this.csvService = csvService;
    }
}
