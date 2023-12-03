package com.usareboot.back.entities.auth;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "person_users", schema = "public", catalog = "usa_reboot")
public class PersonUsersEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "person_user_id")
    private long personUserId;

    @Basic
    @Column(name = "role_id")
    private Long roleId;

    @Basic
    @Column(name = "client_id")
    private Long userId;
    @Basic
    @Column(name = "active")
    private int active;

    public long getPersonUserId() {
        return personUserId;
    }

    public void setPersonUserId(long personUserId) {
        this.personUserId = personUserId;
    }



    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }



    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

}
