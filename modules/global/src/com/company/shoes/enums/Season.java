package com.company.shoes.enums;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum Season implements EnumClass<String> {

    SUMMER("summer"),
    DEMISEASON("demiseason"),
    WINTER("winter");

    private String id;

    Season(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static Season fromId(String id) {
        for (Season at : Season.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}