package com.example.studentspubguide.parse;

public class Placemark {
	private String coordinates;
	private String name;
	private String description;
	private Point point;
	
	
	
	public Placemark(String coordinates, String name, String description) {
		super();
		this.coordinates = coordinates;
		this.name = name;
		this.description = description;
	}

	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public Placemark(){
		
	}
}
