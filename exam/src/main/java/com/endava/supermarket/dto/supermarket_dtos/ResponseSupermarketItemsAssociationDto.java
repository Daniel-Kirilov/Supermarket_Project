package com.endava.supermarket.dto.supermarket_dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseSupermarketItemsAssociationDto {

    private String supermarketId;

    private String itemId;

    private double itemPrice;
}
