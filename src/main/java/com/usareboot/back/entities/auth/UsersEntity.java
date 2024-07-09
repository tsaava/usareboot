package com.usareboot.back.entities.auth;

import jakarta.persistence.*;
import lombok.Getter;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "clients", schema = "public", catalog = "usareboot")
public class UsersEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "client_id")
    private long userId;
//    @Basic
//    @Column(name = "person_id")
//    private long personId;
    @Basic
    @Column(name = "login")
    private String login;
    @Basic
    @Column(name = "password")
    private String password;

    @Getter
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
    private LocalDate birdthDate;

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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "person_users",
            joinColumns = @JoinColumn (name="client_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id" )
    )
    private Set<DRolesEntity> roles;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<DRolesEntity> getRoles(){
    return roles;
}

    public  Set<DRolesEntity> setRoles(Set<DRolesEntity> roles ) {
        this.roles = roles;
        return roles;
    }
    public void setVkId(Long vkId) {
        this.vkId = vkId;
    }


    public void setTgId(Long tgId) {
        this.tgId = tgId;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public void setFio(String fio) {
        this.fio = fio;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }
    public void setiName(String iName) {
        this.iName = iName;
    }
    public void setoName(String oName) {
        this.oName = oName;
    }
    public void setBirdthDate(LocalDate birdthDate) {
        this.birdthDate = birdthDate;
    }
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        UsersEntity that = (UsersEntity) o;
//        return userId == that.userId  && Objects.equals(login, that.login) && Objects.equals(password, that.password) && Objects.equals(roles, that.roles);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(userId, login, password, roles);
//    }
}
