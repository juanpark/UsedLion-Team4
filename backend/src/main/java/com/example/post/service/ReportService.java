package com.example.post.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.post.entity.Report;
import com.example.post.repository.ReportRepository;

@Service
public class ReportService {
    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public void createReport(Report report) {
        reportRepository.save(report);
    }

    public List<Report> getByuserId(Integer userId) {

        return reportRepository.findByTargetId(userId);
    }

}
