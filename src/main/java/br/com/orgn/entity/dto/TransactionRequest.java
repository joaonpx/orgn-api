package br.com.orgn.entity.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record TransactionRequest(
        UUID id,

        @NotNull(message = "Date is required")
        LocalDate date,

        @NotBlank(message = "Type is required")
        String type,

        @NotBlank(message = "Description is required")
        String description,

        @NotNull(message = "Amount is required")
        Float amount
) {
}
