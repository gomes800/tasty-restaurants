package com.gom.tasty_restaurants.services;

import com.gom.tasty_restaurants.repositories.UserRestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRestaurantSecurity {

    @Autowired
    private UserRestaurantRepository userRestaurantRepository;

    @Autowired
    private AuthenticatedUserService authService;

    public boolean isOwner(Long userRestaurantId) {
        Long userId = authService.getUserId();
        return userRestaurantRepository.existsByIdAndUserId(userRestaurantId, userId);
    }
}
