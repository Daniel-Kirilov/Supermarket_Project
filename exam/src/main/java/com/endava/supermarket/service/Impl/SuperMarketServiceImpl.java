package com.endava.supermarket.service.Impl;

import com.endava.supermarket.dto.supermarket_dtos.CreateSupermarketDto;
import com.endava.supermarket.dto.supermarket_dtos.GetSupermarketByNameAndAddressDto;
import com.endava.supermarket.model.Item;
import com.endava.supermarket.model.Supermarket;
import com.endava.supermarket.model.SupermarketItemAssociation;
import com.endava.supermarket.repository.ItemRepository;
import com.endava.supermarket.repository.SuperMarketRepository;
import com.endava.supermarket.repository.SupermarketItemAssociationsRepository;
import com.endava.supermarket.service.SuperMarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SuperMarketServiceImpl implements SuperMarketService {

    private final SuperMarketRepository superMarketRepository;
    private final ItemRepository itemRepository;
    private final SupermarketItemAssociationsRepository associationsRepository;

    @Override
    public Supermarket createSupermarket(CreateSupermarketDto supermarketDto) {

        if(superMarketRepository.existsByAddress(supermarketDto.getAddress())){
            throw new IllegalArgumentException("Supermarket already exists on that address");
        }
        Supermarket supermarketToBeSaved = new Supermarket();
        supermarketToBeSaved.setName(supermarketDto.getName());
        supermarketToBeSaved.setAddress(supermarketDto.getAddress());
        supermarketToBeSaved.setPhoneNumber(supermarketDto.getPhoneNumber());
        supermarketToBeSaved.setWorkHours(supermarketDto.getWorkHours());
        return superMarketRepository.save(supermarketToBeSaved);
    }

    @Override
    public List<SupermarketItemAssociation> addItemsToSuperMarket(String superMarketId, List<String> itemsId,
                                                                  List<Double> itemsPrice) {
        if(itemsId.size() != itemsPrice.size()){
            throw new IllegalArgumentException("The number of items doesn't match the number of item prices");
        }
        Supermarket supermarket = superMarketRepository.findById(superMarketId).get();
        List<Item> items = itemRepository.findAllById(itemsId);
        List<SupermarketItemAssociation> associations = new ArrayList<>();
        for (int i = 0, elementNum = 0; i < items.size(); i++) {
            SupermarketItemAssociation association = new SupermarketItemAssociation();
            association.setItem(items.get(elementNum));
            association.setItemId(items.get(elementNum).getId());
            association.setSupermarket(supermarket);
            association.setSupermarketId(superMarketId);
            association.setItemPrice(itemsPrice.get(elementNum));
            associations.add(association);
            elementNum++;

        }
        associationsRepository.saveAll(associations);
        return associations;

    }

    @Override
    public Supermarket getSuperMarketById(String id) {
        if(!superMarketRepository.existsById(id)){
            throw new IllegalArgumentException("Supermarket doesn't exist");
        }
        return superMarketRepository.findById(id).get();
    }

    @Override
    public Supermarket getSupermarketByNameAndAddress(GetSupermarketByNameAndAddressDto supermarketDataDto) {
        Supermarket supermarket = superMarketRepository.getSupermarketByNameAndAddress(supermarketDataDto.getName(),
                supermarketDataDto.getAddress());
        if(supermarket == null){
            throw new IllegalArgumentException("Invalid name or address");
        }

        return supermarket;
    }
}
