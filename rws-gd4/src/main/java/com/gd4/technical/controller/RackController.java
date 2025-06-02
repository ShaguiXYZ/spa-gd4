package com.gd4.technical.controller;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import com.gd4.technical.api.RackApi;
import com.gd4.technical.api.dto.RackDTO;
import com.gd4.technical.service.RackService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class RackController implements RackApi {
    private final RackService rackService;

    @Override
    public RackDTO create(String warehouseUuId, RackDTO rack) {
        if (rack == null || rack.getUuid() == null || rack.getUuid().isEmpty()) {
            throw new IllegalArgumentException("Rack or UUID must not be null or empty");
        }

        rack.setWarehouseUuid(warehouseUuId);

        return rackService.create(rack);
    }

    @Override
    public RackDTO read(String uuId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'read'");
    }

    @Override
    public RackDTO update(String warehouseUuId, RackDTO rack) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(String uuId) {
        if (uuId == null || uuId.isEmpty()) {
            throw new IllegalArgumentException("UUID must not be null or empty");
        }

        rackService.delete(uuId);
    }

    @Override
    public Page<RackDTO> getAllRacks(String warehouseUuId, int page, int size) {
        if (warehouseUuId == null || warehouseUuId.isEmpty()) {
            throw new IllegalArgumentException("Warehouse UUID must not be null or empty");
        }
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("Page number must be non-negative and size must be positive");
        }

        return rackService.byWarehouse(warehouseUuId, page, size);
    }
}
