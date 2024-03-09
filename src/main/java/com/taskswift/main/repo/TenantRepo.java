package com.taskswift.main.repo;

import com.taskswift.main.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepo extends JpaRepository<Tenant, Long> {
}
