package com.gom.tasty_restaurants.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationDTO {
    private String login;
    private String password;
}
