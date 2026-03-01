package com.recipes.recipe.models;

import com.recipes.recipe.models.base.Base;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "address")
public class Address extends Base {

    @Column(unique = false, nullable = false, name = "city")
    private String city;

    @Column(unique = false, nullable = false, name = "country")
    private String country;

    @Column(unique = false, nullable = true, name = "state")
    private String state;

    @Column(unique = false, nullable = true, name = "postal_code")
    private int postal_code;

    @OneToOne(mappedBy = "address")
    private User user;
}
