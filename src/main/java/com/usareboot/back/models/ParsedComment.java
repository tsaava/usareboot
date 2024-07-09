package com.usareboot.back.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParsedComment {
    private String size;
    private String color;
    private String count;
    private String link;

    @Override
    public String toString() {
        return "ParsedComment{" +
                "size='" + size + '\'' +
                ", color='" + color + '\'' +
                ", count='" + count + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
