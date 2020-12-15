package com.eai.scootermaintenanceapp.ui.map;

import androidx.fragment.app.FragmentActivity;
import androidx.transition.Fade;
import androidx.transition.Slide;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.eai.scootermaintenanceapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {

    private static final String LOG_TAG = MapsActivity.class.getSimpleName();

    private GoogleMap mMap;
    private ExtendedFloatingActionButton mUpdateFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mUpdateFab = findViewById(R.id.update_fab);
    }

    // TODO: add logic to receive incoming scooter breakdown notifications and place them on the map
    // probably requires an adapter of the messages to a Scooter object with coordinates,
    // scooter id/name and the breakdown reason (nullable)

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMapToolbarEnabled(false);

        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapClickListener(this);

        LatLng dummyScooterLocation = new LatLng(52.1326, 5.2913);
        this.addScooterLocation(dummyScooterLocation);
    }

    // TODO: replace argument with scooter object
    public void addScooterLocation(LatLng coordinates) {
        // Add a scooter marker, and move the camera
        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(coordinates)
                .title("Broken Scooter 123"));
        marker.setSnippet("Jammed exhaust system.");

        mMap.moveCamera(CameraUpdateFactory.newLatLng(coordinates));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.d(LOG_TAG, marker.getTitle());

        // Show update fab when marker is selected
        Transition transition = new Slide(Gravity.BOTTOM);
        transition.setDuration(100);
        transition.addTarget(R.id.update_fab);

        ViewGroup parent = findViewById(R.id.map_container);
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

        ViewGroup parent = findViewById(R.id.map_container);
        TransitionManager.beginDelayedTransition(parent, transition);

        mUpdateFab.setVisibility(View.GONE);
    }
}