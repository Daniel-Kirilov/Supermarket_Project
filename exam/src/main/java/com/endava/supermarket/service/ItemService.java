package com.endava.supermarket.service;

import com.endava.supermarket.dto.item_dtos.CreateItemDto;
import com.endava.supermarket.model.Item;

public interface ItemService {

    Item createItem(CreateItemDto createItemDto);
}
