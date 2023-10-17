package ru.spmi.backend.entities.sciense;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Objects;

public class ScienceDissertations {
    private long scienceDissertationId;
    private long personId;
    private Long employeePositionId;
    private Long scienceCouncilSpecId;
    private Long degreeDetailId;
    private Long scienceApplicantStatusId;
    private Long scienceDissertationStatusId;
    private Long auditoryId;
    private Long orderId;
    private Long specId;
    private String fNameEng;
    private String iNameEng;
    private String oNameEng;
    private String contacts;
    private String workPlace;
    private String workPosition;
    private Timestamp dateAdded;
    private Date dateDocument;
    private Date dateAdmission;
    private Date dateDefense;
    private Time timeDefense;
    private String theme;
    private String themeEng;
    private String partText;
    private String keyword;
    private String keywordEng;
    private String urlVak;
    private String urlApplicant;
    private String qrCode;
    private String certificationCase;
    private Date certificationDate;
    private String comment;
    private short active;

    public long getScienceDissertationId() {
        return scienceDissertationId;
    }

    public void setScienceDissertationId(long scienceDissertationId) {
        this.scienceDissertationId = scienceDissertationId;
    }

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    public Long getEmployeePositionId() {
        return employeePositionId;
    }

    public void setEmployeePositionId(Long employeePositionId) {
        this.employeePositionId = employeePositionId;
    }

    public Long getScienceCouncilSpecId() {
        return scienceCouncilSpecId;
    }

    public void setScienceCouncilSpecId(Long scienceCouncilSpecId) {
        this.scienceCouncilSpecId = scienceCouncilSpecId;
    }

    public Long getDegreeDetailId() {
        return degreeDetailId;
    }

    public void setDegreeDetailId(Long degreeDetailId) {
        this.degreeDetailId = degreeDetailId;
    }

    public Long getScienceApplicantStatusId() {
        return scienceApplicantStatusId;
    }

    public void setScienceApplicantStatusId(Long scienceApplicantStatusId) {
        this.scienceApplicantStatusId = scienceApplicantStatusId;
    }

    public Long getScienceDissertationStatusId() {
        return scienceDissertationStatusId;
    }

    public void setScienceDissertationStatusId(Long scienceDissertationStatusId) {
        this.scienceDissertationStatusId = scienceDissertationStatusId;
    }

    public Long getAuditoryId() {
        return auditoryId;
    }

    public void setAuditoryId(Long auditoryId) {
        this.auditoryId = auditoryId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getSpecId() {
        return specId;
    }

    public void setSpecId(Long specId) {
        this.specId = specId;
    }

    public String getfNameEng() {
        return fNameEng;
    }

    public void setfNameEng(String fNameEng) {
        this.fNameEng = fNameEng;
    }

    public String getiNameEng() {
        return iNameEng;
    }

    public void setiNameEng(String iNameEng) {
        this.iNameEng = iNameEng;
    }

    public String getoNameEng() {
        return oNameEng;
    }

    public void setoNameEng(String oNameEng) {
        this.oNameEng = oNameEng;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String getWorkPosition() {
        return workPosition;
    }

    public void setWorkPosition(String workPosition) {
        this.workPosition = workPosition;
    }

    public Timestamp getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Timestamp dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Date getDateDocument() {
        return dateDocument;
    }

    public void setDateDocument(Date dateDocument) {
        this.dateDocument = dateDocument;
    }

    public Date getDateAdmission() {
        return dateAdmission;
    }

    public void setDateAdmission(Date dateAdmission) {
        this.dateAdmission = dateAdmission;
    }

    public Date getDateDefense() {
        return dateDefense;
    }

    public void setDateDefense(Date dateDefense) {
        this.dateDefense = dateDefense;
    }

    public Time getTimeDefense() {
        return timeDefense;
    }

    public void setTimeDefense(Time timeDefense) {
        this.timeDefense = timeDefense;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getThemeEng() {
        return themeEng;
    }

    public void setThemeEng(String themeEng) {
        this.themeEng = themeEng;
    }

    public String getPartText() {
        return partText;
    }

    public void setPartText(String partText) {
        this.partText = partText;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getKeywordEng() {
        return keywordEng;
    }

    public void setKeywordEng(String keywordEng) {
        this.keywordEng = keywordEng;
    }

    public String getUrlVak() {
        return urlVak;
    }

    public void setUrlVak(String urlVak) {
        this.urlVak = urlVak;
    }

    public String getUrlApplicant() {
        return urlApplicant;
    }

    public void setUrlApplicant(String urlApplicant) {
        this.urlApplicant = urlApplicant;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getCertificationCase() {
        return certificationCase;
    }

    public void setCertificationCase(String certificationCase) {
        this.certificationCase = certificationCase;
    }

    public Date getCertificationDate() {
        return certificationDate;
    }

    public void setCertificationDate(Date certificationDate) {
        this.certificationDate = certificationDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
        ScienceDissertations that = (ScienceDissertations) o;
        return scienceDissertationId == that.scienceDissertationId && personId == that.personId && active == that.active && Objects.equals(employeePositionId, that.employeePositionId) && Objects.equals(scienceCouncilSpecId, that.scienceCouncilSpecId) && Objects.equals(degreeDetailId, that.degreeDetailId) && Objects.equals(scienceApplicantStatusId, that.scienceApplicantStatusId) && Objects.equals(scienceDissertationStatusId, that.scienceDissertationStatusId) && Objects.equals(auditoryId, that.auditoryId) && Objects.equals(orderId, that.orderId) && Objects.equals(specId, that.specId) && Objects.equals(fNameEng, that.fNameEng) && Objects.equals(iNameEng, that.iNameEng) && Objects.equals(oNameEng, that.oNameEng) && Objects.equals(contacts, that.contacts) && Objects.equals(workPlace, that.workPlace) && Objects.equals(workPosition, that.workPosition) && Objects.equals(dateAdded, that.dateAdded) && Objects.equals(dateDocument, that.dateDocument) && Objects.equals(dateAdmission, that.dateAdmission) && Objects.equals(dateDefense, that.dateDefense) && Objects.equals(timeDefense, that.timeDefense) && Objects.equals(theme, that.theme) && Objects.equals(themeEng, that.themeEng) && Objects.equals(partText, that.partText) && Objects.equals(keyword, that.keyword) && Objects.equals(keywordEng, that.keywordEng) && Objects.equals(urlVak, that.urlVak) && Objects.equals(urlApplicant, that.urlApplicant) && Objects.equals(qrCode, that.qrCode) && Objects.equals(certificationCase, that.certificationCase) && Objects.equals(certificationDate, that.certificationDate) && Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scienceDissertationId, personId, employeePositionId, scienceCouncilSpecId, degreeDetailId, scienceApplicantStatusId, scienceDissertationStatusId, auditoryId, orderId, specId, fNameEng, iNameEng, oNameEng, contacts, workPlace, workPosition, dateAdded, dateDocument, dateAdmission, dateDefense, timeDefense, theme, themeEng, partText, keyword, keywordEng, urlVak, urlApplicant, qrCode, certificationCase, certificationDate, comment, active);
    }
}
