package com.gd4.technical.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.gd4.technical.api.model.WarehouseFamilyEnum;
import com.gd4.technical.model.WarehouseModel;
import com.gd4.technical.service.WarehouseService;

public class WarehouseControllerTest {
    @InjectMocks
    private WarehouseController warehouseController;

    @Mock
    private WarehouseService warehouseService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    public void createTest() {
        WarehouseModel warehouse = new WarehouseModel();
        warehouse.setUuid("test-uuid");
        warehouse.setFamily(WarehouseFamilyEnum.EST);
        warehouse.setSize(100);

        when(warehouseService.create(any(WarehouseModel.class))).thenReturn(warehouse);

        WarehouseModel createdWarehouse = warehouseController.create(warehouse);

        assert createdWarehouse != null;
        assert createdWarehouse.getUuid().equals("test-uuid");
        assert createdWarehouse.getFamily() == WarehouseFamilyEnum.EST;
        assert createdWarehouse.getSize() == 100;
    }

}
