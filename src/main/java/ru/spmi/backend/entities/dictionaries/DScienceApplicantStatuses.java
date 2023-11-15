package ru.spmi.backend.entities.dictionaries;

import jakarta.persistence.*;

import java.util.Objects;
@Entity
@Table(name = "d_science_applicant_statuses", schema = "public", catalog = "university")
public class DScienceApplicantStatuses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "science_applicant_status_id", nullable = false)
    private long scienceApplicantStatusId;
    @Column(name = "status_short")
    private String statusShort;
    @Column(name = "status_name")
    private String statusName;
    @Column(name = "active")
    private short active;

    public long getScienceApplicantStatusId() {
        return scienceApplicantStatusId;
    }

    public void setScienceApplicantStatusId(long scienceApplicantStatusId) {
        this.scienceApplicantStatusId = scienceApplicantStatusId;
    }

    public String getStatusShort() {
        return statusShort;
    }

    public void setStatusShort(String statusShort) {
        this.statusShort = statusShort;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public short getActive() {
        return active;
    }

    public void setActive(short active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DScienceApplicantStatuses that = (DScienceApplicantStatuses) o;
        return scienceApplicantStatusId == that.scienceApplicantStatusId && active == that.active && Objects.equals(statusShort, that.statusShort) && Objects.equals(statusName, that.statusName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scienceApplicantStatusId, statusShort, statusName, active);
    }
}
