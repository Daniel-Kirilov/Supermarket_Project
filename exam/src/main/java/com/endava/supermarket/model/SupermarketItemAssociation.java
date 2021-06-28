package com.endava.supermarket.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="SUPERMARKET_ITEM")
@IdClass(SupermarketItemAssociationId.class)
@Setter
@Getter
public class SupermarketItemAssociation {

    @Id
    @Column(name = "supermarket_id")
    private String supermarketId;

    @Id
    @Column(name = "item_id")
    private String itemId;

    @Column(name = "item_price")
    private double itemPrice;

    @ManyToOne
    @JoinColumn(name = "supermarket_id", updatable = false, insertable = false, referencedColumnName = "id")
    private Supermarket supermarket;

    @ManyToOne
    @JoinColumn(name = "item_id", updatable = false, insertable = false, referencedColumnName = "id")
    private Item item;
}
