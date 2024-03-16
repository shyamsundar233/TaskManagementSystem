package com.taskswift.main.repo;

import com.taskswift.main.entity.StatusDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusDetailsRepo extends JpaRepository<StatusDetails, Long> {
}
