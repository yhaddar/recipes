package com.recipes.recipe.Model;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@Data
public abstract class BaseModel {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private UUID id;

    @CreatedDate
    private LocalDateTime created_at;
    @LastModifiedDate
    private LocalDateTime updated_at;

    @PrePersist
    public void onCreate(){
        this.created_at = LocalDateTime.now();
        this.updated_at = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate(){
        this.created_at = LocalDateTime.now();
        this.updated_at = LocalDateTime.now();
    }
}