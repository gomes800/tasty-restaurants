package com.gom.tasty_restaurants.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRestaurantResponseDTO {
    private Long id;
    private Long restaurantId;
    private String restaurantName;
    private String comment;
    private Integer rating;
    private boolean wasVisited;
    private String photoUrl;
}
