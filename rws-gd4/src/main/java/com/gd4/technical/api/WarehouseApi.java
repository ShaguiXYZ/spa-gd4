package com.gd4.technical.api;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.gd4.technical.api.model.WarehouseFamilyEnum;
import com.gd4.technical.model.WarehouseModel;

import feign.Headers;

@Headers("Content-Type: application/json;charset=UTF-8")
@RequestMapping(path = { "/api/warehouse" }, produces = { MediaType.APPLICATION_JSON_VALUE })
@ResponseBody
public interface WarehouseApi {
    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public WarehouseModel create(@RequestBody WarehouseModel warehouse);

    @GetMapping("read/{uuId}")
    public WarehouseModel read(@PathVariable String uuId);

    @PutMapping("update")
    @ResponseStatus(HttpStatus.OK)
    public WarehouseModel update(@RequestBody WarehouseModel warehouse);

    @DeleteMapping("delete/{uuId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String uuId);

    @GetMapping("getAll")
    public Page<WarehouseModel> getAllWarehouses(@RequestParam(required = true) int page,
            @RequestParam(required = true) int size);

    @GetMapping("rackList/{family}/{size}")
    public Set<String> getRackList(@PathVariable WarehouseFamilyEnum family, @PathVariable int size);
}
