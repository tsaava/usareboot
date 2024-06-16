package com.usareboot.back.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CardsDTO {
    long cardId;
    String cardName;
    double percent;
}
