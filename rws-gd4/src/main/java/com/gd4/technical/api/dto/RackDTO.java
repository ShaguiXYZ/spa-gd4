package com.gd4.technical.api.dto;

import com.gd4.technical.api.model.RackTypeEnum;

import lombok.Data;

@Data
public class RackDTO {
    private String uuid;
    private RackTypeEnum type;
    private String warehouseUuid;
}
