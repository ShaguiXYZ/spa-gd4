package com.gd4.technical.api.dto;

import com.gd4.technical.api.model.WarehouseFamilyEnum;

import lombok.Data;

@Data
public class WarehouseDTO {
    private Long id;
    private String uuid;
    private String client;
    private Integer size;
    private WarehouseFamilyEnum family;
}
