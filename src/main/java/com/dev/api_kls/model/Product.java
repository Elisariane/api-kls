package com.dev.api_kls.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Product {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @NotBlank
    private String name;

    private String description;

    @NotNull
    @Min(value = 1)
    private Double price;

    @NotNull
    @Min(value = 1)
    private Integer quantity;

    private String brand;

    private boolean isActiveInMarket = true;

}
