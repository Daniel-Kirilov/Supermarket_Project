package com.endava.supermarket.service;

import com.endava.supermarket.dto.supermarket_dtos.CreateSupermarketDto;
import com.endava.supermarket.dto.supermarket_dtos.GetSupermarketByNameAndAddressDto;
import com.endava.supermarket.model.Supermarket;
import com.endava.supermarket.model.SupermarketItemAssociation;

import java.util.List;

public interface SuperMarketService {

    Supermarket createSupermarket(CreateSupermarketDto supermarketDto);

    List<SupermarketItemAssociation> addItemsToSuperMarket(String superMarketId, List<String> itemsId,
                                                           List<Double> itemsPrice);

    Supermarket getSuperMarketById(String id);

    Supermarket getSupermarketByNameAndAddress(GetSupermarketByNameAndAddressDto supermarketDataDto);
}
