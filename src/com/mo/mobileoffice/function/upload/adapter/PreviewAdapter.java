package com.mo.mobileoffice.function.upload.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.mo.mobileoffice.R;
import com.mo.mobileoffice.function.upload.tool.ImageLoader;

public class PreviewAdapter extends PagerAdapter {
	private ArrayList<String> mData;
	private Context mContext;
	
	public PreviewAdapter(Context context, ArrayList<String> picData) {
		mData = picData;
		mContext = context;
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		final ImageView iv = new ImageView(mContext);
		iv.setScaleType(ScaleType.CENTER_INSIDE);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		iv.setLayoutParams(params);
		iv.setImageResource(R.drawable.bg_default_pic);
		iv.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void onGlobalLayout() {
				iv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				int width = iv.getWidth();
				int height = iv.getHeight();
				ImageLoader.getInstance().loadImageFormDisk(iv, mData.get(position), R.drawable.bg_default_pic, width, height);
			}
		});
		container.addView(iv);
		return iv;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}
}
