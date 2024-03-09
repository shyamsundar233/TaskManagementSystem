package com.taskswift.main.service;

import com.taskswift.main.entity.Tenant;

public interface TenantService {

    public void addTenant(Tenant tenant);

    public Long getTenantCount();

    public Tenant getNextActiveTenant();

}
