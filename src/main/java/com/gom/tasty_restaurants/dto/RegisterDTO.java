package com.gom.tasty_restaurants.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterDTO {
    private String name;
    private String login;
    private String password;
}
