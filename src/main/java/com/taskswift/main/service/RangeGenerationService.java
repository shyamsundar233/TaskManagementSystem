package com.taskswift.main.service;

import com.taskswift.main.entity.Tenant;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RangeGenerationService {

    @Autowired
    private TenantService tenantService;

    @Transactional
    public void generateAndInsertRanges() {
        if(tenantService.getTenantCount() == 0) {
            long startRange = 1;
            long endRange = 5000000;
            long rangeSize = 100000;

            while (startRange < endRange && startRange + rangeSize <= endRange) {
                Tenant tenant = new Tenant();
                tenant.setStartRange(startRange);
                tenant.setEndRange(startRange + rangeSize);
                tenantService.addTenant(tenant);
                startRange = startRange + rangeSize;
            }
        }
    }
}
