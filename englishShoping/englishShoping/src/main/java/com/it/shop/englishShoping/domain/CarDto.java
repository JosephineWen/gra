package com.it.shop.englishShoping.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDto {
    private List<ShoppingCar> carList;
    private Integer count;
    private Double money;
}
