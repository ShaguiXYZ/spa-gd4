package com.gd4.technical.api;

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

import com.gd4.technical.api.dto.RackDTO;

import feign.Headers;

@Headers("Content-Type: application/json;charset=UTF-8")
@RequestMapping(path = { "/api/rack" }, produces = { MediaType.APPLICATION_JSON_VALUE })
@ResponseBody
public interface RackApi {
    @PostMapping("create/{warehouseUuId}")
    @ResponseStatus(HttpStatus.CREATED)
    public RackDTO create(@PathVariable String warehouseUuId, @RequestBody RackDTO rack);

    @GetMapping("read/{uuId}")
    public RackDTO read(@PathVariable String uuId);

    @PutMapping("update/{warehouseUuId}")
    @ResponseStatus(HttpStatus.OK)
    public RackDTO update(@PathVariable String warehouseUuId, @RequestBody RackDTO rack);

    @DeleteMapping("delete/{uuId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String uuId);

    @GetMapping("getAll/{warehouseUuId}")
    public Page<RackDTO> getAllRacks(@PathVariable String warehouseUuId, @RequestParam(required = true) int page,
            @RequestParam(required = true) int size);
}
