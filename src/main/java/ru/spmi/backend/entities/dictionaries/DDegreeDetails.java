package ru.spmi.backend.entities.dictionaries;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "d_degree_details", schema = "public", catalog = "university")
public class DDegreeDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "degree_detail_id", nullable = false)
    private long degreeDetailId;
    @Column(name = "degree_detail_code")
    private String degreeDetailCode;
    @Column(name = "degree_detail_short")
    private String degreeDetailShort;
    @Column(name = "degree_detail_name")
    private String degreeDetailName;
    @Column(name = "science")
    private String science;
    @Column(name = "science_short")
    private String scienceShort;
    @Column(name = "science_eng")
    private String scienceEng;
    @Column(name = "active")
    private Short active;

    public long getDegreeDetailId() {
        return degreeDetailId;
    }

    public void setDegreeDetailId(long degreeDetailId) {
        this.degreeDetailId = degreeDetailId;
    }

    public String getDegreeDetailCode() {
        return degreeDetailCode;
    }

    public void setDegreeDetailCode(String degreeDetailCode) {
        this.degreeDetailCode = degreeDetailCode;
    }

    public String getDegreeDetailShort() {
        return degreeDetailShort;
    }

    public void setDegreeDetailShort(String degreeDetailShort) {
        this.degreeDetailShort = degreeDetailShort;
    }

    public String getDegreeDetailName() {
        return degreeDetailName;
    }

    public void setDegreeDetailName(String degreeDetailName) {
        this.degreeDetailName = degreeDetailName;
    }

    public String getScience() {
        return science;
    }

    public void setScience(String science) {
        this.science = science;
    }

    public String getScienceShort() {
        return scienceShort;
    }

    public void setScienceShort(String scienceShort) {
        this.scienceShort = scienceShort;
    }

    public String getScienceEng() {
        return scienceEng;
    }

    public void setScienceEng(String scienceEng) {
        this.scienceEng = scienceEng;
    }

    public Short getActive() {
        return active;
    }

    public void setActive(Short active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DDegreeDetails that = (DDegreeDetails) o;
        return degreeDetailId == that.degreeDetailId && Objects.equals(degreeDetailCode, that.degreeDetailCode) && Objects.equals(degreeDetailShort, that.degreeDetailShort) && Objects.equals(degreeDetailName, that.degreeDetailName) && Objects.equals(science, that.science) && Objects.equals(scienceShort, that.scienceShort) && Objects.equals(scienceEng, that.scienceEng) && Objects.equals(active, that.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(degreeDetailId, degreeDetailCode, degreeDetailShort, degreeDetailName, science, scienceShort, scienceEng, active);
    }
}
