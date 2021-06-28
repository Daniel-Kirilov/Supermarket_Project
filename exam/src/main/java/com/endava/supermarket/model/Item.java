package com.endava.supermarket.model;


import com.endava.supermarket.model.enums.ItemType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@Entity
public class Item {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotNull(message = "Item type cannot be null")
    @Enumerated(EnumType.STRING)
    private ItemType itemType;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<SupermarketItemAssociation> supermarkets;

    @ManyToMany(mappedBy = "items")
    @JsonIgnore
    List<Purchase> purchase;

}
