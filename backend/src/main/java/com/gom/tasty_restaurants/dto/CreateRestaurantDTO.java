package com.gom.tasty_restaurants.dto;

import lombok.Data;

@Data
public class CreateRestaurantDTO {

    private String name;
    private String address;
    private String phone;
    private String site;
    private String photoUrl;
}
