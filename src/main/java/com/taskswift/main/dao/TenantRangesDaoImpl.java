package com.taskswift.main.dao;

import com.taskswift.main.entity.TenantRanges;
import com.taskswift.main.repo.TenantRangesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TenantRangesDaoImpl implements TenantRangesDao{

    @Autowired
    private TenantRangesRepo tenantRangesRepo;

    @Override
    public void addTenantRanges(TenantRanges tenantRanges) {
        tenantRangesRepo.save(tenantRanges);
    }

    @Override
    public Long getTenantRangesCount() {
        return tenantRangesRepo.count();
    }

    @Override
    public Long getNextTenantId() {
        return tenantRangesRepo.findFirstByIsActiveTrue().getRangeId();
    }
}
