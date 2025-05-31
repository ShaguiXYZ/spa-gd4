package com.gd4.technical.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import com.gd4.technical.api.WarehouseApi;
import com.gd4.technical.api.model.WarehouseFamilyEnum;
import com.gd4.technical.model.WarehouseModel;
import com.gd4.technical.service.WarehouseService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class WarehouseController implements WarehouseApi {
    private final WarehouseService warehouseService;

    @Override
    public WarehouseModel create(WarehouseModel warehouse) {
        return warehouseService.create(warehouse);
    }

    @Override
    public WarehouseModel read(String uuId) {
        if (uuId == null || uuId.trim().isEmpty()) {
            throw new IllegalArgumentException("UUID must not be null or empty");
        }

        return warehouseService.read(uuId);
    }

    @Override
    public WarehouseModel update(WarehouseModel warehouse) {
        return warehouseService.update(warehouse);
    }

    @Override
    public void delete(String uuId) {
        if (uuId == null || uuId.isEmpty()) {
            throw new IllegalArgumentException("UUID must not be null or empty");
        }

        warehouseService.delete(uuId);
    }

    @Override
    public Page<WarehouseModel> getAllWarehouses(int page, int size) {
        return warehouseService.allWarehouses(page, size);
    }

    @Override
    public Set<String> getRackList(WarehouseFamilyEnum family, int size) {
        return warehouseService.allRackPermutationBy(family, size).stream()
                .sorted()
                .collect(Collectors.toCollection(java.util.LinkedHashSet::new));
    }
}
