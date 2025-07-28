package com.gom.tasty_restaurants.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "restaurants")
public class Restaurant implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String googlePlaceId;

    private String name;
    private String address;
    private String phone;
    private String site;
    private String photoUrl;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserRestaurant> userInteractions = new ArrayList<>();

    public Restaurant(String name, String address, String phone, String site, String photoUrl) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.site = site;
        this.photoUrl = photoUrl;
    }
}
