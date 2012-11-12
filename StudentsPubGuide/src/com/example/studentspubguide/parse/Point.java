package com.example.studentspubguide.parse;

import com.google.android.maps.GeoPoint;




public class Point  {
	private String coordinates;
	private int longtitude, latitude;
	private GeoPoint geopoint;
		
	public Point(String retezec) {
		String[] splitted = retezec.split(",");
		longtitude = Integer.parseInt(splitted[0]);
		latitude = Integer.parseInt(splitted[1]);
		System.out.println("kuk"+(int)(latitude * 1E6));
		System.out.println("kuk"+(int)(longtitude * 1E6));
		geopoint = new GeoPoint((int)(latitude * 1E6), (int)(longtitude * 1E6));
		
		
		
	}
	public GeoPoint getGeoPoint(){
		return geopoint;
	}
	

}
