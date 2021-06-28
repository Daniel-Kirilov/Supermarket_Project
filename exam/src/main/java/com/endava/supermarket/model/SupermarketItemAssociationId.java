package com.endava.supermarket.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Setter
@Getter
public class SupermarketItemAssociationId implements Serializable {

    private String supermarketId;

    private String itemId;

    public int hashCode() {
        return Integer.parseInt(supermarketId) + Integer.parseInt(itemId);
    }

    public boolean equals(Object object) {
        if (object instanceof SupermarketItemAssociationId) {
            SupermarketItemAssociationId otherId = (SupermarketItemAssociationId) object;
            return (otherId.supermarketId.equals(this.supermarketId)) && (otherId.itemId.equals(this.itemId));
        }
        return false;
    }
}
