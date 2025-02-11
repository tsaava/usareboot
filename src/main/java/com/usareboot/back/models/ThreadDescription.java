package com.usareboot.back.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ThreadDescription {
    private Long eventId;
    private String eventName;
    private String baseEntityId;
}
