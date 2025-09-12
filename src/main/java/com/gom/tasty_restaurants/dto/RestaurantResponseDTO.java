package com.gom.tasty_restaurants.dto;

import com.gom.tasty_restaurants.model.Restaurant;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RestaurantResponseDTO {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String site;
    private String photoUrl;
    private Long createdById;

    public RestaurantResponseDTO(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.name = restaurant.getName();
        this.address = restaurant.getAddress();
        this.phone = restaurant.getPhone();
        this.site = restaurant.getSite();
        this.photoUrl = restaurant.getPhotoUrl();
        this.createdById = restaurant.getCreatedBy() != null ? restaurant.getCreatedBy().getId() : null;
    }
}
