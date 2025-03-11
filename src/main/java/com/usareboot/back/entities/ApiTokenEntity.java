package com.usareboot.back.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "api_token", schema = "public", catalog = "usareboot")
public class ApiTokenEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "api_token_id")
    private long apiTokenId;
    @Basic
    @Column(name = "vk_client_id")
    private Long vkClientId;
    @Basic
    @Column(name = "token")
    private String token;
    @Basic
    @Column(name = "refresh_token")
    private String refreshToken;
    @Basic
    @Column(name = "token_start")
    private LocalDateTime tokenStart;
    @Basic
    @Column(name = "token_end")
    private LocalDateTime tokenEnd;

    @Basic
    @Column(name = "group_id")
    private Long groupId;

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
