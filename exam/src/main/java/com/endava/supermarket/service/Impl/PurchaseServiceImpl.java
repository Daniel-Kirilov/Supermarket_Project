package com.endava.supermarket.service.Impl;

import com.endava.supermarket.dto.purchase_dtos.PurchaseDetailsDto;
import com.endava.supermarket.model.Item;
import com.endava.supermarket.model.Purchase;
import com.endava.supermarket.model.Supermarket;
import com.endava.supermarket.model.SupermarketItemAssociation;
import com.endava.supermarket.model.enums.PaymentType;
import com.endava.supermarket.repository.ItemRepository;
import com.endava.supermarket.repository.PurchaseRepository;
import com.endava.supermarket.repository.SuperMarketRepository;
import com.endava.supermarket.repository.SupermarketItemAssociationsRepository;
import com.endava.supermarket.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final ItemRepository itemRepository;
    private final SuperMarketRepository superMarketRepository;
    private final SupermarketItemAssociationsRepository supermarketItemAssociationsRepository;

    private Supermarket validData(String superMarketId, List<String> itemsId){
        Supermarket validSupermarket = superMarketRepository.findById(superMarketId)
                .orElseThrow(() -> new IllegalArgumentException("Supermarket doesn't exist"));

        List<SupermarketItemAssociation> itemsInSupermarket = validSupermarket.getItems();
        List<String> itemsInSupermarketIds = new ArrayList<>();
        itemsInSupermarket.forEach(itemInSupermarket -> itemsInSupermarketIds.add(itemInSupermarket.getItemId()));
        itemsId.forEach(itemId -> {
            if(!itemsInSupermarketIds.contains(itemId)){
                throw new IllegalArgumentException("Invalid items ids");
            }
        });
        return validSupermarket;

    }
    @Override
    public PurchaseDetailsDto byItemsFromSuperMarket(String superMarketId, List<String> itemsId, PaymentType paymentType, Double cashAmount) {
        Supermarket supermarket =  validData(superMarketId, itemsId);
        List<Item> items = itemRepository.findAllById(itemsId);
        Purchase purchase = new Purchase();
        purchase.setSuperMarketId(superMarketId);
        purchase.setItems(items);
        purchase.setPaymentType(paymentType);
        purchase.setCashAmount(cashAmount);
        purchase = purchaseRepository.save(purchase);
        Purchase finalPurchase = purchase;
        items.forEach(item -> item.setPurchase(List.of(finalPurchase)));
        PurchaseDetailsDto details = new PurchaseDetailsDto();
        double totalPrice = 0;
        Map<String, Double> boughtItems = new HashMap<>();
        for(int i = 0; i < items.size(); i++){
            totalPrice += supermarketItemAssociationsRepository.getSuperMarketItemAssociation(superMarketId, itemsId.get(i)).getItemPrice();
            boughtItems.put(items.get(i).getName(),
                    supermarketItemAssociationsRepository.getSuperMarketItemAssociation(superMarketId, itemsId.get(i)).getItemPrice());
        }
        if(cashAmount - totalPrice < 0){
            throw new IllegalArgumentException("Not enough money");
        }
        details.setSuperMarket(supermarket.getName());
        details.setBoughtItems(boughtItems);
        details.setTotalPrice(totalPrice);
        details.setCashAmount(cashAmount);
        details.setReturnedChange(cashAmount - details.getTotalPrice());
        details.setTimeOfExecutedPayment(LocalDateTime.now());
        return details;
    }

    @Override
    public List<Purchase> getAllPurchases() {

        return purchaseRepository.findAll();
    }
}
