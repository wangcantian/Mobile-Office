package com.mo.mobileoffice.common.widget;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mo.mobileoffice.R;
import com.mo.mobileoffice.common.tool.DisplayTool;

public class HistoryView extends LinearLayout {
	// 顶部菜单布局
	private LinearLayout mTabMenuView;
	// 底部容器
	private FrameLayout mContainerView;
	// 弹出菜单父布局
	private FrameLayout mPopupMenuViews;
	// 遮罩半透明View
	private View mMaskView;
	// tabMenuView里面选中的tab位置，-1表示未选中
	private int mCurrTabPos = -1;
	// mTabMenuView背景颜色
	private int mMenuBgColor = 0xffffffff;
	// mTabMenuView下划线颜色
	private int mUnderLineColor = 0xffcccccc;
	// tab字体大小
	private int mMenuTextSize = 14;
	// 分割线颜色
    private int mDividerColor = 0xffcccccc;
    // tab选中颜色
    private int mTextSelectedColor = 0xff890c85;
    // tab未选中颜色
    private int mTextUnselectedColor = 0xff111111;
    // 遮罩颜色
    private int mMaskColor = 0x88888888;
    // tab选中图标
    private int mMenuSelectedIcon = R.drawable.ico_drop_down_selected;
    // tab未选中图标
    private int mMenuUnselectedIcon = R.drawable.ico_drop_down_unselected;
    
	public HistoryView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public HistoryView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public HistoryView(Context context) {
		this(context, null);
	}

	private void init() {
		setOrientation(VERTICAL);
		
		mTabMenuView = new LinearLayout(getContext());
		mTabMenuView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		mTabMenuView.setOrientation(HORIZONTAL);
		mTabMenuView.setBackgroundColor(mMenuBgColor);
		addView(mTabMenuView, 0);
		
		View underLine = new View(getContext());
		underLine.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, DisplayTool.dp2px(getContext(), 1)));
		underLine.setBackgroundColor(mUnderLineColor);
		addView(underLine, 1);
		
		mContainerView = new FrameLayout(getContext());
		mContainerView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
		addView(mContainerView, 2);
	}
	
	/** 初始化所有数据 **/
	public void setDropDownMenu(List<String> tabTexts, List<View> popupViews, View contentView) {
		if (tabTexts.size() != popupViews.size()) {
			throw new IllegalArgumentException();
		}
		
		for (int i = 0; i < tabTexts.size(); i++) {
			addTab(tabTexts, i);
		}
		mContainerView.addView(contentView, 0);
		
		mMaskView = new View(getContext());
		mMaskView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
		mMaskView.setBackgroundColor(mMaskColor);
		mMaskView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				closeMenu();
			}
		});
		mContainerView.addView(mMaskView, 1);
		mMaskView.setVisibility(GONE);
		
		mPopupMenuViews = new FrameLayout(getContext());
		mPopupMenuViews.setVisibility(GONE);
		mContainerView.addView(mPopupMenuViews, 2);
		
		for (int i = 0; i < popupViews.size(); i++) {
			popupViews.get(i).setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
			mPopupMenuViews.addView(popupViews.get(i), i);
		}
	}
	
	/** popupViews是否可见 **/
	public boolean isShow() {
		return mCurrTabPos != -1;
	}
	
	/** 改变tab文字 **/
	public void setTabText(String text) {
        if (mCurrTabPos != -1) {
            ((TextView) mTabMenuView.getChildAt(mCurrTabPos)).setText(text);
        }
    }
	
	private void addTab(List<String> tabTexts, int i) {
		final TextView tab = new TextView(getContext());
		tab.setSingleLine();
		tab.setGravity(Gravity.CENTER);
		tab.setTextSize(TypedValue.COMPLEX_UNIT_SP, mMenuTextSize);
		tab.setLayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
		tab.setTextColor(mTextUnselectedColor);
		tab.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(mMenuUnselectedIcon), null);
		tab.setText(tabTexts.get(i));
		tab.setPadding(DisplayTool.dp2px(getContext(), 5), 
						DisplayTool.dp2px(getContext(), 12), 
						DisplayTool.dp2px(getContext(), 5), 
						DisplayTool.dp2px(getContext(), 12));
		tab.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				switchMenu(tab);
			}
		});
		mTabMenuView.addView(tab);
		if (i < tabTexts.size() - 1) {
			View view = new View(getContext());
			view.setLayoutParams(new LayoutParams(DisplayTool.dp2px(getContext(), 0.5f), LayoutParams.MATCH_PARENT));
			view.setBackgroundColor(mDividerColor);
			mTabMenuView.addView(view);
		}
	}
	
	/** 切换菜单 **/
	private void switchMenu(View target) {
		for (int i = 0; i < mTabMenuView.getChildCount(); i += 2) {
			if (target == mTabMenuView.getChildAt(i)) {
				if (mCurrTabPos == i) {
					closeMenu();
				} else {
					if (mCurrTabPos == -1) {
						mPopupMenuViews.setVisibility(VISIBLE);
						mPopupMenuViews.setAnimation(createOpenAnimation());
						mMaskView.setVisibility(VISIBLE);
						mMaskView.setAnimation(createMaskOpenAnimation());
					}
					mPopupMenuViews.getChildAt(i / 2).setVisibility(VISIBLE);
					mCurrTabPos = i;
					
					((TextView)target).setTextColor(mTextSelectedColor);
					((TextView)target).setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(mMenuSelectedIcon), null);
				}
			} else {
				((TextView)mTabMenuView.getChildAt(i)).setTextColor(mTextUnselectedColor);
				((TextView)mTabMenuView.getChildAt(i)).setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(mMenuUnselectedIcon), null);
				mPopupMenuViews.getChildAt(i / 2).setVisibility(GONE);
			}
		}
	}
	
	/** 关闭菜单 **/
	public void closeMenu() {
		if (mCurrTabPos != -1) {
			TextView tab = (TextView) mTabMenuView.getChildAt(mCurrTabPos);
			tab.setTextColor(mTextUnselectedColor);
			tab.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(mMenuUnselectedIcon), null);
			
			mPopupMenuViews.setVisibility(GONE);
			mPopupMenuViews.setAnimation(createCloseAnimation());
			
			mMaskView.setVisibility(GONE);
			mMaskView.setAnimation(createMaskCloseAnimation());
			
			mCurrTabPos = -1;
		}
	}
	
	/** 弹出动画 **/
	private Animation createOpenAnimation() {
		AnimationSet set = new AnimationSet(false);
		AlphaAnimation alpha = new AlphaAnimation(0, 1.0f);
		TranslateAnimation translate = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
																Animation.RELATIVE_TO_SELF, 0, 
																Animation.RELATIVE_TO_SELF, -1.0f, 
																Animation.RELATIVE_TO_SELF, 0);
		set.addAnimation(alpha);
		set.addAnimation(translate);
		set.setDuration(250);
		return set;
	}
	
	private Animation createMaskOpenAnimation() {
		AlphaAnimation alpha = new AlphaAnimation(0, 1.0f);
		alpha.setDuration(250);
		return alpha;
	}
	
	/** 收起动画 **/
	private Animation createCloseAnimation() {
		AnimationSet set = new AnimationSet(false);
		AlphaAnimation alpha = new AlphaAnimation(1.0f, 0);
		TranslateAnimation translate = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
																Animation.RELATIVE_TO_SELF, 0, 
																Animation.RELATIVE_TO_SELF, 0, 
																Animation.RELATIVE_TO_SELF, -1.0f);
		set.addAnimation(alpha);
		set.addAnimation(translate);
		set.setDuration(250);
		return set;
	}
	
	private Animation createMaskCloseAnimation() {
		AlphaAnimation alpha = new AlphaAnimation(1.0f, 0);
		alpha.setDuration(250);
		return alpha;
	}
}
