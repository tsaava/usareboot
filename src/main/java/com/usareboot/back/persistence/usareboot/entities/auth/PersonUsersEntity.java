package com.usareboot.back.persistence.usareboot.entities.auth;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Objects;

@Getter
@Entity
@Table(name = "person_users",  catalog = "usareboot")
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

    public void setPersonUserId(long personUserId) {
        this.personUserId = personUserId;
    }


    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }


    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setActive(int active) {
        this.active = active;
    }

}
