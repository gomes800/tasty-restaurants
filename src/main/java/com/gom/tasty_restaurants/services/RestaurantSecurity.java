package com.gom.tasty_restaurants.services;

import com.gom.tasty_restaurants.repositories.RestaurantRepository;
import org.springframework.stereotype.Service;

@Service
public class RestaurantSecurity {

    private final RestaurantRepository restaurantRepository;
    private final AuthenticatedUserService authenticatedUserService;

    public RestaurantSecurity(RestaurantRepository restaurantRepository, AuthenticatedUserService authenticatedUserService) {
        this.restaurantRepository = restaurantRepository;
        this.authenticatedUserService = authenticatedUserService;
    }

    public boolean isCreator(Long restaurantId) {
        Long userId = authenticatedUserService.getUserId();
        return restaurantRepository.existsByIdAndCreatedById(restaurantId, userId);
    }
}
