package com.usareboot.back.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Rate {
    long rateBuyingId;
    long countryId;
    LocalDate dateBuying;
    BigDecimal rateExchange;
    String type;
    BigDecimal percentPayment;
    BigDecimal differenceUsdt;
    BigDecimal ratePayment;
    BigDecimal percentClient;
    BigDecimal rateClient;
    String countryName;
    String currency;
}
