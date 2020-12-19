package com.eai.scootermaintenanceapp.data.model;

import java.util.Arrays;

public enum ScooterStatus {
    BROKEN,
    FUNCTIONAL;

    public static String[] getNames() {
        return Arrays.stream(ScooterStatus.values()).map(Enum::name).toArray(String[]::new);
    }
}
