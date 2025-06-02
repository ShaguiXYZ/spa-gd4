package com.gd4.technical.utils;

import org.springframework.stereotype.Component;

import com.gd4.technical.service.WarehouseService;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class MapperConfig {
	private final WarehouseService warehouseService;

	@PostConstruct
	public void init() {
		Mapper.setConfig(this);
	}

	public WarehouseService getWarehouseService() {
		return warehouseService;
	}
}
