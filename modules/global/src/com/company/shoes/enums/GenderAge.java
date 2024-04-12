package com.company.shoes.enums;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum GenderAge implements EnumClass<String> {

    MALE("male"),
    FEMALE("female"),
    BOYS("boys"),
    GIRLS("girls");

    private String id;

    GenderAge(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static GenderAge fromId(String id) {
        for (GenderAge at : GenderAge.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}