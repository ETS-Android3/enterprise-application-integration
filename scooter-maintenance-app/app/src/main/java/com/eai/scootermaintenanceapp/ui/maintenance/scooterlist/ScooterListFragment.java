package com.eai.scootermaintenanceapp.ui.maintenance.scooterlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eai.scootermaintenanceapp.R;
import com.eai.scootermaintenanceapp.data.model.Scooter;
import com.eai.scootermaintenanceapp.ui.maintenance.ScooterItemClickListener;
import com.eai.scootermaintenanceapp.ui.maintenance.ScooterViewModel;

public class ScooterListFragment extends Fragment implements ScooterItemClickListener {

    private ScooterViewModel mScooterViewModel;
    private ScooterListAdapter mScooterListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scooter_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.scooter_list);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        mScooterListAdapter = new ScooterListAdapter(this);
        recyclerView.setAdapter(mScooterListAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mScooterViewModel = new ViewModelProvider(requireActivity()).get(ScooterViewModel.class);
        mScooterViewModel.getScooterList().observe(getViewLifecycleOwner(), scooterList -> {
            mScooterListAdapter.updateScooterList(scooterList);
        });

        mScooterViewModel.getSelectedScooter().observe(getViewLifecycleOwner(), selectedScooter -> {
            mScooterListAdapter.setSelectedScooter(selectedScooter);
        });
    }

    @Override
    public void onScooterItemClick(Scooter scooter) {
        mScooterViewModel.selectScooter(scooter);
    }
}
