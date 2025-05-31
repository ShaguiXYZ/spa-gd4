package com.gd4.technical.api.model;

import java.util.Set;

public enum WarehouseFamilyEnum {
    EST(Set.of(RackTypeEnum.A, RackTypeEnum.B, RackTypeEnum.C)),
    ROB(Set.of(RackTypeEnum.A, RackTypeEnum.C, RackTypeEnum.D));

    private final Set<RackTypeEnum> racks;

    private WarehouseFamilyEnum(Set<RackTypeEnum> racks) {
        this.racks = racks;
    }

    public Set<RackTypeEnum> rackTypes() {
        return racks;
    }
}
