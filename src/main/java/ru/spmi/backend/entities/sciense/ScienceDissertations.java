package ru.spmi.backend.entities.sciense;

import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "science_dissertations", schema = "public", catalog = "university")
public class ScienceDissertations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "science_dissertation_id", nullable = false)
    private Long scienceDissertationId;

    @Column(name = "person_id")
    private long personId;
    @Column(name = "employee_position_Id")
    private Long employeePositionId;

    @Column(name = "science_council_spec_id")
    private Long scienceCouncilSpecId;
    @Column(name = "degree_detail_id")
    private Long degreeDetailId;
    @Column(name = "science_applicant_status_id")
    private Long scienceApplicantStatusId;

    @Column(name = "science_dissertation_status_id")
    private Long scienceDissertationStatusId;
    @Column(name = "auditory_id")
    private Long auditoryId;
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "spec_id")
    private Long specId;
    @Column(name = "f_name_eng")
    private String fNameEng;
    @Column(name = "i_name_eng")
    private String iNameEng;
    @Column(name = "o_name_eng")
    private String oNameEng;
    @Column(name = "contacts")
    private String contacts;
    @Column(name = "work_place")
    private String workPlace;
    @Column(name = "work_position")
    private String workPosition;
    @Column(name = "date_added")
    private Timestamp dateAdded;
    @Column(name = "date_document")
    private Date dateDocument;
    @Column(name = "date_admission")
    private Date dateAdmission;
    @Column(name = "date_defense")
    private Date dateDefense;
    @Column(name = "time_defense")
    private Time timeDefense;
    @Column(name = "theme")
    private String theme;
    @Column(name = "theme_eng")
    private String themeEng;
    @Column(name = "part_text")
    private String partText;
    @Column(name = "keyword")
    private String keyword;
    @Column(name = "keyword_eng")
    private String keywordEng;
    @Column(name = "url_vak")
    private String urlVak;
    @Column(name = "url_applicant")
    private String urlApplicant;
    @Column(name = "qr_code")
    private String qrCode;
    @Column(name = "certification_case")
    private String certificationCase;
    @Column(name = "certification_date")
    private Date certificationDate;
    @Column(name = "comment")
    private String comment;
    @Column(name = "active")
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
