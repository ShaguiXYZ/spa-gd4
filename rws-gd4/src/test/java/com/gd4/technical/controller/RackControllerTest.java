package com.gd4.technical.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.gd4.technical.api.dto.RackDTO;
import com.gd4.technical.api.model.RackTypeEnum;
import com.gd4.technical.service.RackService;

public class RackControllerTest {
    @InjectMocks
    private RackController rackController;

    @Mock
    private RackService rackService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    public void createTest() {
        RackDTO rack = new RackDTO();
        rack.setUuid("test-uuid");
        rack.setType(RackTypeEnum.A);
        rack.setWarehouseUuid("test-warehouse-uuid");

        when(rackService.create(any(RackDTO.class))).thenReturn(rack);

        RackDTO createdRack = rackController.create("test-warehouse-uuid", rack);

        assert createdRack != null;
        assert createdRack.getUuid().equals("test-uuid");
        assert createdRack.getType() == RackTypeEnum.A;
        assert createdRack.getWarehouseUuid().equals(createdRack.getWarehouseUuid());
    }
}
