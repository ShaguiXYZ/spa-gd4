package com.gd4.technical.model;

import java.util.List;

import com.gd4.technical.api.model.WarehouseFamilyEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Data
@Entity
@Table(name = "warehouses", uniqueConstraints = { @UniqueConstraint(columnNames = { "uuid" }),
        @UniqueConstraint(columnNames = { "client", "family" }) })
public class WarehouseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String uuid;

    @Column(nullable = false)
    private String client;

    @Column(nullable = false)
    private Integer size;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private WarehouseFamilyEnum family;

    @OneToMany(mappedBy = "warehouse", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RackModel> racks;
}
