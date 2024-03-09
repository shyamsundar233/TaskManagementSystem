package com.taskswift.main.repo;

import com.taskswift.main.entity.TenantRanges;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRangesRepo extends JpaRepository<TenantRanges, Long> {
}
