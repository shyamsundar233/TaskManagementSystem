package com.taskswift.main.dao;

import com.taskswift.main.entity.Tenant;
import com.taskswift.main.repo.TenantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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
        Optional<Tenant> tenant = tenantRepo.findFirstByIsActiveTrue();
        return tenant.orElse(null);
    }

    @Override
    public Tenant getTenantById(Long tenantId) {
        Optional<Tenant> tenant = tenantRepo.findById(tenantId);
        return tenant.orElse(null);
    }
}
