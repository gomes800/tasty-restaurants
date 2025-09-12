package com.gom.tasty_restaurants.repositories;

import com.gom.tasty_restaurants.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByLogin(String login);
}
