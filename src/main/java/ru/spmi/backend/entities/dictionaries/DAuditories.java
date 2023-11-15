package ru.spmi.backend.entities.dictionaries;

import jakarta.persistence.*;

import java.util.Objects;
@Entity
@Table(name = "d_auditories", schema = "public", catalog = "university")
public class DAuditories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auditory_id", nullable = false)
    private long auditoryId;
    @Column(name = "education_center_id")
    private long educationCenterId;
    @Column(name = "building_id")
    private long buildingId;
    @Column(name = "auditory_type_id")
    private long auditoryTypeId;
    @Column(name = "auditory_code")
    private String auditoryCode;
    @Column(name = "auditory_name")
    private String auditoryName;
    @Column(name = "active")
    private int active;

    public long getAuditoryId() {
        return auditoryId;
    }

    public void setAuditoryId(long auditoryId) {
        this.auditoryId = auditoryId;
    }

    public long getEducationCenterId() {
        return educationCenterId;
    }

    public void setEducationCenterId(long educationCenterId) {
        this.educationCenterId = educationCenterId;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public Long getAuditoryTypeId() {
        return auditoryTypeId;
    }

    public void setAuditoryTypeId(Long auditoryTypeId) {
        this.auditoryTypeId = auditoryTypeId;
    }

    public String getAuditoryCode() {
        return auditoryCode;
    }

    public void setAuditoryCode(String auditoryCode) {
        this.auditoryCode = auditoryCode;
    }

    public String getAuditoryName() {
        return auditoryName;
    }

    public void setAuditoryName(String auditoryName) {
        this.auditoryName = auditoryName;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DAuditories that = (DAuditories) o;
        return auditoryId == that.auditoryId && educationCenterId == that.educationCenterId && active == that.active && Objects.equals(buildingId, that.buildingId) && Objects.equals(auditoryTypeId, that.auditoryTypeId) && Objects.equals(auditoryCode, that.auditoryCode) && Objects.equals(auditoryName, that.auditoryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(auditoryId, educationCenterId, buildingId, auditoryTypeId, auditoryCode, auditoryName, active);
    }
}
