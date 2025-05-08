package com.usareboot.back.persistence.usareboot.entities;

import com.usareboot.back.persistence.usareboot.entities.auth.TabEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "rate_buying", catalog = "dbusareboot")
public class RateBuyingEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "rate_buying_id")
    private long rateBuyingId;

    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "country_id")
    private DCountriesEntity country;

    @Basic
    @Column(name = "date_buying")
    private Timestamp dateBuying;
    @Basic
    @Column(name = "rate_exchange")
    private BigInteger rateExchange;
    @Basic
    @Column(name = "sum_exchange")
    private BigInteger sumExchange;
    @Basic
    @Column(name = "sum_exchange_usdt")
    private BigInteger sumExchangeUsdt;
    @Basic
    @Column(name = "type")
    private String type;
    @Basic
    @Column(name = "percent_payment")
    private BigInteger percentPayment;
    @Basic
    @Column(name = "difference_usdt")
    private BigInteger differenceUsdt;
    @Basic
    @Column(name = "rate_payment")
    private BigInteger ratePayment;
    @Basic
    @Column(name = "percent_client")
    private BigInteger percentClient;
    @Basic
    @Column(name = "rate_client")
    private BigInteger rateClient;


    public void setRateBuyingId(long rateBuyingId) {
        this.rateBuyingId = rateBuyingId;
    }




    public void setDateBuying(Timestamp dateBuying) {
        this.dateBuying = dateBuying;
    }


    public void setRateExchange(BigInteger rateExchange) {
        this.rateExchange = rateExchange;
    }


    public void setSumExchange(BigInteger sumExchange) {
        this.sumExchange = sumExchange;
    }


    public void setSumExchangeUsdt(BigInteger sumExchangeUsdt) {
        this.sumExchangeUsdt = sumExchangeUsdt;
    }


    public void setType(String type) {
        this.type = type;
    }


    public void setPercentPayment(BigInteger percentPayment) {
        this.percentPayment = percentPayment;
    }


    public void setDifferenceUsdt(BigInteger differenceUsdt) {
        this.differenceUsdt = differenceUsdt;
    }


    public void setRatePayment(BigInteger ratePayment) {
        this.ratePayment = ratePayment;
    }


    public void setPercentClient(BigInteger percentClient) {
        this.percentClient = percentClient;
    }


    public void setRateClient(BigInteger rateClient) {
        this.rateClient = rateClient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RateBuyingEntity that = (RateBuyingEntity) o;
        return rateBuyingId == that.rateBuyingId  && Objects.equals(dateBuying, that.dateBuying) && Objects.equals(rateExchange, that.rateExchange) && Objects.equals(sumExchange, that.sumExchange) && Objects.equals(sumExchangeUsdt, that.sumExchangeUsdt) && Objects.equals(type, that.type) && Objects.equals(percentPayment, that.percentPayment) && Objects.equals(differenceUsdt, that.differenceUsdt) && Objects.equals(ratePayment, that.ratePayment) && Objects.equals(percentClient, that.percentClient) && Objects.equals(rateClient, that.rateClient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rateBuyingId, dateBuying, rateExchange, sumExchange, sumExchangeUsdt, type, percentPayment, differenceUsdt, ratePayment, percentClient, rateClient);
    }
}
