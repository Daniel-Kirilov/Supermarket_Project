package com.endava.supermarket.dto.purchase_dtos;

import com.endava.supermarket.dto.item_dtos.ResponseItemsDto;
import com.endava.supermarket.model.enums.PaymentType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ResponseGetAllPurchasesDto {

    private String id;

    private PaymentType paymentType;

    private String superMarketId;

    private List<ResponseItemsDto> items;

}
