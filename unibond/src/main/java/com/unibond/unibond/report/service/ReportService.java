package com.unibond.unibond.report.service;

import com.unibond.unibond.report.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {
    private ReportRepository reportRepository;
}
