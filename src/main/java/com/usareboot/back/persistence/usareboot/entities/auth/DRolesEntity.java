package com.usareboot.back.persistence.usareboot.entities.auth;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "d_roles",  catalog = "usareboot")
public class DRolesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "role_id")
    private long roleId;
//    @Basic
//    @Column(name = "role_code")
//    private String    roleName;
    @Basic
    @Column(name = "role_name")
    private String    roleName;
    @Basic
    @Column(name = "active")
    private int active;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_tabs",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "tab_id")
    )
    private Set<TabEntity> tabEntities = new HashSet<>();

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public void setRoleName(String roleCode) {
        this.roleName = roleCode;
    }

//    public String getRoleName() {
//        return roleName;
//    }
//
//    public void setRoleName(String roleName) {
//        this.roleName = roleName;
//    }

    public void setActive(int active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DRolesEntity that = (DRolesEntity) o;
        return roleId == that.roleId && active == that.active && Objects.equals(roleName, that.roleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, roleName, active);
    }
}
