package org.ies.tierno.library.controller.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record BookRequest(
        @NotBlank(message = "ISBN is required")
        @Pattern(regexp = "\\d{13}", message = "ISBN must have 13 digits")
        String isbn,

        @NotBlank(message = "Title is required")
        @Size(max = 200, message = "Title must not exceed 200 characters")
        String title,

        @NotBlank(message = "Author is required")
        String author,

        @NotNull(message = "Year is required")
        @Min(value = 1450, message = "Year is not valid")
        Integer year,

        @NotNull(message = "Purchase price is required")
        @PositiveOrZero(message = "Purchase price must not be negative")
        BigDecimal purchasePrice) {}