package com.piyush.foodify.ui.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import com.piyush.foodify.R;

public class MapActivity extends AppCompatActivity {
    private MapView mapView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Configuration.getInstance().setUserAgentValue(getPackageName());
        setContentView(R.layout.activity_map);
        
        mapView = findViewById(R.id.map_view);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setMultiTouchControls(true);
        
        // Set default location (Delhi)
        GeoPoint startPoint = new GeoPoint(28.6139, 77.2090);
        mapView.getController().setZoom(15.0);
        mapView.getController().setCenter(startPoint);
        
        // Add sample restaurant marker
        Marker marker = new Marker(mapView);
        marker.setPosition(startPoint);
        marker.setTitle("Sample Restaurant");
        marker.setSnippet("Delicious food here!");
        mapView.getOverlays().add(marker);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }
}