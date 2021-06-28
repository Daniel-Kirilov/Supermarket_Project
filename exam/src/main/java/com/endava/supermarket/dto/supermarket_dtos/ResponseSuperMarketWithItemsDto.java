package com.endava.supermarket.dto.supermarket_dtos;

import com.endava.supermarket.dto.item_dtos.ResponseItemsOnlyNamesDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ResponseSuperMarketWithItemsDto {

    private List<ResponseItemsOnlyNamesDto> items;
}
