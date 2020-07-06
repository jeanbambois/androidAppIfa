package com.bever.appifa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class mapActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap mapAPI;
    SupportMapFragment  mapFragment ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapAPI);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapAPI = googleMap;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ArrayList<Double> coordinatesX = new ArrayList<>();
            ArrayList<Double> coordinatesY = new ArrayList<>();
            coordinatesX = (ArrayList<Double>) getIntent().getSerializableExtra("coordinatesX");
            coordinatesY = (ArrayList<Double>) getIntent().getSerializableExtra("coordinatesY");
            if(coordinatesX.size() != 0 && coordinatesY.size() != 0) {
                for (int i = 0; i < coordinatesX.size(); i++) {
                    LatLng tempCoord = new LatLng(coordinatesX.get(i), coordinatesY.get(i));
                    mapAPI.addMarker(new MarkerOptions().position(tempCoord).title("Coordonnées: " + i));
                }
            }
        }
    }
}
