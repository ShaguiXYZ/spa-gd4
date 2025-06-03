package com.gd4.technical.controller;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import com.gd4.technical.api.WarehouseApi;
import com.gd4.technical.api.dto.WarehouseDTO;
import com.gd4.technical.api.model.WarehouseFamilyEnum;
import com.gd4.technical.service.WarehouseService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class WarehouseController implements WarehouseApi {
    private final WarehouseService warehouseService;

    @Override
    public WarehouseDTO create(WarehouseDTO warehouse) {
        return warehouseService.create(warehouse);
    }

    @Override
    public WarehouseDTO read(String uuId) {
        if (uuId == null || uuId.trim().isEmpty()) {
            throw new IllegalArgumentException("UUID must not be null or empty");
        }

        return warehouseService.read(uuId);
    }

    @Override
    public WarehouseDTO update(String uuid, WarehouseDTO warehouse) {
        return warehouseService.update(uuid, warehouse);
    }

    @Override
    public void delete(String uuId) {
        if (uuId == null || uuId.isEmpty()) {
            throw new IllegalArgumentException("UUID must not be null or empty");
        }

        warehouseService.delete(uuId);
    }

    @Override
    public Page<WarehouseDTO> getAllWarehouses(int page, int size) {
        return warehouseService.allWarehouses(page, size);
    }

    @Override
    public Set<String> getRackList(WarehouseFamilyEnum family, int size) {
        return warehouseService.allRackPermutationBy(family, size).stream()
                .sorted()
                .collect(Collectors.toCollection(java.util.LinkedHashSet::new));
    }
}
