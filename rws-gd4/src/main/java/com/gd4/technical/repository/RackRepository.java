package com.gd4.technical.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gd4.technical.model.RackModel;

@Repository
public interface RackRepository extends JpaRepository<RackModel, Long> {
    public Optional<RackModel> findByUuid(String uuid);

    public Page<RackModel> findByWarehouseUuid(String warehouseUuid, Pageable pageable);
}
