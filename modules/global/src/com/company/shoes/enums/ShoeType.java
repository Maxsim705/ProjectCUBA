package com.company.shoes.enums;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum ShoeType implements EnumClass<String> {

    SNEAKERS("sneakers"),
    SHOES("shoes"),
    BOOTS("boots"),
    SANDALS("sandals"),
    GALOSHES("galoshes"),
    SLIPPERS("slippers"),
    ESPADRILLES("espadrilles"),
    LOAFERS("loafers"),
    BALLET_FLATS("balletFlats"),
    ORTHOPEDIC_SHOES("orthopedicShoes"),
    MOCCASINS("moccasins"),
    FLIP_FLOPS("flip-flops"),
    OXFORDS("oxfords"),
    COURT_SHOES("courtShoes"),
    HIKING_BOOTS("hikingBoots"),
    PLATFORM_SHOES("platformShoes");
    private String id;

    ShoeType(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static ShoeType fromId(String id) {
        for (ShoeType at : ShoeType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}