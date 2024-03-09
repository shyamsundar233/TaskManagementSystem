package com.taskswift.main.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class TenantRanges {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rangeId;

    private Long startRange;

    private Long endRange;

    public Long getRangeId() {
        return rangeId;
    }

    public void setRangeId(Long rangeId) {
        this.rangeId = rangeId;
    }

    public Long getStartRange() {
        return startRange;
    }

    public void setStartRange(Long startRange) {
        this.startRange = startRange;
    }

    public Long getEndRange() {
        return endRange;
    }

    public void setEndRange(Long endRange) {
        this.endRange = endRange;
    }

    @Override
    public String toString() {
        return "TenantRanges{" +
                "rangeId=" + rangeId +
                ", startRange=" + startRange +
                ", endRange=" + endRange +
                '}';
    }
}
