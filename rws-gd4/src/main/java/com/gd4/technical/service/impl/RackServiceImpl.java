package com.gd4.technical.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.gd4.technical.model.RackModel;
import com.gd4.technical.repository.RackRepository;
import com.gd4.technical.service.RackService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RackServiceImpl implements RackService {
    private final RackRepository rackRepository;

    @Override
    public RackModel create(RackModel rack) {
        rackRepository.findByUuid(rack.getUuid())
                .ifPresent(existingRack -> {
                    throw new IllegalArgumentException("Rack with UUID " + rack.getUuid() + " already exists.");
                });

        return rackRepository.save(rack);
    }

    @Override
    public RackModel read(String uuId) {
        return rackRepository.findByUuid(uuId)
                .orElseThrow(() -> new IllegalArgumentException("Rack not found with UUID: " + uuId));
    }

    @Override
    public RackModel update(RackModel rack) {
        RackModel existingRack = rackRepository.findByUuid(rack.getUuid())
                .orElseThrow(() -> new IllegalArgumentException("Rack not found with UUID: " + rack.getUuid()));

        existingRack.setType(rack.getType());

        return rackRepository.save(existingRack);
    }

    @Override
    public Page<RackModel> byWarehouse(String warehouseUuid) {
        return rackRepository.findByWarehouseUuid(warehouseUuid, PageRequest.of(0, 10));
    }

    @Override
    public Page<RackModel> allRacks(int page, int size, String warehouseUuid) {
        return rackRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public void delete(String uuId) {
        RackModel existingRack = rackRepository.findByUuid(uuId)
                .orElseThrow(() -> new IllegalArgumentException("Rack not found with UUID: " + uuId));

        rackRepository.delete(existingRack);
    }
}
