package com.gom.tasty_restaurants.controllers;

import com.gom.tasty_restaurants.dto.CreateRestaurantDTO;
import com.gom.tasty_restaurants.dto.RestaurantResponseDTO;
import com.gom.tasty_restaurants.model.Restaurant;
import com.gom.tasty_restaurants.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<List<RestaurantResponseDTO>> findAll() {
        return ResponseEntity.ok(restaurantService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> findById(@PathVariable Long id) {
        Optional<Restaurant> restaurant = restaurantService.findById(id);
        return restaurant.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<RestaurantResponseDTO> createRestaurant(@RequestBody CreateRestaurantDTO restaurantDTO) {
        RestaurantResponseDTO newRestaurant = restaurantService.createRestaurant(restaurantDTO);

        return new ResponseEntity<>(newRestaurant, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RestaurantResponseDTO> updateRestaurant(
            @PathVariable Long id,
            @RequestBody CreateRestaurantDTO dto
    ) {
        RestaurantResponseDTO updated = restaurantService.updateRestaurant(id, dto);
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }

}
