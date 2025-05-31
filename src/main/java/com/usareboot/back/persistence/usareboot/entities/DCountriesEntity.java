package com.usareboot.back.persistence.usareboot.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Table(name = "d_countries",  catalog = "dbusareboot")
public class DCountriesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "country_id")
    private int countryId;
    @Basic
    @Column(name = "country_name")
    private String countryName;
    @Basic
    @Column(name = "currency")
    private String currency;
    @Basic
    @Column(name = "active")
    private int active;


    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }


    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }


    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public void setActive(int active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DCountriesEntity that = (DCountriesEntity) o;
        return countryId == that.countryId && Objects.equals(countryName, that.countryName) && Objects.equals(currency, that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryId, countryName, currency);
    }
}
