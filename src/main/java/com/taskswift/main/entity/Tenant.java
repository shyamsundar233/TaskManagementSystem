package com.taskswift.main.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tenantId;

    private Long startRange;

    private Long endRange;

    private Long currentUniqueId;

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

    @Override
    public String toString() {
        return "Tenant{" +
                "tenantId=" + tenantId +
                ", startRange=" + startRange +
                ", endRange=" + endRange +
                ", currentUniqueId=" + currentUniqueId +
                '}';
    }
}
