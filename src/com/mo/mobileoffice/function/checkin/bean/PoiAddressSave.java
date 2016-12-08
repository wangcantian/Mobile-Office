package com.mo.mobileoffice.function.checkin.bean;

import java.io.Serializable;

import com.amap.api.services.core.LatLonPoint;

public class PoiAddressSave implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4154806747359707210L;
	private String storeName;
	private String addressName;
	private LatLonPoint point;

	public PoiAddressSave(String storeName, String addressName,
			LatLonPoint point) {
		super();
		this.storeName = storeName;
		this.addressName = addressName;
		this.point = point;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	public LatLonPoint getPoint() {
		return point;
	}

	public void setPoint(LatLonPoint point) {
		this.point = point;
	}

}
