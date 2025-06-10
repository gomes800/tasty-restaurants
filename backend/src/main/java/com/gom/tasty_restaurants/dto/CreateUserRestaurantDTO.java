package com.gom.tasty_restaurants.dto;

import lombok.Data;

@Data
public class CreateUserRestaurantDTO {

    private Long restaurantId;
    private Integer rating;
    private boolean wasVisited;
    private String comment;
    private String photoUrl;
}
