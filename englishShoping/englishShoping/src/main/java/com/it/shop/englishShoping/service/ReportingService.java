package com.it.shop.englishShoping.service;

import com.it.shop.englishShoping.domain.Reporting;

import java.util.List;

public interface ReportingService {
    List<Reporting> findByAll();

    String deleteById(String id);

    String add(Reporting reporting);

    String updateByStatus(Reporting reporting);
}
