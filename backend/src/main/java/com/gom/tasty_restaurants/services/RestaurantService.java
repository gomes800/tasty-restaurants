package com.gom.tasty_restaurants.services;

import com.gom.tasty_restaurants.dto.CreateRestaurantDTO;
import com.gom.tasty_restaurants.dto.CreateUserRestaurantDTO;
import com.gom.tasty_restaurants.model.Restaurant;
import com.gom.tasty_restaurants.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    public List<Restaurant> findAll() {
        return restaurantRepository.findAll();
    }

    public Optional<Restaurant> findById(Long restaurantId) {
        return restaurantRepository.findById(restaurantId);
    }

    public Restaurant createRestaurant(CreateRestaurantDTO dto) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(dto.getName());
        restaurant.setAddress(dto.getAddress());
        restaurant.setPhone(dto.getPhone());
        restaurant.setSite(dto.getSite());
        restaurant.setPhotoUrl(dto.getPhotoUrl());

        return restaurantRepository.save(restaurant);
    }

}
