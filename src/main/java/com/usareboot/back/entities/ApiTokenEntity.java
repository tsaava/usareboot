package com.usareboot.back.entities;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Table(name = "api_token", schema = "public", catalog = "usareboot")
public class ApiTokenEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "api_token_id")
    private long apiTokenId;
    @Basic
    @Column(name = "vk_client_id")
    private long vkClientId;
    @Basic
    @Column(name = "token")
    private String token;
    @Basic
    @Column(name = "token_start")
    private LocalDateTime tokenStart;
    @Basic
    @Column(name = "token_end")
    private LocalDateTime tokenEnd;

    public void setApiTokenId(long vkUserDataId) {
        this.apiTokenId = vkUserDataId;
    }

    public void setVkClientId(long vkId) {
        this.vkClientId = vkId;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setTokenStart(LocalDateTime tokenStart) {
        this.tokenStart = tokenStart;
    }

    public void setTokenEnd(LocalDateTime tokenEnd) {
        this.tokenEnd = tokenEnd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiTokenEntity that = (ApiTokenEntity) o;
        return apiTokenId == that.apiTokenId && vkClientId == that.vkClientId && Objects.equals(token, that.token) && Objects.equals(tokenStart, that.tokenStart) && Objects.equals(tokenEnd, that.tokenEnd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(apiTokenId, vkClientId, token, tokenStart, tokenEnd);
    }
}
