package com.yash.inventory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WarehouseRequest {

    @NotBlank
    private String name;

    private String location;

}