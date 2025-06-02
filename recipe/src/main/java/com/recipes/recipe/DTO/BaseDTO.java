package com.recipes.recipe.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class BaseDTO {

    private static final String format_date = "EEE dd, YYYY";

    private UUID id;
    @JsonFormat(pattern = format_date)
    private LocalDateTime created_at;

    @JsonFormat(pattern = format_date)
    private LocalDateTime updated_at;
}