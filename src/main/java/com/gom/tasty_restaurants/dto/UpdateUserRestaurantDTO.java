package com.gom.tasty_restaurants.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserRestaurantDTO {
    @Min(0) @Max(5) private Integer rating;
    private Boolean wasVisited;
    @Size(max = 500) private String comment;
    private String photoUrl;
}
