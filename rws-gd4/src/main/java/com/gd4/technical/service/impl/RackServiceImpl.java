package com.gd4.technical.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.gd4.technical.api.dto.RackDTO;
import com.gd4.technical.model.RackModel;
import com.gd4.technical.repository.RackRepository;
import com.gd4.technical.repository.WarehouseRepository;
import com.gd4.technical.service.RackService;
import com.gd4.technical.utils.Mapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RackServiceImpl implements RackService {
    private final RackRepository rackRepository;

    private final WarehouseRepository warehouseRepository;

    @Override
    public RackDTO create(RackDTO rack) {
        rackRepository.findByUuid(rack.getUuid())
                .ifPresent(existingRack -> {
                    throw new IllegalArgumentException("Rack with UUID " + rack.getUuid() + " already exists.");
                });

        return Mapper.parse(rackRepository.save(Mapper.parse(rack)));
    }

    @Override
    public RackDTO read(String uuId) {
        return Mapper.parse(rackRepository.findByUuid(uuId)
                .orElseThrow(() -> new IllegalArgumentException("Rack not found with UUID: " + uuId)));
    }

    @Override
    public RackDTO update(RackDTO rack) {
        RackModel existingRack = rackRepository.findByUuid(rack.getUuid())
                .orElseThrow(() -> new IllegalArgumentException("Rack not found with UUID: " + rack.getUuid()));

        existingRack.setType(rack.getType());

        return Mapper.parse(rackRepository.save(existingRack));
    }

    @Override
    public Page<RackDTO> byWarehouse(String warehouseUuid, int page, int size) {
        return rackRepository.findByWarehouseUuid(warehouseUuid, PageRequest.of(page, size)).map(Mapper::parse);
    }

    @Override
    public Page<RackDTO> allRacks(int page, int size, String warehouseUuid) {
        return rackRepository.findAll(PageRequest.of(page, size)).map(Mapper::parse);
    }

    @Override
    public void delete(String uuId) {
        RackModel existingRack = rackRepository.findByUuid(uuId)
                .orElseThrow(() -> new IllegalArgumentException("Rack not found with UUID: " + uuId));

        rackRepository.delete(existingRack);
    }
}
