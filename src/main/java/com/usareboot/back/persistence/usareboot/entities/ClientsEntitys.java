package com.usareboot.back.persistence.usareboot.entities;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "clients",  catalog = "usareboot")
public class ClientsEntitys {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "client_id")
    private long clientId;
    @Basic
    @Column(name = "login")
    private String login;
    @Basic
    @Column(name = "password")
    private String password;
    @Basic
    @Column(name = "phone")
    private String phone;
    @Basic
    @Column(name = "f_name")
    private String fName;
    @Basic
    @Column(name = "i_name")
    private String iName;
    @Basic
    @Column(name = "o_name")
    private String oName;
    @Basic
    @Column(name = "birdth_date")
    private Date birdthDate;
    @Basic
    @Column(name = "vk_id")
    private Long vkId;
    @Basic
    @Column(name = "tg_id")
    private Long tgId;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "fio")
    private String fio;

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getiName() {
        return iName;
    }

    public void setiName(String iName) {
        this.iName = iName;
    }

    public String getoName() {
        return oName;
    }

    public void setoName(String oName) {
        this.oName = oName;
    }

    public Date getBirdthDate() {
        return birdthDate;
    }

    public void setBirdthDate(Date birdthDate) {
        this.birdthDate = birdthDate;
    }

    public Long getVkId() {
        return vkId;
    }

    public void setVkId(Long vkId) {
        this.vkId = vkId;
    }

    public Long getTgId() {
        return tgId;
    }

    public void setTgId(Long tgId) {
        this.tgId = tgId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientsEntitys that = (ClientsEntitys) o;
        return clientId == that.clientId && Objects.equals(login, that.login) && Objects.equals(password, that.password) && Objects.equals(phone, that.phone) && Objects.equals(fName, that.fName) && Objects.equals(iName, that.iName) && Objects.equals(oName, that.oName) && Objects.equals(birdthDate, that.birdthDate) && Objects.equals(vkId, that.vkId) && Objects.equals(tgId, that.tgId) && Objects.equals(email, that.email) && Objects.equals(fio, that.fio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, login, password, phone, fName, iName, oName, birdthDate, vkId, tgId, email, fio);
    }
}
