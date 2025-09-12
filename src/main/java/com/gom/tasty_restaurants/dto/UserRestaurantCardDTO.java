package com.gom.tasty_restaurants.dto;

import lombok.Data;

@Data
public class UserRestaurantCardDTO {
    private Long userRestaurantId;
    private Long restaurantId;
    private String restaurantName;
    private String address;
    private String phone;
    private String site;
    private String comment;
    private String restaurantPhotoUrl;
    private String userPhotoUrl;
    private Integer rating;
    private boolean wasVisited;
}
