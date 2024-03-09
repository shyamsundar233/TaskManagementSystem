package com.taskswift.main.service;

import com.taskswift.main.dao.TenantDao;
import com.taskswift.main.entity.Tenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TenantServiceImpl implements TenantService{

    @Autowired
    private TenantDao tenantDao;

    @Override
    public void addTenant(Tenant tenant) {
        tenantDao.addTenant(tenant);
    }

    @Override
    public Long getTenantCount() {
        return tenantDao.getTenantCount();
    }

    @Override
    public Tenant getNextActiveTenant() {
        return tenantDao.getNextActiveTenant();
    }
}
