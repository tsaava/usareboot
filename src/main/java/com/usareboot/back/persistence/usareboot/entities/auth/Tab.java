package com.usareboot.back.persistence.usareboot.entities.auth;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "tabs", catalog = "dbusareboot")
public class Tab {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "tab_id")
    private int tabId;
    @Basic
    @Column(name = "path")
    private String path;
    @Basic
    @Column(name = "name")
    private String name;

    public void setTabId(int id) {
        this.tabId = id;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tab that = (Tab) o;
        return tabId == that.tabId && Objects.equals(path, that.path) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tabId, path, name);
    }
}
