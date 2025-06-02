package com.gd4.technical.service;

import java.util.Set;

import org.springframework.data.domain.Page;

import com.gd4.technical.api.model.WarehouseFamilyEnum;
import com.gd4.technical.model.WarehouseModel;

public interface WarehouseService {
    public WarehouseModel create(WarehouseModel warehouse);

    public WarehouseModel read(String uuId);

    public WarehouseModel update(String uuid, WarehouseModel warehouse);

    public void delete(String uuId);

    public Page<WarehouseModel> allWarehouses(int page, int size);

    public Set<String> allRackPermutationBy(String uuId);

    public Set<String> allRackPermutationBy(WarehouseFamilyEnum family, int length);
}
