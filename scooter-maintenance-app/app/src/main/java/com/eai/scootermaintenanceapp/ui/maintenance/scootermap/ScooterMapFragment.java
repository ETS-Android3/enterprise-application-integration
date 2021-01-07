package com.eai.scootermaintenanceapp.ui.maintenance.scootermap;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.transition.Slide;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.eai.scootermaintenanceapp.R;
import com.eai.scootermaintenanceapp.data.model.Scooter;
import com.eai.scootermaintenanceapp.data.model.ScooterStatus;
import com.eai.scootermaintenanceapp.ui.maintenance.ScooterViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class ScooterMapFragment extends Fragment implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {

    private static final String LOG_TAG = ScooterMapFragment.class.getSimpleName();

    private ScooterViewModel mScooterViewModel;
    private Scooter mSelectedScooter;

    private GoogleMap mMap;
    private Marker mMarker;

    private ExtendedFloatingActionButton mUpdateFab;

    private final ActivityResultLauncher<String> mRequestLocationPermission =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    mMap.setMyLocationEnabled(true);
                    mMap.getUiSettings().setMyLocationButtonEnabled(true);
                } else {
                    Toast.makeText(getContext(), getString(R.string.location_permission_denied), Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scooter_map, container, false);

        mUpdateFab = view.findViewById(R.id.update_fab);
        mUpdateFab.setOnClickListener(view1 -> {
            if (mSelectedScooter == null) {
                return;
            }

            // Show scooter update dialog
            MaterialAlertDialogBuilder editDialog = new MaterialAlertDialogBuilder(getContext());

            editDialog.setTitle(getString(R.string.update_status_dialog_title, mSelectedScooter.getId()));
            editDialog.setNeutralButton(R.string.action_cancel, ((dialogInterface, i) -> {
                // Reset scooter status
                mSelectedScooter.setStatus(ScooterStatus.BROKEN);
            }));

            String[] scooterStatusValues = ScooterStatus.getNames();
            editDialog.setSingleChoiceItems(scooterStatusValues, 0, (dialogInterface, i) -> {
                mSelectedScooter.setStatus(ScooterStatus.values()[i]);
            });

            editDialog.setPositiveButton(R.string.action_confirm, (dialogInterface, i) -> {
                // Remove scooter if it is no longer broken
                if (mSelectedScooter.getStatus() == ScooterStatus.FUNCTIONAL) {
                    mScooterViewModel.removeScooter(mSelectedScooter);
                    updateScooterMarker();
                }
            });

            editDialog.show();
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mScooterViewModel = new ViewModelProvider(requireActivity()).get(ScooterViewModel.class);
        mScooterViewModel.getSelectedScooter().observe(getViewLifecycleOwner(), selectedScooter -> {
            mSelectedScooter = selectedScooter;
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Request location permission and enable location features if granted
        mRequestLocationPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION);

        mMap.getUiSettings().setMapToolbarEnabled(false);

        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapClickListener(this);

        updateScooterMarker();

        if (mMarker != null && mMarker.isInfoWindowShown()) {
            mUpdateFab.setVisibility(View.VISIBLE);
        } else {
            mUpdateFab.setVisibility(View.GONE);
        }
    }

    public void updateScooterMarker() {
        boolean firstPaint = true;

        if (mMap == null) {
            return;
        }

        // Remove old selected scooter marker
        if (mMarker != null) {
            firstPaint = false;
            mMarker.remove();
        }

        if (mSelectedScooter == null) {
            mUpdateFab.setVisibility(View.GONE);
            return;
        }

        // Add scooter marker, and move the camera
        LatLng coordinates = new LatLng(mSelectedScooter.getLatitude(), mSelectedScooter.getLongitude());
        mMarker = mMap.addMarker(new MarkerOptions()
                .position(coordinates)
                .title(getString(R.string.scooter_id, mSelectedScooter.getId())));
        mMarker.setSnippet(mSelectedScooter.getFailureReason());

        mMarker.showInfoWindow();

        // Animate camera movement if it's not the first call to updateScooterMarker
        if (firstPaint) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(coordinates));
        } else {
            mMap.animateCamera(CameraUpdateFactory.newLatLng(coordinates));
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.d(LOG_TAG, marker.getTitle());

        // Show update fab when marker is selected
        Transition transition = new Slide(Gravity.BOTTOM);
        transition.setDuration(100);
        transition.addTarget(R.id.update_fab);

        ViewGroup parent = getView().findViewById(R.id.map_container);
        TransitionManager.beginDelayedTransition(parent, transition);

        mUpdateFab.setVisibility(View.VISIBLE);

        return false;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        // Remove update fab if marker is not selected
        Transition transition = new Slide(Gravity.BOTTOM);
        transition.setDuration(100);
        transition.addTarget(R.id.update_fab);

        ViewGroup parent = getView().findViewById(R.id.map_container);
        TransitionManager.beginDelayedTransition(parent, transition);

        mUpdateFab.setVisibility(View.GONE);
    }
}