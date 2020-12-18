package com.eai.scootermaintenanceapp.ui.maintenance;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.eai.scootermaintenanceapp.data.model.Scooter;
import com.eai.scootermaintenanceapp.data.model.ScooterStatus;

import java.util.ArrayList;
import java.util.List;

public class ScooterViewModel extends ViewModel {

    private MutableLiveData<List<Scooter>> mScooterList;
    private final MutableLiveData<Scooter> mSelectedScooter = new MutableLiveData<>();

    public LiveData<List<Scooter>> getScooterList() {
        if (mScooterList == null) {
            mScooterList = new MutableLiveData<>();
            loadScooterList();
        }

        return mScooterList;
    }

    private void loadScooterList() {
        // TODO: change to real implementation instead of dummy data
        List<Scooter> scooterList = new ArrayList<>();
        scooterList.add(new Scooter(52.1326, 5.2913, "Scooter 1",
                ScooterStatus.BROKEN, "Jammed exhaust system"));
        scooterList.add(new Scooter(52.1526, 5.2913, "Scooter 2",
                ScooterStatus.BROKEN, "Broken steering axle"));
        scooterList.add(new Scooter(52.1326, 5.4913, "Scooter 3",
                ScooterStatus.BROKEN, "Out of gas"));

        mScooterList.setValue(scooterList);
        mSelectedScooter.setValue(scooterList.get(0));
    }

    public void selectScooter(Scooter scooter) {
        mSelectedScooter.setValue(scooter);
    }

    public LiveData<Scooter> getSelectedScooter() {
        return mSelectedScooter;
    }

    public void removeScooter(Scooter scooter) {
        // TODO: add messaging logic

        List<Scooter> oldList = mScooterList.getValue();
        oldList.remove(scooter);

        if (oldList.isEmpty()) {
            mSelectedScooter.setValue(null);
            return;
        }

        mSelectedScooter.setValue(oldList.get(0));
    }
}
