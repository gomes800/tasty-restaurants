package com.gom.tasty_restaurants.repositories;

import com.gom.tasty_restaurants.model.Restaurant;
import com.gom.tasty_restaurants.model.User;
import com.gom.tasty_restaurants.model.UserRestaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRestaurantRepository extends JpaRepository<UserRestaurant, Long> {

    boolean existsByUserAndRestaurant(User user, Restaurant restaurant);

    List<UserRestaurant> findAllByUserId(Long userId);

    boolean existsByIdAndUserId(Long userRestaurantId, Long userId);

}
