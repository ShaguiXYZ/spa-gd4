package com.gd4.technical.utils;

import com.gd4.technical.api.dto.RackDTO;
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
            WarehouseModel warehouse = config.getWarehouseService().read(source.getWarehouseUuid());

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
}
