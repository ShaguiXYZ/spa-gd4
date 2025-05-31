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

import com.gd4.technical.model.RackModel;

import feign.Headers;

@Headers("Content-Type: application/json;charset=UTF-8")
@RequestMapping(path = { "/api/rack" }, produces = { MediaType.APPLICATION_JSON_VALUE })
@ResponseBody
public interface RackApi {
    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public RackModel create(@RequestBody RackModel rack);

    @GetMapping("read/{uuId}")
    public RackModel read(@PathVariable String uuId);

    @PutMapping("update")
    @ResponseStatus(HttpStatus.OK)
    public RackModel update(@RequestBody RackModel rack);

    @DeleteMapping("delete/{uuId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String uuId);

    @GetMapping("getAll")
    public Page<RackModel> getAllracks(@RequestParam(required = true) int page,
            @RequestParam(required = true) int size);
}
