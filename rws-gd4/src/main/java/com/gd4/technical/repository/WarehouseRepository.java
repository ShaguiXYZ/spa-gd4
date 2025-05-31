package com.gd4.technical.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gd4.technical.model.WarehouseModel;

@Repository
public interface WarehouseRepository extends JpaRepository<WarehouseModel, Long> {
    public Optional<WarehouseModel> findByUuid(String uuid);
}
