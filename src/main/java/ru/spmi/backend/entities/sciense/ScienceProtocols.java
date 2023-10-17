package ru.spmi.backend.entities.sciense;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

public class ScienceProtocols {
    private long scienceProtocolId;
    private long scienceProtocolTypeId;
    private long scienceProtocolReasonId;
    private Long scienceCouncilId;
    private Long scienceDissertationId;
    private Long specId;
    private Long orderId;
    private String numProtocol;
    private Date dateProtocol;
    private short yearNum;
    private short countComission;
    private short countPresents;
    private short countVoiteFor;
    private short countVoiteNotFor;
    private short countVoiteForgo;
    private short countVoiteBad;
    private String presents;
    private String speakers;
    private String approveds;
    private String agenda;
    private String listened;
    private String resolution;
    private String modifyUser;
    private Timestamp modifyDate;
    private Short active;

    public long getScienceProtocolId() {
        return scienceProtocolId;
    }

    public void setScienceProtocolId(long scienceProtocolId) {
        this.scienceProtocolId = scienceProtocolId;
    }

    public long getScienceProtocolTypeId() {
        return scienceProtocolTypeId;
    }

    public void setScienceProtocolTypeId(long scienceProtocolTypeId) {
        this.scienceProtocolTypeId = scienceProtocolTypeId;
    }

    public long getScienceProtocolReasonId() {
        return scienceProtocolReasonId;
    }

    public void setScienceProtocolReasonId(long scienceProtocolReasonId) {
        this.scienceProtocolReasonId = scienceProtocolReasonId;
    }

    public Long getScienceCouncilId() {
        return scienceCouncilId;
    }

    public void setScienceCouncilId(Long scienceCouncilId) {
        this.scienceCouncilId = scienceCouncilId;
    }

    public Long getScienceDissertationId() {
        return scienceDissertationId;
    }

    public void setScienceDissertationId(Long scienceDissertationId) {
        this.scienceDissertationId = scienceDissertationId;
    }

    public Long getSpecId() {
        return specId;
    }

    public void setSpecId(Long specId) {
        this.specId = specId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getNumProtocol() {
        return numProtocol;
    }

    public void setNumProtocol(String numProtocol) {
        this.numProtocol = numProtocol;
    }

    public Date getDateProtocol() {
        return dateProtocol;
    }

    public void setDateProtocol(Date dateProtocol) {
        this.dateProtocol = dateProtocol;
    }

    public short getYearNum() {
        return yearNum;
    }

    public void setYearNum(short yearNum) {
        this.yearNum = yearNum;
    }

    public short getCountComission() {
        return countComission;
    }

    public void setCountComission(short countComission) {
        this.countComission = countComission;
    }

    public short getCountPresents() {
        return countPresents;
    }

    public void setCountPresents(short countPresents) {
        this.countPresents = countPresents;
    }

    public short getCountVoiteFor() {
        return countVoiteFor;
    }

    public void setCountVoiteFor(short countVoiteFor) {
        this.countVoiteFor = countVoiteFor;
    }

    public short getCountVoiteNotFor() {
        return countVoiteNotFor;
    }

    public void setCountVoiteNotFor(short countVoiteNotFor) {
        this.countVoiteNotFor = countVoiteNotFor;
    }

    public short getCountVoiteForgo() {
        return countVoiteForgo;
    }

    public void setCountVoiteForgo(short countVoiteForgo) {
        this.countVoiteForgo = countVoiteForgo;
    }

    public short getCountVoiteBad() {
        return countVoiteBad;
    }

    public void setCountVoiteBad(short countVoiteBad) {
        this.countVoiteBad = countVoiteBad;
    }

    public String getPresents() {
        return presents;
    }

    public void setPresents(String presents) {
        this.presents = presents;
    }

    public String getSpeakers() {
        return speakers;
    }

    public void setSpeakers(String speakers) {
        this.speakers = speakers;
    }

    public String getApproveds() {
        return approveds;
    }

    public void setApproveds(String approveds) {
        this.approveds = approveds;
    }

    public String getAgenda() {
        return agenda;
    }

    public void setAgenda(String agenda) {
        this.agenda = agenda;
    }

    public String getListened() {
        return listened;
    }

    public void setListened(String listened) {
        this.listened = listened;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public Timestamp getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Timestamp modifyDate) {
        this.modifyDate = modifyDate;
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
        ScienceProtocols that = (ScienceProtocols) o;
        return scienceProtocolId == that.scienceProtocolId && scienceProtocolTypeId == that.scienceProtocolTypeId && scienceProtocolReasonId == that.scienceProtocolReasonId && yearNum == that.yearNum && countComission == that.countComission && countPresents == that.countPresents && countVoiteFor == that.countVoiteFor && countVoiteNotFor == that.countVoiteNotFor && countVoiteForgo == that.countVoiteForgo && countVoiteBad == that.countVoiteBad && Objects.equals(scienceCouncilId, that.scienceCouncilId) && Objects.equals(scienceDissertationId, that.scienceDissertationId) && Objects.equals(specId, that.specId) && Objects.equals(orderId, that.orderId) && Objects.equals(numProtocol, that.numProtocol) && Objects.equals(dateProtocol, that.dateProtocol) && Objects.equals(presents, that.presents) && Objects.equals(speakers, that.speakers) && Objects.equals(approveds, that.approveds) && Objects.equals(agenda, that.agenda) && Objects.equals(listened, that.listened) && Objects.equals(resolution, that.resolution) && Objects.equals(modifyUser, that.modifyUser) && Objects.equals(modifyDate, that.modifyDate) && Objects.equals(active, that.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scienceProtocolId, scienceProtocolTypeId, scienceProtocolReasonId, scienceCouncilId, scienceDissertationId, specId, orderId, numProtocol, dateProtocol, yearNum, countComission, countPresents, countVoiteFor, countVoiteNotFor, countVoiteForgo, countVoiteBad, presents, speakers, approveds, agenda, listened, resolution, modifyUser, modifyDate, active);
    }
}
