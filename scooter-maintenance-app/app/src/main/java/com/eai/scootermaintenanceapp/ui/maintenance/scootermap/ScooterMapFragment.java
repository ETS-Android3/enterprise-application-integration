package com.eai.scootermaintenanceapp.ui.maintenance.scootermap;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class ScooterMapFragment extends Fragment implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {

    private static final String LOG_TAG = ScooterMapFragment.class.getSimpleName();

    private boolean mFirstPaint = true;

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

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }

    // TODO: add logic to receive incoming scooter breakdown notifications and place them on the map
    // probably requires an adapter of the messages to a Scooter object with coordinates,
    // scooter id/name and the breakdown reason (nullable)

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

        if (mFirstPaint) {
            LatLng dummyScooterLocation = new LatLng(52.1326, 5.2913);
            this.addScooterLocation(dummyScooterLocation);
        }

        mFirstPaint = false;

        if (mMarker != null && mMarker.isInfoWindowShown()) {
            mUpdateFab.setVisibility(View.VISIBLE);
        }
    }

    // TODO: replace argument with scooter object
    public void addScooterLocation(LatLng coordinates) {
        // Add a scooter marker, and move the camera
        mMarker = mMap.addMarker(new MarkerOptions()
                .position(coordinates)
                .title("Broken Scooter 123"));
        mMarker.setSnippet("Jammed exhaust system.");

        mMap.moveCamera(CameraUpdateFactory.newLatLng(coordinates));
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        mFirstPaint = true;
    }
}