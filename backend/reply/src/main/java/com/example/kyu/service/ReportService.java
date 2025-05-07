package com.example.kyu.service;

import org.springframework.stereotype.Service;

import com.example.kyu.repository.ReportRepository;

@Service
public class ReportService {
    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

}
