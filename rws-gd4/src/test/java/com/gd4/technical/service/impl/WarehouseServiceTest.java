package com.gd4.technical.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.gd4.technical.api.dto.WarehouseDTO;
import com.gd4.technical.api.model.WarehouseFamilyEnum;
import com.gd4.technical.model.WarehouseModel;
import com.gd4.technical.repository.WarehouseRepository;
import com.gd4.technical.utils.Mapper;

public class WarehouseServiceTest {
	@InjectMocks
	private WarehouseServiceImpl warehouseService;

	@Mock
	private WarehouseRepository warehouseRepository;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void createTest() {
		WarehouseDTO warehouse = new WarehouseDTO();
		warehouse.setUuid("test-uuid");
		warehouse.setFamily(WarehouseFamilyEnum.EST);
		warehouse.setSize(100);

		when(warehouseRepository.findByUuid(anyString())).thenReturn(Optional.empty());
		when(warehouseRepository.save(any())).thenReturn(Mapper.parse(warehouse));

		WarehouseDTO createdWarehouse = warehouseService.create(warehouse);

		assert createdWarehouse != null;
		assert createdWarehouse.getUuid().equals("test-uuid");
		assert createdWarehouse.getFamily() == WarehouseFamilyEnum.EST;
		assert createdWarehouse.getSize() == 100;
	}

	@Test
	public void readTest() {
		WarehouseDTO warehouse = new WarehouseDTO();
		warehouse.setUuid("test-uuid");
		warehouse.setFamily(WarehouseFamilyEnum.EST);
		warehouse.setSize(100);

		when(warehouseRepository.findByUuid(anyString())).thenReturn(Optional.of(Mapper.parse(warehouse)));

		WarehouseDTO foundWarehouse = warehouseService.read("test-uuid");

		assert foundWarehouse != null;
		assert foundWarehouse.getUuid().equals("test-uuid");
		assert foundWarehouse.getFamily() == WarehouseFamilyEnum.EST;
		assert foundWarehouse.getSize() == 100;
	}

	@Test
	public void updateTest() {
		String uuid = "test-uuid";

		WarehouseDTO warehouse = new WarehouseDTO();
		warehouse.setUuid(uuid);
		warehouse.setFamily(WarehouseFamilyEnum.EST);
		warehouse.setSize(100);

		WarehouseModel warehouseModel = Mapper.parse(warehouse);

		when(warehouseRepository.findByUuid(anyString())).thenReturn(Optional.of(warehouseModel));
		when(warehouseRepository.save(any())).thenReturn(warehouseModel);

		WarehouseDTO updatedWarehouse = warehouseService.update(uuid, warehouse);

		assert updatedWarehouse != null;
		assert updatedWarehouse.getUuid().equals("test-uuid");
		assert updatedWarehouse.getFamily() == WarehouseFamilyEnum.EST;
		assert updatedWarehouse.getSize() == 100;
	}

	@Test
	public void deleteTest() {
		WarehouseModel warehouse = new WarehouseModel();
		warehouse.setUuid("test-uuid");
		warehouse.setFamily(WarehouseFamilyEnum.EST);
		warehouse.setSize(100);

		when(warehouseRepository.findByUuid(anyString())).thenReturn(Optional.of(warehouse));
		doNothing().when(warehouseRepository).delete(any(WarehouseModel.class));

		warehouseService.delete("test-uuid");

		Mockito.verify(warehouseRepository).delete(any(WarehouseModel.class));
	}
}
