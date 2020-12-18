package com.eai.scootermaintenanceapp.util;

import androidx.fragment.app.Fragment;

import com.eai.scootermaintenanceapp.R;
import com.eai.scootermaintenanceapp.ui.maintenance.scooterlist.ScooterListFragment;
import com.eai.scootermaintenanceapp.ui.maintenance.scootermap.ScooterMapFragment;

import java.util.HashMap;
import java.util.Map;

public enum BottomNavigationPosition {
    SCOOTER_LIST(R.id.scooter_list),
    SCOOTER_MAP(R.id.scooter_map);

    private static final Map<Integer, BottomNavigationPosition> BY_ITEM_ID = new HashMap<>();

    static {
        for (BottomNavigationPosition bottomNavigationPosition : values()) {
            BY_ITEM_ID.put(bottomNavigationPosition.itemId, bottomNavigationPosition);
        }
    }

    public final int itemId;

    BottomNavigationPosition(Integer itemId) {
        this.itemId = itemId;
    }

    public static BottomNavigationPosition valueOfItemId(Integer itemId) {
        return BY_ITEM_ID.get(itemId);
    }

    public String getTag() {
        switch (this) {
            case SCOOTER_LIST:
                return ScooterListFragment.class.getSimpleName();
            case SCOOTER_MAP:
                return ScooterMapFragment.class.getSimpleName();
        }

        return null;
    }

    public Fragment createFragment() {
        switch (this) {
            case SCOOTER_LIST:
                return new ScooterListFragment();
            case SCOOTER_MAP:
                return new ScooterMapFragment();
        }

        return null;
    }
}
