package com.gom.tasty_restaurants.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticatedUserService {

    public Long getUserId() {
        return (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();
    }

    public String getEmail() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
