package com.mo.mobileoffice.function.checkin.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ListView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.SupportMapFragment;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.poisearch.PoiSearch.OnPoiSearchListener;
import com.amap.api.services.poisearch.PoiSearch.SearchBound;
import com.mo.mobileoffice.R;
import com.mo.mobileoffice.common.adapter.CommonAdapter;
import com.mo.mobileoffice.common.mvp.MvpIdeaFragment;
import com.mo.mobileoffice.function.checkin.bean.PoiAddressSave;
import com.mo.mobileoffice.function.checkin.contract.MapContract;
import com.mo.mobileoffice.function.checkin.contract.MapContract.Presenter;
import com.mo.mobileoffice.function.checkin.presenter.MapPresenter;

public class MapFragment extends MvpIdeaFragment<MapContract.Presenter>
		implements MapContract.View, LocationSource, AMapLocationListener,
		OnPoiSearchListener {

	private AMap map;
	private UiSettings mUiSettings;
	private ListView listView;

	@Override
	protected void init() {
		listView = findViewById(R.id.listView);
		getPresenter().initMapStyle();
		getPresenter().initMyLocation();

		setRight(getResources().getString(R.string.ok));
	}

	@Override
	public void onClick(View v) {

	}

	@Override
	public void rightOnClick() {
		if (poiAddressList != null && poiAddressList.size() > 0) {
			Intent intent = new Intent();
			getPresenter().setForResult(getActivity(), intent);
		}
	}

	@Override
	protected int setContentViewId() {
		return R.layout.fragment_checkin_map;
	}

	@Override
	public void initMapLocationStyle() {
		if (map == null) {
			map = ((SupportMapFragment) (getActivity()
					.getSupportFragmentManager()).findFragmentById(R.id.map))
					.getMap();
			mUiSettings = map.getUiSettings();
			mUiSettings.setZoomControlsEnabled(false);
			mUiSettings.setMyLocationButtonEnabled(true);
			map.moveCamera(CameraUpdateFactory.zoomTo(16));
			MyLocationStyle myLocationStyle = new MyLocationStyle();
			myLocationStyle.myLocationIcon(BitmapDescriptorFactory
					.fromResource(R.drawable.location_marker));
			myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
			myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
			map.setMyLocationStyle(myLocationStyle);
			map.setLocationSource(this);
			map.setMyLocationEnabled(true);// 重点没这个方法位置图标显示不了
		}
	}

	private AMapLocationClient mLocationClient = null;
	private AMapLocationClientOption locationClientOption = null;

	@Override
	public void initLocation() {
		if (mLocationClient == null) {
			mLocationClient = new AMapLocationClient(getActivity()
					.getApplicationContext());
			locationClientOption = new AMapLocationClientOption();
			locationClientOption.setNeedAddress(true);
			// 设置是否只定位一次,默认为false
			locationClientOption.setOnceLocation(false);
			// 设置是否强制刷新WIFI，默认为强制刷新
			locationClientOption.setWifiActiveScan(true);
			// 设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
			locationClientOption
					.setLocationMode(AMapLocationMode.Hight_Accuracy);
			// 设置是否允许模拟位置,默认为false，不允许模拟位置
			locationClientOption.setMockEnable(false);
			// 设置定位间隔,单位毫秒,默认为2000ms
			locationClientOption.setInterval(100000);
		}
		mLocationClient.setLocationListener(this);
		mLocationClient.setLocationOption(locationClientOption);
		mLocationClient.startLocation();
	}

	private boolean locationChange = false;

	// private AMapLocation location;

	@Override
	public void onLocationChanged(AMapLocation location) {
		// this.location = location;
		if (mListener != null && location != null) {
			mListener.onLocationChanged(location);
		}
		if (!locationChange) {
			if (location != null) {
				LatLng latlng = new LatLng(location.getLatitude(),
						location.getLongitude());
				CameraPosition cameraPosition = new CameraPosition.Builder()
						.target(latlng).zoom(18).build();
				map.animateCamera(CameraUpdateFactory
						.newCameraPosition(cameraPosition));
				if (latlng != null) {
					String keyWord = location.getCity();
					PoiSearch.Query query = new PoiSearch.Query(
							keyWord,
							"汽车服务|汽车销售|汽车维修|摩托车服务|餐饮服务|购物服务|生活服务|体育休闲服务|医疗保健服务|风景名胜|商务住宅|政府机构及社会团体|科教文化服务|交通设施服务|金融保险服务|公司企业|道路附属设施|地名地址信息|公共设施",
							"");// (第三个参数)这里可以传空字符串，空字符串代表全国在全国范围内进行搜索
					query.setPageNum(1);
					query.setPageSize(40);
					PoiSearch poiSearch = new PoiSearch(getActivity(), query);
					poiSearch.setBound(new SearchBound(new LatLonPoint(
							latlng.latitude, latlng.longitude), 3000));
					poiSearch.setOnPoiSearchListener(this);
					poiSearch.searchPOIAsyn();
				}
				locationChange = true;
			}
		}

	}

	@Override
	public void onResume() {
		if (mLocationClient != null) {
			mLocationClient.startLocation();
		}
		super.onResume();
	}

	@Override
	public void onPause() {
		if (mLocationClient != null) {
			mLocationClient.stopLocation();
		}
		super.onPause();
	}

	@Override
	public void onDestroy() {
		if (mLocationClient != null) {
			mLocationClient.stopLocation();
			mLocationClient.onDestroy();
		}
		mLocationClient = null;
		locationClientOption = null;
		super.onDestroy();
	}

	private OnLocationChangedListener mListener = null;

	@Override
	public void activate(OnLocationChangedListener mListener) {
		this.mListener = mListener;
	}

	@Override
	public void deactivate() {
		mListener = null;
	}

	// 监听位置周围的信息
	@Override
	public void onPoiItemSearched(PoiItem item, int arg1) {

	}

	private List<PoiAddressSave> poiAddressList = new ArrayList<>();

	@Override
	public void onPoiSearched(PoiResult result, int code) {
		if (result != null && result.getPois().size() > 0) {
			ArrayList<PoiItem> list = result.getPois();
			for (int i = 0; i < list.size(); i++) {
				String storeName = list.get(i).getTitle();
				String addressName = list.get(i).getCityName()
						+ list.get(i).getAdName() + list.get(i).getSnippet();
				LatLonPoint point = list.get(i).getLatLonPoint();
				PoiAddressSave poi = new PoiAddressSave(storeName, addressName,
						point);
				poiAddressList.add(poi);
			}
			if (poiAddressList != null && poiAddressList.size() > 0) {
				CommonAdapter<PoiAddressSave> adapter = getPresenter().initAdapter(getActivity(), 
						poiAddressList, R.layout.listitem_fragmentmap);
				getPresenter().adapterListView(listView, adapter);
			}
		}
	}

	@Override
	protected Presenter createPresenter() {
		return new MapPresenter(getActivity());
	}

}
