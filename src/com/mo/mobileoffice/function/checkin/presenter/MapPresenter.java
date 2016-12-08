package com.mo.mobileoffice.function.checkin.presenter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.mo.mobileoffice.R;
import com.mo.mobileoffice.common.adapter.CommonAdapter;
import com.mo.mobileoffice.common.adapter.ViewHolder;
import com.mo.mobileoffice.common.mvp.BaseMvpPresenter;
import com.mo.mobileoffice.function.checkin.bean.PoiAddressSave;
import com.mo.mobileoffice.function.checkin.contract.MapContract;

/**
 * map的presenter，处理逻辑代码
 * 
 * @author Administrator guo
 * 
 */
public class MapPresenter extends BaseMvpPresenter<MapContract.View> implements
		MapContract.Presenter {

	public MapPresenter(Context context) {
		super(context);
	}

	@Override
	public void initMapStyle() {
		getView().initMapLocationStyle();
	}

	@Override
	public void initMyLocation() {
		getView().initLocation();
	}

	CommonAdapter<PoiAddressSave> adapter = null;
	int mSelectedPos = 0;
	private List<PoiAddressSave> poiAddressList = null;

	@Override
	public CommonAdapter<PoiAddressSave> initAdapter(Context context,
			List<PoiAddressSave> list, int viewId) {
		poiAddressList = list;
		adapter = new CommonAdapter<PoiAddressSave>(context, list, viewId) {
			@Override
			public void convert(ViewHolder holder, PoiAddressSave item,
					int position) {
				holder.setText(R.id.storeName, item.getStoreName());
				holder.setText(R.id.addressName, item.getAddressName());
				if (mSelectedPos == position) {
					holder.setImageViewVisiable(R.id.isSelectAddress,
							View.VISIBLE);
				} else {
					holder.setImageViewVisiable(R.id.isSelectAddress,
							View.INVISIBLE);
				}
			}
		};
		return adapter;
	}

	@Override
	public <T> void adapterListView(ListView listView,
			final CommonAdapter<T> adapter) {
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mSelectedPos = position;
				adapter.notifyDataSetChanged();
			}

		});
	}

	@Override
	public void setForResult(Activity activity, Intent intent) {
		Intent in = new Intent();
		if (in != null) {
			if (poiAddressList.size() > 0) {
				PoiAddressSave mPoiAddressSave = poiAddressList
						.get(mSelectedPos);
				Bundle bundle = new Bundle();
				bundle.putString("storeName", mPoiAddressSave.getStoreName());
				bundle.putString("addressName",
						mPoiAddressSave.getAddressName());
				bundle.putDouble("lat", mPoiAddressSave.getPoint()
						.getLatitude());
				bundle.putDouble("lng", mPoiAddressSave.getPoint()
						.getLongitude());
				in.putExtras(bundle);
			}
		}
		activity.setResult(200, in);
		activity.finish();
	}

	@Override
	public void detachView(boolean retainInstance) {

	}
}
