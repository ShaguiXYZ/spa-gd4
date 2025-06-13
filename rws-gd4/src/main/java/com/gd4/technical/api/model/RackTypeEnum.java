package com.gd4.technical.api.model;

public enum RackTypeEnum {
    A, B(2), C, D(4);

    private int _size;

    private RackTypeEnum() {
        this._size = 1;
    }

    private RackTypeEnum(int size) {
        this._size = size;
    }

    public int size() {
        return _size;
    }
}
