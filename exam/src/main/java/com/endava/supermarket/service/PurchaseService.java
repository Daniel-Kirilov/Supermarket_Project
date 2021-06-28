package com.endava.supermarket.service;

import com.endava.supermarket.dto.purchase_dtos.PurchaseDetailsDto;
import com.endava.supermarket.model.Purchase;
import com.endava.supermarket.model.enums.PaymentType;

import java.util.List;

public interface PurchaseService {

    PurchaseDetailsDto byItemsFromSuperMarket(String superMarketId, List<String> itemsId, PaymentType paymentType, Double cashAmount);

    List<Purchase> getAllPurchases();
}
