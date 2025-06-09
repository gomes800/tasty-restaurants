package com.gom.tasty_restaurants.services;

import com.gom.tasty_restaurants.model.User;
import com.gom.tasty_restaurants.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticatedUserService authenticatedUserService;

    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    @Transactional
    public User update(User user) {
        Long userId = authenticatedUserService.getUserId();

        User existing = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found."));

        existing.setName(user.getName());

        return userRepository.save(existing);
    }
}
