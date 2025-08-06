package com.gom.tasty_restaurants.services;

import com.gom.tasty_restaurants.dto.CreateRestaurantDTO;
import com.gom.tasty_restaurants.dto.CreateUserRestaurantDTO;
import com.gom.tasty_restaurants.dto.RestaurantResponseDTO;
import com.gom.tasty_restaurants.dto.UpdateRestaurantDTO;
import com.gom.tasty_restaurants.model.Restaurant;
import com.gom.tasty_restaurants.model.User;
import com.gom.tasty_restaurants.repositories.RestaurantRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private AuthenticatedUserService authenticatedUserService;

    public List<Restaurant> findAll() {
        return restaurantRepository.findAll();
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
    public RestaurantResponseDTO updateRestaurant(Long id, UpdateRestaurantDTO dto) {
        Long userId = authenticatedUserService.getUserId();

        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurante não encontrado"));

        if (!restaurant.getCreatedBy().getId().equals(userId)) {
            throw new AccessDeniedException("Você não tem permissão para editar este restaurante.");
        }

        if (dto.getName() != null) restaurant.setName(dto.getName());
        if (dto.getAddress() != null) restaurant.setAddress(dto.getAddress());
        if (dto.getPhone() != null) restaurant.setPhone(dto.getPhone());
        if (dto.getSite() != null) restaurant.setSite(dto.getSite());
        if (dto.getPhotoUrl() != null) restaurant.setPhotoUrl(dto.getPhotoUrl());

        restaurantRepository.save(restaurant);

        return new RestaurantResponseDTO(restaurant);
    }

    @Transactional
    public void deleteRestaurant(Long id) {
        Long userId = authenticatedUserService.getUserId();

        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurante não encontrado"));

        if (!restaurant.getCreatedBy().getId().equals(userId)) {
            throw new AccessDeniedException("Você não tem permissão para deletar este restaurante.");
        }

        restaurantRepository.delete(restaurant);
    }

}
