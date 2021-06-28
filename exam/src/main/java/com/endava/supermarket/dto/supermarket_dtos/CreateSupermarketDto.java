package com.endava.supermarket.dto.supermarket_dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateSupermarketDto {

    String name;

    String address;

    String phoneNumber;

    String workHours;
}
