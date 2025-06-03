package com.gd4.technical.service;

import java.util.Set;

import org.springframework.data.domain.Page;

import com.gd4.technical.api.dto.WarehouseDTO;
import com.gd4.technical.api.model.WarehouseFamilyEnum;

public interface WarehouseService {
    public WarehouseDTO create(WarehouseDTO warehouse);

    public WarehouseDTO read(String uuId);

    public WarehouseDTO update(String uuid, WarehouseDTO warehouse);

    public void delete(String uuId);

    public Page<WarehouseDTO> allWarehouses(int page, int size);

    public Set<String> allRackPermutationBy(String uuId);

    public Set<String> allRackPermutationBy(WarehouseFamilyEnum family, int length);
}
