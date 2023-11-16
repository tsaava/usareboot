package ru.spmi.backend.dto.sciense;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ListScienceCouncilDTO {
    //для вывода списка персон
    private long scienceCouncilSpecId;
    private long scienceCouncilId;
    private long specId;
    private long orderId;
    private String orderNumber;
    private String orderDate;
    private String scienceCouncilName;
    private String dateFrom;
    private String dateTo;
    private String specName;
    private String phone;
    private String eMail;
}
