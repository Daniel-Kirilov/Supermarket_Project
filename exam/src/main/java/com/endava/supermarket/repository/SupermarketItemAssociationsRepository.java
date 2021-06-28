package com.endava.supermarket.repository;

import com.endava.supermarket.model.SupermarketItemAssociation;
import com.endava.supermarket.model.SupermarketItemAssociationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SupermarketItemAssociationsRepository extends JpaRepository<SupermarketItemAssociation, SupermarketItemAssociationId> {

    @Query("SELECT s FROM SupermarketItemAssociation s WHERE s.supermarketId = ?1 and s.itemId = ?2")
    SupermarketItemAssociation getSuperMarketItemAssociation(String supermarketId, String itemId);
}
