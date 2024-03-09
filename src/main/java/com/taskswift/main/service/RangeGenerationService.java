package com.taskswift.main.service;

import com.taskswift.main.entity.TenantRanges;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RangeGenerationService {

    @Autowired
    private TenantRangesService tenantRangesService;

    @Transactional
    public void generateAndInsertRanges() {
        if(tenantRangesService.getTenantRangesCount() == 0) {
            long startRange = 1;
            long endRange = 5000000;
            long rangeSize = 100000;

            while (startRange < endRange && startRange + rangeSize <= endRange) {
                TenantRanges tenantRanges = new TenantRanges();
                tenantRanges.setStartRange(startRange);
                tenantRanges.setEndRange(startRange + rangeSize);
                tenantRangesService.addTenantRanges(tenantRanges);
                startRange = startRange + rangeSize;
            }
        }
    }
}
