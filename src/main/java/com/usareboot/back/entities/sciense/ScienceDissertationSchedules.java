package com.usareboot.back.entities.sciense;

import java.sql.Date;
import java.util.Objects;

public class ScienceDissertationSchedules {
    private long scienceDissertationScheduleId;
    private long scienceDissertationId;
    private long scienceScheduleTypeId;
    private Date datePlan;
    private Date dateFact;

    public long getScienceDissertationScheduleId() {
        return scienceDissertationScheduleId;
    }

    public void setScienceDissertationScheduleId(long scienceDissertationScheduleId) {
        this.scienceDissertationScheduleId = scienceDissertationScheduleId;
    }

    public long getScienceDissertationId() {
        return scienceDissertationId;
    }

    public void setScienceDissertationId(long scienceDissertationId) {
        this.scienceDissertationId = scienceDissertationId;
    }

    public long getScienceScheduleTypeId() {
        return scienceScheduleTypeId;
    }

    public void setScienceScheduleTypeId(long scienceScheduleTypeId) {
        this.scienceScheduleTypeId = scienceScheduleTypeId;
    }

    public Date getDatePlan() {
        return datePlan;
    }

    public void setDatePlan(Date datePlan) {
        this.datePlan = datePlan;
    }

    public Date getDateFact() {
        return dateFact;
    }

    public void setDateFact(Date dateFact) {
        this.dateFact = dateFact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScienceDissertationSchedules that = (ScienceDissertationSchedules) o;
        return scienceDissertationScheduleId == that.scienceDissertationScheduleId && scienceDissertationId == that.scienceDissertationId && scienceScheduleTypeId == that.scienceScheduleTypeId && Objects.equals(datePlan, that.datePlan) && Objects.equals(dateFact, that.dateFact);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scienceDissertationScheduleId, scienceDissertationId, scienceScheduleTypeId, datePlan, dateFact);
    }
}
