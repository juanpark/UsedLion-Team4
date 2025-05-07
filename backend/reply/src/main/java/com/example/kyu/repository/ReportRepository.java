package com.example.kyu.repository;

import java.util.List;

import com.example.kyu.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Integer> {

    List<Report> findByTargetId(Integer targetId);
}
