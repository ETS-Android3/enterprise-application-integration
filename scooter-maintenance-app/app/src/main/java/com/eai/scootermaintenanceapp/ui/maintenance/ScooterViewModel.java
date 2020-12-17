package com.eai.scootermaintenanceapp.ui.maintenance;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.eai.scootermaintenanceapp.data.model.Scooter;

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
        scooterList.add(new Scooter(52.1326, 5.2913, "Scooter 1", "Jammed exhaust system"));
        scooterList.add(new Scooter(52.1526, 5.2913, "Scooter 2", "Broken steering axle"));
        scooterList.add(new Scooter(52.1326, 5.4913, "Scooter 3", "Out of gas"));

        mScooterList.setValue(scooterList);
        mSelectedScooter.setValue(scooterList.get(0));
    }

    public void selectScooter(Scooter scooter) {
        mSelectedScooter.setValue(scooter);
    }

    public LiveData<Scooter> getSelectedScooter() {
        return mSelectedScooter;
    }
}
