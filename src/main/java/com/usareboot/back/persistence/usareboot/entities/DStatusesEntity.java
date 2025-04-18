package com.usareboot.back.persistence.usareboot.entities;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Objects;

@Getter
@Entity
@Table(name = "d_statuses",  catalog = "usareboot")
public class DStatusesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "status_id")
    private long statusId;
    @Basic
    @Column(name = "status_name")
    private String statusName;
    @Basic
    @Column(name = "active")
    private Integer active;
    @Basic
    @Column(name = "status_type")
    private Integer statusType;

    public void setStatusId(long statusId) {
        this.statusId = statusId;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public void setStatusType(Integer statusType) {
        this.statusType = statusType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DStatusesEntity that = (DStatusesEntity) o;
        return statusId == that.statusId && Objects.equals(statusName, that.statusName) && Objects.equals(active, that.active) && Objects.equals(statusType, that.statusType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusId, statusName, active, statusType);
    }
}
