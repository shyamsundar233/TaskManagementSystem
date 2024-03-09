package com.taskswift.main.service;

import com.taskswift.main.dao.TenantRangesDao;
import com.taskswift.main.entity.TenantRanges;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TenantRangesServiceImpl implements TenantRangesService{

    @Autowired
    private TenantRangesDao tenantRangesDao;

    @Override
    public void addTenantRanges(TenantRanges tenantRanges) {
        tenantRangesDao.addTenantRanges(tenantRanges);
    }

    @Override
    public Long getTenantRangesCount() {
        return tenantRangesDao.getTenantRangesCount();
    }
}
