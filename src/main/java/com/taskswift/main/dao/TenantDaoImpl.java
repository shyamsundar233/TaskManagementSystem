package com.taskswift.main.dao;

import com.taskswift.main.entity.Tenant;
import com.taskswift.main.repo.TenantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TenantDaoImpl implements TenantDao{

    @Autowired
    private TenantRepo tenantRepo;

    @Override
    public void addTenant(Tenant tenant) {
        tenantRepo.save(tenant);
    }

    @Override
    public Long getTenantCount() {
        return tenantRepo.count();
    }

    @Override
    public Tenant getNextActiveTenant() {
        return tenantRepo.findFirstByIsActiveTrue();
    }
}
