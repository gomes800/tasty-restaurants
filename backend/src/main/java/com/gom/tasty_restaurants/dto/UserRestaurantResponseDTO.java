package com.gom.tasty_restaurants.dto;

import lombok.Data;

@Data
public class UserRestaurantResponseDTO {
    private String restaurantName;
    private String comment;
    private Integer rating;
    private boolean wasVisited;
}
