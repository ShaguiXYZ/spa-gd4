package com.gd4.technical.api.dto;

import com.gd4.technical.api.model.RackTypeEnum;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RackDTOTest {

    @Test
    public void testRackDTOGettersAndSetters() {
        RackDTO rack = new RackDTO();

        // Test setters y getters
        rack.setUuid("rack-uuid-123");
        rack.setType(RackTypeEnum.A);
        rack.setWarehouseUuid("warehouse-uuid-456");

        // Verificar getters
        assertEquals("rack-uuid-123", rack.getUuid());
        assertEquals(RackTypeEnum.A, rack.getType());
        assertEquals("warehouse-uuid-456", rack.getWarehouseUuid());
    }

    @Test
    public void testRackDTOEqualsAndHashCode() {
        RackDTO rack1 = new RackDTO();
        rack1.setUuid("rack-uuid");
        rack1.setType(RackTypeEnum.B);
        rack1.setWarehouseUuid("warehouse-uuid");

        RackDTO rack2 = new RackDTO();
        rack2.setUuid("rack-uuid");
        rack2.setType(RackTypeEnum.B);
        rack2.setWarehouseUuid("warehouse-uuid");

        // Test equals
        assertEquals(rack1, rack2);
        assertEquals(rack1.hashCode(), rack2.hashCode());
    }

    @Test
    public void testRackDTOToString() {
        RackDTO rack = new RackDTO();
        rack.setUuid("rack-uuid-123");
        rack.setType(RackTypeEnum.A);
        rack.setWarehouseUuid("warehouse-uuid-456");

        String toString = rack.toString();

        // Verificar que toString contiene los valores
        assertNotNull(toString);
        assertTrue(toString.contains("rack-uuid-123"));
        assertTrue(toString.contains("A"));
        assertTrue(toString.contains("warehouse-uuid-456"));
    }

    @Test
    public void testRackDTODefaultConstructor() {
        RackDTO rack = new RackDTO();

        assertNull(rack.getUuid());
        assertNull(rack.getType());
        assertNull(rack.getWarehouseUuid());
    }

    @Test
    public void testRackDTOWithDifferentTypes() {
        RackDTO rackA = new RackDTO();
        rackA.setType(RackTypeEnum.A);

        RackDTO rackB = new RackDTO();
        rackB.setType(RackTypeEnum.B);

        assertEquals(RackTypeEnum.A, rackA.getType());
        assertEquals(RackTypeEnum.B, rackB.getType());
        assertNotEquals(rackA.getType(), rackB.getType());
    }
}