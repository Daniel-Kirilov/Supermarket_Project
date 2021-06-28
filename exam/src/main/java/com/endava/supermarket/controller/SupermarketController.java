package com.endava.supermarket.controller;

import com.endava.supermarket.dto.supermarket_dtos.*;
import com.endava.supermarket.model.Supermarket;
import com.endava.supermarket.service.SuperMarketService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/v1/supermarkets")
@RequiredArgsConstructor
public class SupermarketController {

    private final SuperMarketService superMarketService;
    private final ModelMapper modelMapper;


    @PostMapping(value = "/create-supermarket", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createSuperMarket(@Valid @RequestBody CreateSupermarketDto createSupermarketDto) {

        Supermarket superMarket = superMarketService.createSupermarket(createSupermarketDto);
        return new ResponseEntity(modelMapper.map(superMarket, ResponseSuperMarketDto.class), HttpStatus.CREATED);
    }

    @PostMapping(value = "/add-items", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ResponseSupermarketItemsAssociationDto>> addItemsInSuperMarket(
            @Valid @RequestParam @Pattern(regexp = "([a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8})") @NotNull String superMarketId,
            @Valid @RequestParam @NotEmpty List<String> itemsId,
            @Valid @RequestParam @NotEmpty List<Double> itemsPrice) {

        return ResponseEntity.ok(this.superMarketService.addItemsToSuperMarket(superMarketId, itemsId, itemsPrice)
                .stream()
                .map(supermarketItemAssociation -> modelMapper.map(supermarketItemAssociation, ResponseSupermarketItemsAssociationDto.class))
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/get-by-id/{superMarketId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseGetSupermarketByIdDto> getSuperMarket(
            @Valid @PathVariable(value = "superMarketId") String superMarketId) {

        Supermarket superMarket = superMarketService.getSuperMarketById(superMarketId);
        return ResponseEntity.ok(modelMapper.map(superMarket, ResponseGetSupermarketByIdDto.class));
    }

    @GetMapping(value = "/get-by-name-and-address", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseSuperMarketDto> getSuperMarket(
            @Valid @RequestBody GetSupermarketByNameAndAddressDto supermarketDataDto) {

        Supermarket superMarket = superMarketService.getSupermarketByNameAndAddress(supermarketDataDto);
        return ResponseEntity.ok(modelMapper.map(superMarket, ResponseSuperMarketDto.class));
    }

}
