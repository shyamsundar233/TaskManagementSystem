package com.taskswift.main.entity;

import jakarta.persistence.*;

@Entity
public class TenantRanges {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rangeId;

    private Long startRange;

    private Long endRange;

    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean isActive;

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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "TenantRanges{" +
                "rangeId=" + rangeId +
                ", startRange=" + startRange +
                ", endRange=" + endRange +
                ", isActive=" + isActive +
                '}';
    }
}
