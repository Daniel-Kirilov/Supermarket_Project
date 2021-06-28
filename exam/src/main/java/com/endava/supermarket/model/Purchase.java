package com.endava.supermarket.model;


import com.endava.supermarket.model.enums.PaymentType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@Entity
public class Purchase {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @NotBlank
    private String superMarketId;

    @NotEmpty
    @ManyToMany
    @JoinTable(
            name = "purchase_item",
            joinColumns = @JoinColumn(name = "purchase_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Item> items;

    private Double cashAmount;

}
