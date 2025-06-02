package com.gd4.technical.service;

import org.springframework.data.domain.Page;

import com.gd4.technical.api.dto.RackDTO;

public interface RackService {
    public RackDTO create(RackDTO rack);

    public RackDTO read(String uuId);

    public RackDTO update(RackDTO rack);

    public Page<RackDTO> byWarehouse(String uuid, int page, int size);

    public void delete(String uuId);

    public Page<RackDTO> allRacks(int page, int size, String warehouseUuid);
}
