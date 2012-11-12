package com.example.studentspubguide;

import java.util.ArrayList;
import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;

import com.example.studentspubguide.mapa.MyItemizedOverlay;
import com.example.studentspubguide.parse.Parser;
import com.example.studentspubguide.parse.Placemark;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MapaActivity extends MapActivity {
	protected MapView map;
    private MyLocationOverlay myLocationOverlay;
    private Parser parser;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        setupMapView();
        setupMyLocation();
        parser = new Parser();
        
        List<Overlay> mapOverlays = map.getOverlays();
        Drawable drawable = this.getResources().getDrawable(R.drawable.location_marker2);
        MyItemizedOverlay itemizedoverlay = new MyItemizedOverlay(drawable, this);
        
        
        //GeoPoint point = new GeoPoint(50412307,16163707);
        //OverlayItem overlayitem = new OverlayItem(point, "Nazdar", "Jsem Náchodská hospoda");
        
       // GeoPoint point2 = new GeoPoint(50420724,16164316);
        //OverlayItem overlayitem2 = new OverlayItem(point2, "Nazdar", "Jsem druhá Náchodská hospoda");
        
       // GeoPoint point3 = new GeoPoint(50203424,15830116);
       // OverlayItem overlayitem3 = new OverlayItem(point3, "Nazdar", "Jsem první hradecká");
        
        ArrayList<Placemark> places = parser.getMyPlacmarks();
        for (Placemark place : places) {
        	itemizedoverlay.addOverlay(new OverlayItem(new GeoPoint(place.getLatitude(), place.getLongtitude()), place.getName(), place.getDescription()));
        	//itemizedoverlay.addOverlay(new OverlayItem(place.getPoint().getGeoPoint(),place.getName(), place.getDescription()));
		}
        
       // itemizedoverlay.addOverlay(overlayitem3);
        //itemizedoverlay.addOverlay(overlayitem);
        //itemizedoverlay.addOverlay(overlayitem2);
        mapOverlays.add(itemizedoverlay);
        
    }

    private void setupMapView() {
    	this.map = (MapView) findViewById(R.id.mapview);
	    map.setBuiltInZoomControls(true);
		
	}

	private void setupMyLocation() {
		this.myLocationOverlay = new MyLocationOverlay(this, map);
        myLocationOverlay.enableMyLocation();
        myLocationOverlay.runOnFirstFix(new Runnable() {
          //@Override
          public void run() {
            GeoPoint currentLocation = myLocationOverlay.getMyLocation();
            map.getController().animateTo(currentLocation);
            map.getController().setZoom(14);
            map.getOverlays().add(myLocationOverlay);
            //System.out.println("AHOJ" + currentLocation.getLatitudeE6());
            //System.out.println("ahoj" + currentLocation.getLongitudeE6());
            //myLocationOverlay.setFollowing(true);
          }
        });
		
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_mapa, menu);
        return true;
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected void onResume() {
		myLocationOverlay.enableMyLocation();
	    myLocationOverlay.enableCompass();
	    super.onResume();
	}
}
