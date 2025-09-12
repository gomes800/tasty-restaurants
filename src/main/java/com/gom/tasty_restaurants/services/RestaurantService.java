package com.gom.tasty_restaurants.services;

import com.gom.tasty_restaurants.dto.CreateRestaurantDTO;
import com.gom.tasty_restaurants.dto.RestaurantResponseDTO;
import com.gom.tasty_restaurants.model.Restaurant;
import com.gom.tasty_restaurants.model.User;
import com.gom.tasty_restaurants.repositories.RestaurantRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private AuthenticatedUserService authenticatedUserService;

    public List<RestaurantResponseDTO> findAll() {
        return restaurantRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toUnmodifiableList());
    }

    private RestaurantResponseDTO convertToDTO(Restaurant restaurant) {
        RestaurantResponseDTO dto = new RestaurantResponseDTO();
        dto.setId(restaurant.getId());
        dto.setName(restaurant.getName());
        dto.setAddress(restaurant.getAddress());
        dto.setPhotoUrl(restaurant.getPhotoUrl());

        return dto;
    }

    public Optional<Restaurant> findById(Long restaurantId) {
        return restaurantRepository.findById(restaurantId);
    }

    public RestaurantResponseDTO createRestaurant(CreateRestaurantDTO dto) {

        Long userId = authenticatedUserService.getUserId();

        User user = new User();
        user.setId(userId);

        Restaurant restaurant = new Restaurant(dto, user);

        Restaurant saved = restaurantRepository.save(restaurant);

        return new RestaurantResponseDTO(saved);
    }

    @Transactional
    @PreAuthorize("@restaurantSecurity.isCreator(#id)")
    public RestaurantResponseDTO updateRestaurant(Long id, CreateRestaurantDTO dto) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurante não encontrado"));

        if (dto.getName() != null) restaurant.setName(dto.getName());
        if (dto.getAddress() != null) restaurant.setAddress(dto.getAddress());
        if (dto.getPhotoUrl() != null) restaurant.setPhotoUrl(dto.getPhotoUrl());

        restaurantRepository.save(restaurant);

        return new RestaurantResponseDTO(restaurant);
    }

    @Transactional
    @PreAuthorize("@restaurantSecurity.isCreator(#id)")
    public void deleteRestaurant(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurante não encontrado"));

        restaurantRepository.delete(restaurant);
    }

}
