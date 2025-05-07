package com.example.post.service;

import org.springframework.stereotype.Service;

import com.example.post.repository.ReportRepository;

@Service
public class ReportService {
    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

}
