package com.example.studentspubguide.parse;




public class Point  {
	private String coordinates;
	private int longtitude, latitude;
		
	public Point(String retezec) {
		String[] splitted = retezec.split(",");
		longtitude = Integer.parseInt(splitted[0]);
		latitude = Integer.parseInt(splitted[1]);
		
	}
	

}
