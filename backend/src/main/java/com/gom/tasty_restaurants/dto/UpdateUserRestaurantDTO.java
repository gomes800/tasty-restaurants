package com.gom.tasty_restaurants.dto;

import lombok.Data;

@Data
public class UpdateUserRestaurantDTO {
    private Integer rating;
    private Boolean wasVisited;
    private String comment;
    private String photoUrl;
}
