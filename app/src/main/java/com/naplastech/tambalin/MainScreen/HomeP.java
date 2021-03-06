package com.naplastech.tambalin.MainScreen;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.naplastech.tambalin.R;
import com.naplastech.tambalin.api.ApiClient;
import com.naplastech.tambalin.api.ApiInterface;
import com.naplastech.tambalin.api.Pengendara;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeP extends AppCompatActivity {
    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private MapView map = null;
    MyLocationNewOverlay myLocationOverlay;
    CompassOverlay mCompassOverlay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        //handle permissions first, before map is created. not depicted here

        //load/initialize the osmdroid configuration, this can be done
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        //setting this before the layout is inflated is a good idea
        //it 'should' ensure that the map has a writable location for the map cache, even without permissions
        //if no tiles are displayed, you can try overriding the cache path using Configuration.getInstance().setCachePath
        //see also StorageUtils
        //note, the load method also sets the HTTP User Agent to your application's package name, abusing osm's
        //tile servers will get you banned based on this string

        //inflate and create the map
        setContentView(R.layout.activity_home_p);


        final TextView nama_user = findViewById(R.id.namaPengendara);

        Integer user_id = getIntent().getIntExtra("user_id", 0);
        
        ApiInterface apiint = ApiClient.getClient().create(ApiInterface.class);
        Call<Pengendara> pengendara = apiint.dataPengendara(user_id);
        pengendara.enqueue(new Callback<Pengendara>() {
            @Override
            public void onResponse(Call<Pengendara> call, Response<Pengendara> response) {
                if (response.body().getStatus() == 1){
                    nama_user.setText(response.body().getNama());
                }
            }

            @Override
            public void onFailure(Call<Pengendara> call, Throwable t) {

            }
        });

        map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);
        IMapController mapController = map.getController();
        mapController.setZoom(13.0);
        GeoPoint startPoint = new GeoPoint(-7.253007965750139, 112.77006039641445);
        mapController.setCenter(startPoint);

        GeoPoint startPoint2 = new GeoPoint(-7.3268735074444375, 112.72224819696986);
        mapController.setCenter(startPoint2);

        // Lokasi Terkini
        this.myLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(this),map);
        this.myLocationOverlay.enableMyLocation();
        map.getOverlays().add(this.myLocationOverlay);

        // Kompas
        // this.mCompassOverlay = new CompassOverlay(this, new InternalCompassOrientationProvider(this), map);
        // this.mCompassOverlay.enableCompass();
        // map.getOverlays().add(this.mCompassOverlay);

        // Marker
        Marker startMarker = new Marker(map);
        startMarker.setPosition(startPoint);
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        map.getOverlays().add(startMarker);
        startMarker.setIcon(getResources().getDrawable(R.drawable.marker));
        startMarker.setTitle("Tambal Ban Cak Alvin \n Desa Ploso, Tambak Sari, Surabaya");

        Marker startMarker2 = new Marker(map);
        startMarker2.setPosition(startPoint2);
        startMarker2.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        map.getOverlays().add(startMarker2);
        startMarker2.setIcon(getResources().getDrawable(R.drawable.marker));
        startMarker2.setTitle("Tambal Ban Pak Jo \n Jl.Gayung Kebonsari X12-2, Surabaya");

        requestPermissionsIfNecessary(new String[]{
                // Wird sp??ter auskommentiert, da GNSS noch nicht ben??tigt wird
                // Manifest.permission.ACCESS_FINE_LOCATION,
                // WRITE_EXTERNAL_STORAGE is required in order to show the map
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                Manifest.permission.ACCESS_FINE_LOCATION
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        map.onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }

    @Override
    public void onPause() {
        super.onPause();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        map.onPause();  //needed for compass, my location overlays, v6.0.0 and up
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            permissionsToRequest.add(permissions[i]);
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    private void requestPermissionsIfNecessary(String[] permissions) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                permissionsToRequest.add(permission);
            }
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }
}