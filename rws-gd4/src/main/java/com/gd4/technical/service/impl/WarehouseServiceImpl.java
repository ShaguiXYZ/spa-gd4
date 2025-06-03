package com.gd4.technical.service.impl;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.gd4.technical.api.dto.WarehouseDTO;
import com.gd4.technical.api.model.RackTypeEnum;
import com.gd4.technical.api.model.WarehouseFamilyEnum;
import com.gd4.technical.model.WarehouseModel;
import com.gd4.technical.repository.WarehouseRepository;
import com.gd4.technical.service.WarehouseService;
import com.gd4.technical.utils.Mapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class WarehouseServiceImpl implements WarehouseService {
    private final WarehouseRepository warehouseRepository;

    @Override
    public WarehouseDTO create(WarehouseDTO warehouse) {
        warehouseRepository.findByUuid(warehouse.getUuid())
                .ifPresent(existingWarehouse -> {
                    throw new IllegalArgumentException(
                            "Warehouse with UUID " + warehouse.getUuid() + " already exists.");
                });

        return Mapper.parse(warehouseRepository.save(Mapper.parse(warehouse)));
    }

    @Override
    public WarehouseDTO read(String uuId) {
        return Mapper.parse(warehouseRepository.findByUuid(uuId)
                .orElseThrow(() -> new IllegalArgumentException("Warehouse not found with UUID: " + uuId)));
    }

    @Override
    public WarehouseDTO update(String uuid, WarehouseDTO warehouse) {
        if (uuid == null || uuid.isEmpty()) {
            throw new IllegalArgumentException("UUID must not be null or empty");
        }

        if (warehouse == null) {
            throw new IllegalArgumentException("Warehouse must not be null");
        }

        if (!uuid.equals(warehouse.getUuid())) {
            throw new IllegalArgumentException("UUID in path does not match UUID in warehouse object");
        }

        WarehouseModel existingWarehouse = warehouseRepository.findByUuid(uuid)
                .orElseThrow(
                        () -> new IllegalArgumentException("Warehouse not found with UUID: " + warehouse.getUuid()));

        existingWarehouse.setClient(warehouse.getClient());
        existingWarehouse.setFamily(warehouse.getFamily());
        existingWarehouse.setSize(warehouse.getSize());

        return Mapper.parse(warehouseRepository.save(existingWarehouse));
    }

    @Override
    public void delete(String uuId) {
        WarehouseModel existingWarehouse = warehouseRepository.findByUuid(uuId)
                .orElseThrow(() -> new IllegalArgumentException("Warehouse not found with UUID: " + uuId));

        warehouseRepository.delete(existingWarehouse);
    }

    @Override
    public Page<WarehouseDTO> allWarehouses(int page, int size) {
        return warehouseRepository.findAll(PageRequest.of(page, size)).map(Mapper::parse);
    }

    @Override
    public Set<String> allRackPermutationBy(String uuId) {
        WarehouseModel warehouse = warehouseRepository.findByUuid(uuId)
                .orElseThrow(() -> new IllegalArgumentException("Warehouse not found with id: " + uuId));

        Set<RackTypeEnum> permutationElements = warehouse.getFamily().rackTypes();

        return getPermutations(permutationElements, warehouse.getSize());
    }

    @Override
    public Set<String> allRackPermutationBy(WarehouseFamilyEnum family, int length) {
        return getPermutations(family.rackTypes(), length);
    }

    private Set<String> getPermutations(Set<RackTypeEnum> permutationElements, int length) {
        if (length == 0) {
            return new LinkedHashSet<>();
        }

        if (length == 1) {
            return permutationElements.stream()
                    .map(RackTypeEnum::name)
                    .collect(LinkedHashSet::new, Set::add, Set::addAll);
        }

        Set<String> permutations = getPermutations(permutationElements, --length);
        Set<String> newPermutations = permutationElements.stream()
                .flatMap(element -> permutations.stream().map(p -> element.name() + p))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        return newPermutations;
    }
}
