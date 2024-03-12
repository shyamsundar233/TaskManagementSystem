package com.taskswift.main.entity;

import jakarta.persistence.*;

@Entity
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tenantId;

    private Long startRange;

    private Long endRange;

    private Long currentUniqueId;

    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean isActive;

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
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

    public Long getCurrentUniqueId() {
        return currentUniqueId;
    }

    public void setCurrentUniqueId(Long currentUniqueId) {
        this.currentUniqueId = currentUniqueId;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "Tenant{" +
                "tenantId=" + tenantId +
                ", startRange=" + startRange +
                ", endRange=" + endRange +
                ", currentUniqueId=" + currentUniqueId +
                ", isActive=" + isActive +
                '}';
    }
}
