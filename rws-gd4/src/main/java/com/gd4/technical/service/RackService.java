package com.gd4.technical.service;

import org.springframework.data.domain.Page;

import com.gd4.technical.model.RackModel;

public interface RackService {
    public RackModel create(RackModel rack);

    public RackModel read(String uuId);

    public RackModel update(RackModel rack);

    public Page<RackModel> byWarehouse(String uuid);

    public void delete(String uuId);

    public Page<RackModel> allRacks(int page, int size, String warehouseUuid);
}
