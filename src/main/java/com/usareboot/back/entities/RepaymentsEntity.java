package com.usareboot.back.entities;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigInteger;
import java.sql.Date;
import java.util.Objects;

@Getter
@Entity
@Table(name = "repayments", schema = "public", catalog = "usareboot")
public class RepaymentsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "repayment_id")
    private long repaymentId;
    @Basic
    @Column(name = "repayment_name")
    private String repaymentName;
    @Basic
    @Column(name = "track")
    private String track;
    @Basic
    @Column(name = "date_shipme_foreign_wh")
    private Date dateShipmeForeignWh;
    @Basic
    @Column(name = "pk_name")
    private String pkName;
    @Basic
    @Column(name = "date_received_central_wh")
    private Date dateReceivedCentralWh;
    @Basic
    @Column(name = "percentage_income", nullable = false)
    private Integer percentageIncome;
    @Basic
    @Column(name = "percentage")
    private Integer percentage;
    @Basic
    @Column(name = "amount_purchase")
    private Integer amountPurchase;
    @Basic
    @Column(name = "amount_income")
    private Integer amountIncome;
    @Basic
    @Column(name = "is_done")
    private Boolean isDone;


    public void setRepaymentId(long repaymentId) {
        this.repaymentId = repaymentId;
    }


    public void setRepaymentName(String repaymentName) {
        this.repaymentName = repaymentName;
    }


    public void setTrack(String track) {
        this.track = track;
    }

    public void setDateShipmeForeignWh(Date dateShipmeForeignWh) {
        this.dateShipmeForeignWh = dateShipmeForeignWh;
    }


    public void setPkName(String pkName) {
        this.pkName = pkName;
    }


    public void setDateReceivedCentralWh(Date dateReceivedCentralWh) {
        this.dateReceivedCentralWh = dateReceivedCentralWh;
    }

    public void setPercentageIncome(Integer percentageIncome) {
        this.percentageIncome = percentageIncome;
    }


    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }


    public void setAmountPurchase(Integer amountPurchase) {
        this.amountPurchase = amountPurchase;
    }


    public void setAmountIncome(Integer amountIncome) {
        this.amountIncome = amountIncome;
    }


    public void setDone(Boolean done) {
        isDone = done;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RepaymentsEntity that = (RepaymentsEntity) o;
        return repaymentId == that.repaymentId && Objects.equals(repaymentName, that.repaymentName) && Objects.equals(track, that.track) && Objects.equals(dateShipmeForeignWh, that.dateShipmeForeignWh) && Objects.equals(pkName, that.pkName) && Objects.equals(dateReceivedCentralWh, that.dateReceivedCentralWh) && Objects.equals(percentageIncome, that.percentageIncome) && Objects.equals(percentage, that.percentage) && Objects.equals(amountPurchase, that.amountPurchase) && Objects.equals(amountIncome, that.amountIncome) && Objects.equals(isDone, that.isDone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(repaymentId, repaymentName, track, dateShipmeForeignWh, pkName, dateReceivedCentralWh, percentageIncome, percentage, amountPurchase, amountIncome, isDone);
    }
}
