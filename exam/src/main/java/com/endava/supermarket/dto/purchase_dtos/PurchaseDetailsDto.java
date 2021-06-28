package com.endava.supermarket.dto.purchase_dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Setter
@Getter
public class PurchaseDetailsDto {

    private String superMarket;

    private Map<String, Double> boughtItems;

    private Double totalPrice;

    private Double cashAmount;

    private Double returnedChange;

    private LocalDateTime timeOfExecutedPayment;
}
