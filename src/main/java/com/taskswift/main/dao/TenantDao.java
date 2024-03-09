package com.taskswift.main.dao;

import com.taskswift.main.entity.Tenant;

public interface TenantDao {

    public void addTenant(Tenant tenant);

    public Long getTenantCount();

    public Tenant getNextActiveTenant();

}
