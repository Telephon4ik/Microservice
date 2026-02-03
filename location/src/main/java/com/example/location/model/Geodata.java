package com.example.location.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Geodata {
    @Id
    @GeneratedValue
    private int id;

    @NonNull
    private double latitude;

    @NonNull
    private double longitude;

    @NonNull
    private String name;
}