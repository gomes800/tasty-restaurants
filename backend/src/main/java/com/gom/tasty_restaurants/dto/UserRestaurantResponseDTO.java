package com.gom.tasty_restaurants.dto;

import lombok.Data;

@Data
public class UserRestaurantResponseDTO {
    private Long id;
    private Long restaurantId;
    private String restaurantName;
    private String comment;
    private Integer rating;
    private boolean wasVisited;
    private String photoUrl;
}
