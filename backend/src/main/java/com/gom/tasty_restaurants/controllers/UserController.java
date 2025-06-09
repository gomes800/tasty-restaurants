package com.gom.tasty_restaurants.controllers;

import com.gom.tasty_restaurants.model.User;
import com.gom.tasty_restaurants.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("#id == principal.id")
    @GetMapping("/{userId}")
    public ResponseEntity<User> findById(@PathVariable Long userId){
        Optional<User> user = userService.findById(userId);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<User> update(@RequestBody User user) {
        try {
            User updated = userService.update(user);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
