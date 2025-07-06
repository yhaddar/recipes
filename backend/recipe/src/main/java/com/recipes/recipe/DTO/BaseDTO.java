package com.recipes.recipe.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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

//    private static final String format_date = "EEE dd, YYYY";

    private UUID id;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime created_at;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updated_at;
}