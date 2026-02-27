package com.recipes.recipe.models.base;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@NoArgsConstructor
public class Base {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    @Getter
    @Setter
    private UUID id;

    @CreatedDate
    @Getter
    private LocalDateTime created_at;

    @LastModifiedDate
    @Getter
    private LocalDateTime updated_at;

    @PrePersist
    public void onCreated(){
        this.created_at = LocalDateTime.now();
        this.updated_at = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdated(){
        this.updated_at = LocalDateTime.now();
    }
}
