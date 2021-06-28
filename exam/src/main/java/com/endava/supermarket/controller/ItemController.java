package com.endava.supermarket.controller;

import com.endava.supermarket.dto.item_dtos.CreateItemDto;
import com.endava.supermarket.dto.item_dtos.ResponseItemsDto;
import com.endava.supermarket.model.Item;
import com.endava.supermarket.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final ModelMapper modelMapper;

    @PostMapping(value = "/create-item", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createItem(@Valid @RequestBody CreateItemDto createItemDto) {

        Item item = itemService.createItem(createItemDto);
        return new ResponseEntity(modelMapper.map(item, ResponseItemsDto.class), HttpStatus.CREATED);
    }
}
