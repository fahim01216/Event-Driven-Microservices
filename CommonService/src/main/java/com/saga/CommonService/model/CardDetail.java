package com.saga.CommonService.model;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CardDetail {

    // step : 6
    private String name;
    private String cardNumber;
    private Integer expirationMonth;
    private Integer getExpirationYear;
    private Integer cvvNumber;
}
