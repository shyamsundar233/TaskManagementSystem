package com.taskswift.main.repo;

import com.taskswift.main.entity.TaskCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskCategoryRepo extends JpaRepository<TaskCategory, Long> {

//    public TaskCategory findByCategoryTitleBetween(String categoryTitle, Long startRange, Long endRange);

    @Query("SELECT tc FROM TaskCategory tc WHERE tc.categoryTitle = :title AND tc.categoryId BETWEEN :startRange AND :endRange")
    TaskCategory findByCategoryTitleAndCategoryIdRange(@Param("title") String title, @Param("startRange") Long startRange, @Param("endRange") Long endRange);

    public List<TaskCategory> findAllByCategoryIdIsBetween(Long startRange, Long endRange);

}
