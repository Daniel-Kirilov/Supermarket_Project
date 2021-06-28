package com.endava.supermarket.dto.supermarket_dtos;

import com.endava.supermarket.dto.item_dtos.ResponseItemsDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ResponseGetSupermarketByIdDto {

    private String name;

    private String address;

    private String phoneNumber;

    private String workHours;

    private List<ResponseItemsDto> items;
}
