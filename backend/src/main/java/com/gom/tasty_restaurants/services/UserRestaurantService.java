package com.gom.tasty_restaurants.services;

import com.gom.tasty_restaurants.dto.*;
import com.gom.tasty_restaurants.model.Restaurant;
import com.gom.tasty_restaurants.model.User;
import com.gom.tasty_restaurants.model.UserRestaurant;
import com.gom.tasty_restaurants.repositories.UserRestaurantRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRestaurantService {

    @Autowired
    private UserRestaurantRepository userRestaurantRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticatedUserService authenticatedUserService;

    public List<UserRestaurantCardDTO> getUserRestaurants() {
        Long userId = authenticatedUserService.getUserId();
        return userRestaurantRepository.findAllByUserId(userId)
                .stream()
                .map(relation -> {

                    Restaurant r = relation.getRestaurant();

                    UserRestaurantCardDTO dto = new UserRestaurantCardDTO();
                    dto.setUserRestaurantId(relation.getId());
                    dto.setRestaurantId(r.getId());
                    dto.setRestaurantName(r.getName());
                    dto.setAddress(r.getAddress());
                    dto.setPhone(r.getPhone());
                    dto.setSite(r.getSite());
                    dto.setRestaurantPhotoUrl(r.getPhotoUrl());
                    dto.setUserPhotoUrl(relation.getPhotoUrl());
                    dto.setRating(relation.getRating());
                    dto.setComment(relation.getComment());
                    dto.setWasVisited(relation.isWasVisited());

                    return dto;
                })
                .toList();
    }

    @Transactional
    public UserRestaurant linkUserToRestaurant(@Valid CreateUserRestaurantRelationDTO dto) {
        Long userId = authenticatedUserService.getUserId();
        User user = userService.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found."));

        Restaurant restaurant = restaurantService.findById(dto.getRestaurantId())
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found."));

        if (userRestaurantRepository.existsByUserAndRestaurant(user, restaurant)) {
            throw new IllegalStateException("You already added this restaurant.");
        }

        UserRestaurant relation = new UserRestaurant();
        relation.setUser(user);
        relation.setRestaurant(restaurant);
        relation.setWasVisited(dto.isWasVisited());
        Integer rating = dto.getRating();
        if (dto.getRating() != null && (rating < 0 || rating > 5)) {
            throw new IllegalArgumentException("Rating must be between 0 and 5.");
        }
        relation.setRating(rating);
        relation.setComment(dto.getComment());
        relation.setPhotoUrl(dto.getPhotoUrl());

        return userRestaurantRepository.save(relation);
    }

    @Transactional
    @PreAuthorize("@userRestaurantSecurity.isOwner(#userRestaurantId)")
    public UserRestaurantResponseDTO updateUserRestaurant(Long userRestaurantId, @Valid UpdateUserRestaurantDTO dto) {

        UserRestaurant existing = userRestaurantRepository.findById(userRestaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Relation not found"));

        if (dto.getRating() != null) existing.setRating(dto.getRating());
        if (dto.getComment() != null) existing.setComment(dto.getComment());
        if (dto.getWasVisited() != null) existing.setWasVisited(dto.getWasVisited());
        if (dto.getPhotoUrl() != null) existing.setPhotoUrl(dto.getPhotoUrl());

        UserRestaurant saved = userRestaurantRepository.save(existing);

        UserRestaurantResponseDTO response = new UserRestaurantResponseDTO();
        response.setId(saved.getId());
        response.setRestaurantName(saved.getRestaurant().getName());
        response.setRating(saved.getRating());
        response.setWasVisited(saved.isWasVisited());
        response.setComment(saved.getComment());
        response.setPhotoUrl(saved.getPhotoUrl());

        return response;
    }

    @Transactional
    @PreAuthorize("@userRestaurantSecurity.isOwner(#userRestaurantId)")
    public void addRating(Long userRestaurantId,Integer rating) {
        if (rating == null || rating < 0 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 0 and 5.");
        }

        UserRestaurant restaurant = userRestaurantRepository.findById(userRestaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Relation not found"));

        restaurant.setRating(rating);
        userRestaurantRepository.save(restaurant);
    }

    @Transactional
    @PreAuthorize("@userRestaurantSecurity.isOwner(#userRestaurantId)")
    public void deleteUserRestaurant(Long userRestaurantId) {

        UserRestaurant restaurant = userRestaurantRepository.findById(userRestaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found"));

        userRestaurantRepository.delete(restaurant);
    }
}
