package com.gd4.technical.api.dto;

import com.gd4.technical.api.model.WarehouseFamilyEnum;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WarehouseDTOTest {

    @Test
    public void testWarehouseDTOGettersAndSetters() {
        WarehouseDTO warehouse = new WarehouseDTO();

        // Test setters y getters
        warehouse.setId(1L);
        warehouse.setUuid("test-uuid-123");
        warehouse.setClient("Test Client");
        warehouse.setSize(100);
        warehouse.setFamily(WarehouseFamilyEnum.EST);

        // Verificar getters
        assertEquals(1L, warehouse.getId());
        assertEquals("test-uuid-123", warehouse.getUuid());
        assertEquals("Test Client", warehouse.getClient());
        assertEquals(100, warehouse.getSize());
        assertEquals(WarehouseFamilyEnum.EST, warehouse.getFamily());
    }

    @Test
    public void testWarehouseDTOEqualsAndHashCode() {
        WarehouseDTO warehouse1 = new WarehouseDTO();
        warehouse1.setId(1L);
        warehouse1.setUuid("test-uuid");
        warehouse1.setClient("Client");
        warehouse1.setSize(50);
        warehouse1.setFamily(WarehouseFamilyEnum.ROB);

        WarehouseDTO warehouse2 = new WarehouseDTO();
        warehouse2.setId(1L);
        warehouse2.setUuid("test-uuid");
        warehouse2.setClient("Client");
        warehouse2.setSize(50);
        warehouse2.setFamily(WarehouseFamilyEnum.ROB);

        // Test equals
        assertEquals(warehouse1, warehouse2);
        assertEquals(warehouse1.hashCode(), warehouse2.hashCode());
    }

    @Test
    public void testWarehouseDTOToString() {
        WarehouseDTO warehouse = new WarehouseDTO();
        warehouse.setId(1L);
        warehouse.setUuid("test-uuid");
        warehouse.setClient("Test Client");
        warehouse.setSize(75);
        warehouse.setFamily(WarehouseFamilyEnum.EST);

        String toString = warehouse.toString();

        // Verificar que toString contiene los valores
        assertNotNull(toString);
        assertTrue(toString.contains("test-uuid"));
        assertTrue(toString.contains("Test Client"));
        assertTrue(toString.contains("75"));
        assertTrue(toString.contains("EST"));
    }

    @Test
    public void testWarehouseDTODefaultConstructor() {
        WarehouseDTO warehouse = new WarehouseDTO();

        assertNull(warehouse.getId());
        assertNull(warehouse.getUuid());
        assertNull(warehouse.getClient());
        assertNull(warehouse.getSize());
        assertNull(warehouse.getFamily());
    }
}