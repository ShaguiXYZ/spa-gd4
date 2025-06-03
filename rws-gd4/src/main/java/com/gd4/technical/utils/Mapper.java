package com.gd4.technical.utils;

import com.gd4.technical.api.dto.RackDTO;
import com.gd4.technical.api.dto.WarehouseDTO;
import com.gd4.technical.model.RackModel;
import com.gd4.technical.model.WarehouseModel;

public class Mapper {

    private static MapperConfig config;

    private Mapper() {
    }

    protected static void setConfig(MapperConfig config) {
        Mapper.config = config;
    }

    public static RackModel parse(RackDTO source) {
        if (source == null) {
            return null;
        }

        RackModel rack = new RackModel();
        rack.setUuid(source.getUuid());
        rack.setType(source.getType());

        if (source.getWarehouseUuid() != null && !source.getWarehouseUuid().isEmpty()) {
            WarehouseModel warehouse = config.getWarehouseRepository().findByUuid(source.getWarehouseUuid())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Warehouse not found with UUID: " + source.getWarehouseUuid()));

            rack.setWarehouse(warehouse);
        }

        return rack;
    }

    public static RackDTO parse(RackModel source) {
        if (source == null) {
            return null;
        }

        RackDTO rack = new RackDTO();
        rack.setUuid(source.getUuid());
        rack.setType(source.getType());

        if (source.getWarehouse() != null) {
            rack.setWarehouseUuid(source.getWarehouse().getUuid());
        }

        return rack;
    }

    public static WarehouseModel parse(WarehouseDTO source) {
        if (source == null) {
            return null;
        }

        WarehouseModel warehouse = new WarehouseModel();
        warehouse.setUuid(source.getUuid());
        warehouse.setClient(source.getClient());
        warehouse.setSize(source.getSize());
        warehouse.setFamily(source.getFamily());

        return warehouse;
    }

    public static WarehouseDTO parse(WarehouseModel source) {
        if (source == null) {
            return null;
        }

        WarehouseDTO warehouse = new WarehouseDTO();
        warehouse.setUuid(source.getUuid());
        warehouse.setClient(source.getClient());
        warehouse.setSize(source.getSize());
        warehouse.setFamily(source.getFamily());

        return warehouse;
    }
}
