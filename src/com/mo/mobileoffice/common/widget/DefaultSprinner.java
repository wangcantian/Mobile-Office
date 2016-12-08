package com.mo.mobileoffice.common.widget;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mo.mobileoffice.R;
import com.mo.mobileoffice.common.adapter.CommonAdapter;
import com.mo.mobileoffice.common.adapter.ViewHolder;
import com.mo.mobileoffice.common.tool.DisplayTool;
import com.mo.mobileoffice.function.checkin.bean.CheckIn_HistoryDataFragment_Respond.Data;
import com.mo.mobileoffice.function.user.model.UserModel;

public class DefaultSprinner extends LinearLayout implements OnClickListener,
		OnRefreshListener {
	// tab选中图标
//	private int mMenuSelectedIcon = R.drawable.ico_drop_down_selected;
	// tab未选中图标
	private int mMenuUnselectedIcon = R.drawable.ico_drop_down_unselected;
	private UserModel mUserModel;

	public DefaultSprinner(Context context, AttributeSet attrs) {
		super(context, attrs);
		mUserModel = new UserModel(context);
		initView();
	}

	public DefaultSprinner(Context context) {
		this(context, null);
	}

	private void initView() {
		setOrientation(LinearLayout.VERTICAL);
		addMenuView();
		addContainerView();
	}

	// 添加添加菜单栏
	private LinearLayout menu;

	private void addMenuView() {
		menu = new LinearLayout(getContext());
		menu.setOrientation(LinearLayout.HORIZONTAL);
		menu.setGravity(Gravity.CENTER);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		menu.setLayoutParams(lp);
		int value1 = DisplayTool.dp2px(getContext(), 10);
		int value2 = DisplayTool.dp2px(getContext(), 5);
		menu.setPadding(value1, value2, value1, value2);
		menu.setBackgroundResource(R.drawable.shape_announce_listitem_bg);
		this.addView(menu, 0);
		addMenuChildView();
		menu.setOnClickListener(this);
		menu.setId(1);
	}

	private void addMenuChildView() {
		TextView tv = new TextView(getContext());
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lp.rightMargin = DisplayTool.dp2px(getContext(), 5);
		tv.setGravity(Gravity.CENTER);
		tv.setText("查看时间");
		tv.setTextSize(14);
		tv.setLayoutParams(lp);

		ImageView iv = new ImageView(getContext());
		iv.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		iv.setLayoutParams(lp);
		iv.setImageResource(mMenuUnselectedIcon);

		menu.addView(tv, 0);
		menu.addView(iv, 1);
	}

	// 添加第二个布局
	private FrameLayout container;

	private void addContainerView() {
		container = new FrameLayout(getContext());
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		container.setLayoutParams(lp);
		this.addView(container, 1);

		addFirstView();
		addSecondView();
		addThirdView();
		addFourView();
		initFirstChildView();
	}

	private View view;

	@SuppressLint("InflateParams") 
	private void addFirstView() {
		LayoutInflater layoutInflater = LayoutInflater.from(getContext());
		view = layoutInflater.inflate(R.layout.fragment_checkin_historyrefresh, null);
		FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.MATCH_PARENT);
		view.setLayoutParams(lp);
		container.addView(view, 0);
	}

	public void setViewGone() {
		if (view != null) {
			view.setVisibility(View.GONE);
		}
	}

	public void setViewVisibility() {
		if (view != null) {
			view.setVisibility(View.VISIBLE);
		}
	}

	public void setSecondViewVisiable() {
		if (secondView != null) {
			secondView.setVisibility(View.VISIBLE);
		}
	}

	private TextView secondView;

	private void addSecondView() {
		secondView = new TextView(getContext());
		FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
				android.widget.FrameLayout.LayoutParams.MATCH_PARENT,
				android.widget.FrameLayout.LayoutParams.MATCH_PARENT);
		secondView.setLayoutParams(lp);
		secondView.setGravity(Gravity.CENTER);
		secondView.setBackgroundColor(Color.WHITE);
		secondView.setText("没有数据");
		secondView.setTextSize(14);
		container.addView(secondView, 1);
		setSecondViewGone();
	}

	public void setSecondViewGone() {
		if (secondView != null) {
			secondView.setVisibility(View.GONE);
		}
	}

	// 初始化下拉刷新头部和listView
	private SwipeRefreshLayout refreshView;
	private ListView listView;

	private void initFirstChildView() {
		refreshView = (SwipeRefreshLayout) view
				.findViewById(R.id.swipeRefreshView);
		listView = (ListView) view.findViewById(R.id.listView);
		refreshView.setColorSchemeResources(android.R.color.holo_red_light,
				android.R.color.holo_green_light,
				android.R.color.holo_blue_bright,
				android.R.color.holo_orange_light);
		refreshView.setOnRefreshListener(this);
		refreshView.setEnabled(false);
	}

	private CommonAdapter<Data> adapter;

	public void setFreshData(Context context, ArrayList<Data> data, int itemId) {
		adapter = new CommonAdapter<Data>(context, data, itemId) {

			@Override
			public void convert(ViewHolder holder, Data data, int position) {
				holder.setImageResId(R.id.userimg, R.drawable.headpic);
				holder.setText(R.id.place, "签到地点:" + data.getPlace());
				holder.setText(R.id.time, "签到时间:"
						+ data.getTime().split(" ")[0]);
				holder.setText(R.id.user, mUserModel.getCurrUserInfo().getName()
						+ mUserModel.getUserId());
			}

		};

		listView.setAdapter(adapter);
	}

	public void notifyAdapter(ArrayList<Data> data) {
		if (data != null && data.size() > 0) {
			adapter.setList(data);
			adapter.notifyDataSetChanged();
			setSecondViewGone();
			setViewVisibility();
		} else {
			secondView.setVisibility(View.VISIBLE);
			setViewGone();
		}
		refreshView.setRefreshing(false);
		refreshView.setEnabled(false);
	}

	private TextView tv;

	private void addThirdView() {
		tv = new TextView(getContext());
		android.widget.FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
				android.widget.FrameLayout.LayoutParams.MATCH_PARENT,
				android.widget.FrameLayout.LayoutParams.MATCH_PARENT);
		tv.setLayoutParams(lp);
		tv.setGravity(Gravity.CENTER);
		tv.setAlpha(0.5f);
		tv.setOnClickListener(this);
		tv.setId(6);
		tv.setBackgroundColor(0x88888888);
		tv.setVisibility(View.GONE);
		container.addView(tv, 2);
	}

	private LinearLayout selectList;

	private void addFourView() {
		selectList = new LinearLayout(getContext());
		FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.WRAP_CONTENT);
		selectList.setLayoutParams(lp);
		selectList.setOrientation(LinearLayout.VERTICAL);
		selectList.setVisibility(View.GONE);
		container.addView(selectList, 3);
		addThirdChildView();
	}

	String[] str = new String[] { "最近一个月", "最近两个月", "最近三个月" };
	TextView[] textViews = new TextView[3];

	private void addThirdChildView() {
		int value = DisplayTool.dp2px(getContext(), 5);
		for (int i = 0; i < str.length; i++) {
			TextView textView = new TextView(getContext());
			LinearLayout.LayoutParams lp = new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			textView.setGravity(Gravity.CENTER);
			textView.setText(str[i]);
			textView.setLayoutParams(lp);
			textView.setTextSize(14);
			textView.setBackgroundResource(R.drawable.shape_announce_listitem_bg);
			textView.setPadding(value, value, value, value);
			textView.setId(3 + i);
			textView.setOnClickListener(this);
			selectList.addView(textView, i);
			textViews[i] = textView;
		}
		selectList.setVisibility(View.GONE);
		// Animation animation = createCloseAnimation();
		// animation.setFillAfter(true);
		// selectList.startAnimation(animation);
	}

	/** 弹出动画 **/
	private Animation createOpenAnimation() {
		AnimationSet set = new AnimationSet(false);
		AlphaAnimation alpha = new AlphaAnimation(0, 1.0f);
		TranslateAnimation translate = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
				Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF,
				0);
		set.addAnimation(alpha);
		set.addAnimation(translate);
		set.setDuration(250);
		return set;
	}

	/** 收起动画 **/
	private Animation createCloseAnimation() {
		AnimationSet set = new AnimationSet(false);
		AlphaAnimation alpha = new AlphaAnimation(1.0f, 0);
		TranslateAnimation translate = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
				Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF,
				-1.0f);
		set.addAnimation(alpha);
		set.addAnimation(translate);
		set.setDuration(250);
		return set;
	}

	private void closeView() {
		isShow = !isShow;
		tv.setVisibility(View.GONE);
		Animation animation = createCloseAnimation();
		animation.setFillAfter(true);
		selectList.startAnimation(animation);
		selectList.setVisibility(View.GONE);
		for (int i = 0; i < textViews.length; i++) {
			textViews[i].setVisibility(View.GONE);
		}
		setSecondViewGone();
	}

	private boolean isShow = false;

	int currentFlag = 0;

	public static final int RECENT_ONE = 3;
	public static final int RECENT_TWO = 4;
	public static final int RECENT_THREE = 5;

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case 1:
			isShow = !isShow;
			if (isShow) {
				tv.setVisibility(View.VISIBLE);
				for (int i = 0; i < textViews.length; i++) {
					textViews[i].setVisibility(View.VISIBLE);
				}
				selectList.setVisibility(View.VISIBLE);
				Animation animation = createOpenAnimation();
				animation.setFillAfter(true);
				selectList.startAnimation(animation);
			} else {
				Animation animation = createCloseAnimation();
				animation.setFillAfter(true);
				selectList.startAnimation(animation);
				tv.setVisibility(View.GONE);
				selectList.setVisibility(View.GONE);
				for (int i = 0; i < textViews.length; i++) {
					textViews[i].setVisibility(View.GONE);
				}
			}
			break;
		case 3:
			refreshView.setEnabled(true);
			currentFlag = RECENT_ONE;
			closeView();
			this.onRefresh();
			refreshView.setRefreshing(true);

			break;
		case 4:
			refreshView.setEnabled(true);
			currentFlag = RECENT_TWO;
			closeView();
			this.onRefresh();
			refreshView.setRefreshing(true);
			break;
		case 5:
			refreshView.setEnabled(true);
			currentFlag = RECENT_THREE;
			closeView();
			this.onRefresh();
			refreshView.setRefreshing(true);
			break;
		case 6:
			closeView();
			break;

		default:
			break;
		}
	}

	// 下拉刷新的标志
	@Override
	public void onRefresh() {
		// 请求网络操作
		switch (currentFlag) {
		case RECENT_ONE:
			if (refresh != null) {
				refresh.clickToRefresh(currentFlag);
				currentFlag = 0;
			}
			break;
		case RECENT_TWO:
			if (refresh != null) {
				refresh.clickToRefresh(currentFlag);
				currentFlag = 0;
			}
			break;
		case RECENT_THREE:
			if (refresh != null) {
				refresh.clickToRefresh(currentFlag);
				currentFlag = 0;
			}
			break;
		default:
//			refreshView.setRefreshing(false);
			break;
		}
	}

	public interface OnItemClickToRefresh {
		void clickToRefresh(int currentFlag);
	}

	OnItemClickToRefresh refresh;

	public void setOnItemClickRefresh(OnItemClickToRefresh refresh) {
		this.refresh = refresh;
	}

}
