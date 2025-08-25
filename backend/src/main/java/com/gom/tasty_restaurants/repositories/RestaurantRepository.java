package com.gom.tasty_restaurants.repositories;

import com.gom.tasty_restaurants.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    boolean existsByIdAndCreatedById(Long id, Long createdById);
}
