package com.mo.mobileoffice.function.approval.ui;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mo.mobileoffice.R;
import com.mo.mobileoffice.common.mvp.MvpIdeaFragment;
import com.mo.mobileoffice.function.approval.contract.ImageViewShowContract;
import com.mo.mobileoffice.function.approval.contract.ImageViewShowContract.Presenter;
import com.mo.mobileoffice.function.approval.presenter.ShowImagePresenter;
import com.squareup.picasso.Picasso;

public class ShowImageFragment extends
		MvpIdeaFragment<ImageViewShowContract.Presenter> implements
		ImageViewShowContract.View {

	@Override
	public void onClick(View v) {

	}

	@Override
	public void rightOnClick() {

	}

	@Override
	protected Presenter createPresenter() {
		return new ShowImagePresenter(getActivity());
	}

	ArrayList<String> imageList = null;
	ViewPager viewPager;
	ImageAdapter adapter;

	@Override
	protected void init() {
		imageList = getIntent().getExtras().getStringArrayList("imageurl");
		int id=getIntent().getExtras().getInt("imageId");
		viewPager = findViewById(R.id.viewpager);
		adapter = new ImageAdapter();
		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(id);
	}

	@Override
	protected int setContentViewId() {
		return R.layout.layout_approvaldetailfragment_image;
	}

	public class ImageAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return imageList.size();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public Object instantiateItem(final ViewGroup container, int position) {
			final ImageView imageView = new ImageView(getActivity());
			Picasso.with(getActivity()).load(imageList.get(position)).into(imageView);
			container.addView(imageView);
			return imageView;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

	}

}
