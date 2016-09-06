package com.cyril.training.day24;


import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity
        extends
        FragmentActivity
        implements
        OnMapReadyCallback

{
    final String LOG_TAG="day24Log";

    private Marker mMarker;
    private GoogleMap mMap;
    private LatLng mCameraCenterLatLng;

    private ImageButton mMapMarkerImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mMapMarkerImageView = (ImageButton) findViewById(R.id.marker_ImageButton);

        mMapMarkerImageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(mCameraCenterLatLng != null)
                {
                    // Showing SHORT Toast of Map-camera's position.
                    shortToastandVerboseLog("Lat: " +String.valueOf(mCameraCenterLatLng.latitude)
                            +", Lng: " +String.valueOf(mCameraCenterLatLng.longitude));
                }
            }
        });

        // To enable/disable custom place-picker.s
        ToggleButton toggleButton= (ToggleButton) findViewById(R.id.toggleButton);

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                {
                    // Removing the current marker
                    if(mMarker != null) {mMarker.remove();}

                    //  Setting the Map-marker-ImageView visible
                    mMapMarkerImageView.setVisibility(View.VISIBLE);
                }
                else
                {
                    mMapMarkerImageView.setVisibility(View.GONE);
//                        if(mLatLng != null) {mLatLng= mCameraCenterLatLng;}


                    // Removing previous Marker.
                    if(mMarker != null) {mMarker.remove();}

                    // Setting Marker on the Map.
                    mMarker= mMap.addMarker(new MarkerOptions()
                              .position(mCameraCenterLatLng)
                              .icon(BitmapDescriptorFactory.fromResource(R.drawable.location_icon_blue_small))
                              .title("Marker at Location.")
                              .draggable(true));

                    mMarker.setPosition(mCameraCenterLatLng);

                    mMap.moveCamera(CameraUpdateFactory.newLatLng(mCameraCenterLatLng));

                    // Showing SHORT Toast of Map-camera's position.
                    shortToastandVerboseLog("Lat: " +String.valueOf(mCameraCenterLatLng.latitude)
                                           +", Lng: " +String.valueOf(mCameraCenterLatLng.longitude));

                    mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener()
                    {
                        @Override
                        public void onMarkerDragStart(Marker marker)
                        {

                        }

                        @Override
                        public void onMarkerDrag(Marker marker)
                        {

                        }

                        @Override
                        public void onMarkerDragEnd(Marker marker)
                        {
                            LatLng markerPositionAfterDrag= marker.getPosition();

                            // Toasting Marker's position after drag.
                            shortToastandVerboseLog("Lat: " +String.valueOf(markerPositionAfterDrag.latitude)
                                    +", Lng: " +String.valueOf(markerPositionAfterDrag.longitude));
                        }
                    });
                }
            }
        });

        // Obtaining the SupportMapFragment and getting notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     *
     * Used to obtain the Map-camera's target location.
     */

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        // Getting the value of Map-camera's position. (Screen center)
        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener()
        {
            @Override
            public void onCameraChange(CameraPosition cameraPosition)
            {
                mCameraCenterLatLng = new LatLng(cameraPosition.target.latitude, cameraPosition.target.longitude);
            }
        });
    }

    /**
     * Method to show a Verbose Log and a SHORT Toast.
     * @param message
     */
    private void shortToastandVerboseLog(String message)
    {
        Log.v(LOG_TAG, message);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
