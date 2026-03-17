package com.yash.inventory.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderRequest {

    @NotNull
    private Long productId;

    @NotNull
    private Integer quantity;

    @NotNull
    private String type; // PURCHASE / SALE
}