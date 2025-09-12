package com.gom.tasty_restaurants.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateRestaurantDTO {

    private String name;
    private String address;
    private String photoUrl;
}
