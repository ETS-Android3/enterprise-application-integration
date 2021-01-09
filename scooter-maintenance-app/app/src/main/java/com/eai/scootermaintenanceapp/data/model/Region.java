package com.eai.scootermaintenanceapp.data.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Region {

    NORTH_GRONINGEN("North Groningen", "north-groningen"),
    SOUTH_GRONINGEN("South Groningen", "south-groningen");

    private final String name;
    private final String id;

    public static final List<Region> regions;

    static {
        regions = new ArrayList<>();
        regions.addAll(Arrays.asList(Region.values()));
    }

    Region(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }
}
