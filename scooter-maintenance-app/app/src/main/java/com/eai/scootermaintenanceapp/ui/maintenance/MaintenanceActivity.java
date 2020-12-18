package com.eai.scootermaintenanceapp.ui.maintenance;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.eai.scootermaintenanceapp.R;
import com.eai.scootermaintenanceapp.ui.login.LoginViewModel;
import com.eai.scootermaintenanceapp.ui.login.LoginViewModelFactory;
import com.eai.scootermaintenanceapp.util.BottomNavigationPosition;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MaintenanceActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String LOG_TAG = MaintenanceActivity.class.getSimpleName();
    private static final String KEY_ITEM_ID = "keyItemId";

    private Integer mNavItemId = BottomNavigationPosition.SCOOTER_LIST.itemId;

    private LoginViewModel mLoginViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);

        mLoginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        initFragment(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            mLoginViewModel.logout();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        // Save current navigation item id
        outState.putInt(KEY_ITEM_ID, mNavItemId);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Restore saved navigation item id to prevent fragment recreation
        mNavItemId = savedInstanceState.getInt(KEY_ITEM_ID, BottomNavigationPosition.SCOOTER_LIST.itemId);
    }

    private void initFragment(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            return;
        }

        switchFragment(BottomNavigationPosition.SCOOTER_LIST);
    }

    private void switchFragment(BottomNavigationPosition bottomNavigationPosition) {
        String fragmentTag = bottomNavigationPosition.getTag();
        Fragment selectedFragment = getSupportFragmentManager().findFragmentByTag(fragmentTag);

        // Create new fragment instance if no saved instance is found
        if (selectedFragment == null) {
            Log.d(LOG_TAG, "Create new " + fragmentTag + " instance");
            selectedFragment = bottomNavigationPosition.createFragment();
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, selectedFragment, fragmentTag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        BottomNavigationPosition bottomNavigationPosition = BottomNavigationPosition.valueOfItemId(item.getItemId());
        mNavItemId = item.getItemId();

        switchFragment(bottomNavigationPosition);

        return true;
    }
}
