package com.mo.mobileoffice.function.checkin.bean;

public class LocationDataSave {
	private String address;
	private String locationAOI;
	private double lat;
	private double lng;
	@SuppressWarnings("unused")
	private String type;
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLocationAOI() {
		return locationAOI;
	}
	public void setLocationAOI(String locationAOI) {
		this.locationAOI = locationAOI;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	
}
