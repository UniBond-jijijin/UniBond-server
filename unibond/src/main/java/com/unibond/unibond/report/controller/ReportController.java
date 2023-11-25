package com.unibond.unibond.report.controller;

import com.unibond.unibond.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReportController {
    private ReportService reportService;
}
