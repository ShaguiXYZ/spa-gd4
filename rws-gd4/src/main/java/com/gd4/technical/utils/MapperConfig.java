package com.gd4.technical.utils;

import org.springframework.stereotype.Component;

import com.gd4.technical.repository.WarehouseRepository;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class MapperConfig {
	private final WarehouseRepository warehouseRepository;

	@PostConstruct
	public void init() {
		Mapper.setConfig(this);
	}

	public WarehouseRepository getWarehouseRepository() {
		return warehouseRepository;
	}
}
