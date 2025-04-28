package com.usareboot.back.persistence.usareboot.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
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
    @Basic
    @Column(name = "sort")
    private Integer sort;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DStatusesEntity that = (DStatusesEntity) o;
        return statusId == that.statusId && Objects.equals(statusName, that.statusName) && Objects.equals(active, that.active) && Objects.equals(statusType, that.statusType) && Objects.equals(sort, that.sort);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusId, statusName, active, statusType,sort);
    }
}
