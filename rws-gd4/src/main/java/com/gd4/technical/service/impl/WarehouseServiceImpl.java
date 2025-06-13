package com.gd4.technical.service.impl;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        return getPermutations(permutationElements, warehouse.getSize())
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public Set<String> allRackPermutationBy(WarehouseFamilyEnum family, int size) {
        return getPermutations(family.rackTypes(), size).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private Stream<String> getPermutations(Set<RackTypeEnum> elements, int size) {
        if (size <= 0 || elements.isEmpty()) {
            return Stream.of(); // Return empty stream for invalid size or empty set
        }

        return elements.stream().flatMap(element -> {
            if (element.size() > size) {
                return Set.<String>of().stream(); // Skip elements larger than the size
            } else if (element.size() == size) {
                return Set.of(element.name()).stream(); // Return the element itself if it matches the size
            } else {
                return getPermutations(elements, size - element.size())
                        .map(permutation -> element.name() + permutation);
            }
        });
    }
}
