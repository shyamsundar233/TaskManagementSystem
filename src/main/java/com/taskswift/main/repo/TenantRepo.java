package com.taskswift.main.repo;

import com.taskswift.main.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TenantRepo extends JpaRepository<Tenant, Long> {
    Optional<Tenant> findFirstByIsActiveTrue();
}
