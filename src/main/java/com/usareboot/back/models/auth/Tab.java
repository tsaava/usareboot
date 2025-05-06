package com.usareboot.back.models.auth;

import com.usareboot.back.persistence.usareboot.entities.auth.TabEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tab {
    private String name;
    private String path;
    private String sort;
}
