package com.gom.tasty_restaurants.services;

import com.gom.tasty_restaurants.dto.CreateUserRestaurantDTO;
import com.gom.tasty_restaurants.dto.UpdateUserRestaurantDTO;
import com.gom.tasty_restaurants.dto.UserRestaurantResponseDTO;
import com.gom.tasty_restaurants.model.Restaurant;
import com.gom.tasty_restaurants.model.User;
import com.gom.tasty_restaurants.model.UserRestaurant;
import com.gom.tasty_restaurants.repositories.UserRestaurantRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<UserRestaurantResponseDTO> showAllUserRestaurants() {
        Long userId = authenticatedUserService.getUserId();
        return userRestaurantRepository.findAllByUserId(userId)
                .stream()
                .map(relation -> {
                    UserRestaurantResponseDTO dto = new UserRestaurantResponseDTO();
                    dto.setRestaurantName(relation.getRestaurant().getName());
                    dto.setComment(relation.getComment());
                    dto.setRating(relation.getRating());
                    dto.setWasVisited(relation.isWasVisited());
                    return dto;
                })
                .toList();
    }

    @Transactional
    public UserRestaurant linkUserToRestaurant(CreateUserRestaurantDTO createUserRestaurantDTO) {
        Long userId = authenticatedUserService.getUserId();
        User user = userService.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found."));

        Restaurant restaurant = restaurantService.findById(createUserRestaurantDTO.getRestaurantId())
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found."));

        if (userRestaurantRepository.existsByUserAndRestaurant(user, restaurant)) {
            throw new IllegalStateException("You already added this restaurant.");
        }

        UserRestaurant relation = new UserRestaurant();
        relation.setUser(user);
        relation.setRestaurant(restaurant);
        relation.setWasVisited(createUserRestaurantDTO.isWasVisited());
        Integer rating = createUserRestaurantDTO.getRating();
        if (createUserRestaurantDTO.getRating() != null && (rating < 0 || rating > 5)) {
            throw new IllegalArgumentException("Rating must be between 0 and 5.");
        }
        relation.setRating(rating);
        relation.setComment(createUserRestaurantDTO.getComment());
        relation.setPhotoUrl(createUserRestaurantDTO.getPhotoUrl());

        return userRestaurantRepository.save(relation);
    }

    @Transactional
    public UserRestaurantResponseDTO updateUserRestaurant(Long userRestaurantId, UpdateUserRestaurantDTO dto) {
        Long userId = authenticatedUserService.getUserId();

        UserRestaurant existing = userRestaurantRepository.findById(userRestaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Relation not found"));

        if (!existing.getUser().getId().equals(userId)) {
            throw new SecurityException("Cannot update another user's relation.");
        }

        if (dto.getRating() != null) {
            if (dto.getRating() < 0 || dto.getRating() > 5) {
                throw new IllegalArgumentException("Rating must be between 0 and 5.");
            }
            existing.setRating(dto.getRating());
        }
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
    public void addRating(Long userRestaurantId,Integer rating) {
        if (rating == null || rating < 0 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 0 and 5.");
        }

        Long userId = authenticatedUserService.getUserId();

        UserRestaurant restaurant = userRestaurantRepository.findById(userRestaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Relation not found"));

        if (!restaurant.getUser().getId().equals(userId)) {
            throw new SecurityException("You can only rate your own restaurant relation.");
        }

        restaurant.setRating(rating);
        userRestaurantRepository.save(restaurant);
    }
}
