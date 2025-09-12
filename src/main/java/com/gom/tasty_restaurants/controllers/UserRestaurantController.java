package com.gom.tasty_restaurants.controllers;

import com.gom.tasty_restaurants.dto.CreateUserRestaurantRelationDTO;
import com.gom.tasty_restaurants.dto.UpdateUserRestaurantDTO;
import com.gom.tasty_restaurants.dto.UserRestaurantCardDTO;
import com.gom.tasty_restaurants.dto.UserRestaurantResponseDTO;
import com.gom.tasty_restaurants.model.UserRestaurant;
import com.gom.tasty_restaurants.services.UserRestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-restaurant")
public class UserRestaurantController {

    @Autowired
    private UserRestaurantService userRestaurantService;

    @GetMapping("/restaurants")
    public ResponseEntity<List<UserRestaurantCardDTO>> getUserRestaurants() {
        List<UserRestaurantCardDTO> restaurants = userRestaurantService.getUserRestaurants();

        return ResponseEntity.ok(restaurants);
    }

    @PostMapping("/link-restaurant")
    public ResponseEntity<UserRestaurantResponseDTO> linkUserToRestaurant(@RequestBody CreateUserRestaurantRelationDTO dto) {
        UserRestaurant relation = userRestaurantService.linkUserToRestaurant(dto);

        UserRestaurantResponseDTO response = new UserRestaurantResponseDTO();
        response.setId(relation.getId());
        response.setRestaurantId(relation.getRestaurant().getId());
        response.setRestaurantName(relation.getRestaurant().getName());
        response.setComment(relation.getComment());
        response.setRating(relation.getRating());
        response.setWasVisited(relation.isWasVisited());
        response.setPhotoUrl(relation.getPhotoUrl());

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{userRestaurantId}")
    public ResponseEntity<UserRestaurantResponseDTO> update(@PathVariable Long userRestaurantId, @RequestBody UpdateUserRestaurantDTO dto) {
        UserRestaurantResponseDTO update = userRestaurantService.updateUserRestaurant(userRestaurantId, dto);

        return ResponseEntity.ok(update);
    }

    @PatchMapping("/add-rating/{userRestaurantId}")
    public ResponseEntity<Void> addRating(@PathVariable Long userRestaurantId,@RequestBody Integer rating) {
        userRestaurantService.addRating(userRestaurantId, rating);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userRestaurantId}")
    public ResponseEntity<Void> deleteUserRestaurant(@PathVariable Long userRestaurantId) {
        userRestaurantService.deleteUserRestaurant(userRestaurantId);
        return ResponseEntity.noContent().build();
    }
}
