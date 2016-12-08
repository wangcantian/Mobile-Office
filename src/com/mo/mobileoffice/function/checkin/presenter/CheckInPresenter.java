package com.mo.mobileoffice.function.checkin.presenter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.mo.mobileoffice.R;
import com.mo.mobileoffice.common.mvp.BaseMvpPresenter;
import com.mo.mobileoffice.common.net.RequestArr.ACTION;
import com.mo.mobileoffice.common.tool.GsonTool;
import com.mo.mobileoffice.common.tool.StringTool;
import com.mo.mobileoffice.function.checkin.bean.CheckIn_DataSend_Request;
import com.mo.mobileoffice.function.checkin.bean.CheckIn_DataSend_Respond;
import com.mo.mobileoffice.function.checkin.bean.CheckIn_HistoryData_Request;
import com.mo.mobileoffice.function.checkin.bean.CheckIn_HistoryData_Respond;
import com.mo.mobileoffice.function.checkin.bean.CheckIn_HistoryData_Respond.HistoryDate;
import com.mo.mobileoffice.function.checkin.bean.LocationDataSave;
import com.mo.mobileoffice.function.checkin.contract.CheckInContract;
import com.mo.mobileoffice.function.user.model.UserModel;
import com.squareup.okhttp.Request;

/**
 * guo
 * 
 * @author Administrator2016/5/8
 * 
 */
public class CheckInPresenter extends BaseMvpPresenter<CheckInContract.View>
		implements CheckInContract.Presenter {

	private UserModel mModel;
	private String today = StringTool.DataToString2(new Date());

	public CheckInPresenter(Context context) {
		super(context);
		mModel = new UserModel(context);
	}

	private boolean isFirst = false;
	// 声明AMapLocationClient类对象
	public AMapLocationClient mLocationClient = null;
	private LocationDataSave mDataSave = new LocationDataSave();
	// 声明定位回调监听器
	public AMapLocationListener mLocationListener = new AMapLocationListener() {

		@Override
		public void onLocationChanged(AMapLocation amapLocation) {
			if (mDataSave == null) {
				mDataSave = new LocationDataSave();
			}
			if (amapLocation != null) {
				if (amapLocation.getErrorCode() == 0) {
					// 定位成功回调信息，设置相关消息
					amapLocation.getLocationType();// 获取当前定位结果来源，如网络定位结果，详见定位类型表
					double lat = amapLocation.getLatitude();// 获取纬度
					double lng = amapLocation.getLongitude();// 获取经度
					amapLocation.getAccuracy();// 获取精度信息
					SimpleDateFormat df = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					Date date = new Date(amapLocation.getTime());
					df.format(date);// 定位时间
					StringTool.DateToString1(new Date(amapLocation.getTime()));
					String address = amapLocation.getAddress();// 地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
					amapLocation.getCountry();// 国家信息
					amapLocation.getProvince();// 省信息
					amapLocation.getCity();// 城市信息
					amapLocation.getDistrict();// 城区信息
					amapLocation.getStreet();// 街道信息
					amapLocation.getStreetNum();// 街道门牌号信息
					amapLocation.getCityCode();// 城市编码
					amapLocation.getAdCode();// 地区编码
					String mLocationAOI = amapLocation.getAoiName();// 获取当前定位点的AOI信息
					if (!isFirst) {
						mDataSave.setAddress(address);
						mDataSave.setLat(lat);
						mDataSave.setLng(lng);
						mDataSave.setLocationAOI(mLocationAOI);
						if (mLocationAOI != null && !mLocationAOI.equals("")) {
							storeName.setText(mLocationAOI);
						} else {
							storeName.setText(amapLocation.getCity()
									+ amapLocation.getDistrict()
									+ amapLocation.getStreet()
									+ amapLocation.getStreetNum());
						}
						addressName.setText(address);
						getView().initLatLng(lat, lng);
						isFirst = true;
					}
				} else {
					// 显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
					Log.e("AmapError", "location Error, ErrCode:"
							+ amapLocation.getErrorCode() + ", errInfo:"
							+ amapLocation.getErrorInfo());
				}
			}
		}
	};

	// 声明mLocationOption对象
	public AMapLocationClientOption mLocationOption = null;

	public void initLocation() {
		if (mLocationClient == null) {
			// 初始化定位
			mLocationClient = new AMapLocationClient(mContext);
			// 设置定位回调监听
			mLocationClient.setLocationListener(mLocationListener);
			// 初始化定位参数
			mLocationOption = new AMapLocationClientOption();
			// 设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
			mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
			// 设置是否返回地址信息（默认返回地址信息）
			mLocationOption.setNeedAddress(true);
			// 设置是否只定位一次,默认为false
			mLocationOption.setOnceLocation(false);
			// 设置是否强制刷新WIFI，默认为强制刷新
			mLocationOption.setWifiActiveScan(true);
			// 设置是否允许模拟位置,默认为false，不允许模拟位置
			mLocationOption.setMockEnable(false);
			// 设置定位间隔,单位毫秒,默认为2000ms
			mLocationOption.setInterval(10000);
		}
		// 给定位客户端对象设置定位参数
		mLocationClient.setLocationOption(mLocationOption);
		// 启动定位
		mLocationClient.startLocation();
	}

	// 签到访问服务器操作
	@Override
	public int doCheckInHttpRequest(String address, double lat, double lng) {
		mDataSave.setLat(lat);
		mDataSave.setLng(lng);
		mDataSave.setAddress(address);
		CheckIn_DataSend_Request mRequest = new CheckIn_DataSend_Request(
				mModel.getUserId(), mModel.getUserToken(), mDataSave.getLat(),
				mDataSave.getLng(), "", "", mDataSave.getAddress());
		request(ACTION.ACTION_CHECKIN, mRequest, new CallBack() {
			
			@Override
			public void onResponse(String responseStr) throws IOException {
				CheckIn_DataSend_Respond respond = GsonTool.getData(responseStr, CheckIn_DataSend_Respond.class);
				if (respond.getFlag() == 200) {
					getUIHandler().post(new Runnable() {
						@Override
						public void run() {
							getView().setCheckInButtonStyle();
							// 当传递成功的时候再往日历上边打钩
							getView().changeCalendarUI();
						}
					});
				} else {
					getUIHandler().post(new Runnable() {
						@Override
						public void run() {
							toastShowOnUI(R.string.checkin_fail);
						}
					});
				}
			}
			
			@Override
			public void onFailure(Request request, IOException exception) {
				
			}
		});
		return 0;
	}

	@Override
	public void destroyLocationClient() {
		if (mLocationClient != null) {
			mLocationClient.stopLocation();
			mLocationClient.onDestroy();
			mLocationClient = null;
		}
	}

	// 页面跳转
	@Override
	public void doPageChange() {
		getView().changeToAmap();
	}

	// 设置的年月
	@Override
	public void doDateSet() {
		getView().dateSet(new Date());
	}

	@Override
	public void startLocation() {
		if (mLocationClient != null && !mLocationClient.isStarted()) {
			mLocationClient.startLocation();
		}
	}

	@Override
	public void stopLocation() {
		if (mLocationClient != null && mLocationClient.isStarted()) {
			mLocationClient.stopLocation();
		}
	}

	private TextView storeName;
	private TextView addressName;

	@Override
	public void setAddress(TextView storeName, TextView addressName) {
		this.storeName = storeName;
		this.addressName = addressName;
	}

	private CheckIn_HistoryData_Respond respond = null;
	private ArrayList<String> dateList = new ArrayList<>();

	private boolean isCheckIn = false;

	@Override
	public CheckIn_HistoryData_Respond getCheckInHistory() {
		CheckIn_HistoryData_Request mHistoryRequest = new CheckIn_HistoryData_Request(
				mModel.getUserId(), mModel.getUserToken());
		request(ACTION.ACTION_CHECKINHISTORY, mHistoryRequest, new CallBack() {
			
			@Override
			public void onResponse(String responseStr) throws IOException {
				if (responseStr != null && !responseStr.equals("")) {
					respond = GsonTool.getData(responseStr,
							CheckIn_HistoryData_Respond.class);
					if (respond.getFlag() == 200) {
						ArrayList<HistoryDate> data = respond.getData();
						if (data != null && data.size() > 0) {
							for (int i = 0; i < data.size(); i++) {
								String[] d = data.get(i).getTime().split(" ");
								if (d[0].equals(today)) {
									isCheckIn = true;
								}
								dateList.add(i, d[0]);
							}
							getUIHandler().post(new Runnable() {

								@Override
								public void run() {
									if (isCheckIn) {
										getView().setCheckInButtonStyle();
									}
									if (dateList != null && dateList.size() > 0) {
										getView().addCalendarHistory(dateList);
									}
								}
							});
						}
					}
				}
			}
			
			@Override
			public void onFailure(Request request, IOException exception) {
				
			}
		});
		return respond;
	}

	@Override
	public void detachView(boolean retainInstance) {

	}
}
