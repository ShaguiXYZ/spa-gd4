package com.gd4.technical.controller;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import com.gd4.technical.api.RackApi;
import com.gd4.technical.model.RackModel;
import com.gd4.technical.service.RackService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class RackController implements RackApi {
    private final RackService rackService;

    @Override
    public RackModel create(RackModel rack) {
        if (rack == null || rack.getUuid() == null || rack.getUuid().isEmpty()) {
            throw new IllegalArgumentException("Rack or UUID must not be null or empty");
        }

        return rackService.create(rack);
    }

    @Override
    public RackModel read(String uuId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'read'");
    }

    @Override
    public RackModel update(RackModel rack) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(String uuId) {
        if (uuId == null || uuId.isEmpty()) {
            throw new IllegalArgumentException("UUID must not be null or empty");
        }

        rackService.delete(uuId);
    }

    @Override
    public Page<RackModel> getAllracks(int page, int size) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllracks'");
    }
}
