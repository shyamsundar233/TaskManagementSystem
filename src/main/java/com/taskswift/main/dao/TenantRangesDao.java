package com.taskswift.main.dao;

import com.taskswift.main.entity.Tenant;
import com.taskswift.main.entity.TenantRanges;

public interface TenantRangesDao {

    public void addTenantRanges(TenantRanges tenantRanges);

    public Long getTenantRangesCount();

    public Long getNextTenantId();

}
