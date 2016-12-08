package com.mo.mobileoffice.function.approval.ui;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Scroller;
import android.widget.TextView;

import com.mo.mobileoffice.R;
import com.mo.mobileoffice.common.mvp.MvpIdeaFragment;
import com.mo.mobileoffice.function.approval.contract.WaitMeApprContract;
import com.mo.mobileoffice.function.approval.contract.WaitMeApprContract.Presenter;
import com.mo.mobileoffice.function.approval.presenter.WaitMeApprPresenter;

/** 待我审批 **/
public class WaitMeApprFragment extends
		MvpIdeaFragment<WaitMeApprContract.Presenter> implements
		WaitMeApprContract.View {

	private View scrollBar;
	private RelativeLayout scrollBarContainer;
	private Scroller mScroller;
	private TextView waitApproval;
	private TextView historyAproval;
	private FrameLayout container;

	@Override
	protected void init() {
		waitApproval = findViewById(R.id.waitApproval);
		historyAproval = findViewById(R.id.historyApproval);
		scrollBar = findViewById(R.id.scrollbar);
		container = findViewById(R.id.container);
		scrollBarContainer = (RelativeLayout) scrollBar.getParent();
		mScroller = new Scroller(getActivity());
		initiScrollBar();
		// getChildFragmentManager().beginTransaction()
		// .add(R.id.container, mWaitMeApprovalFragment).commit();
		getChildFragmentManager().beginTransaction()
				.add(R.id.container, new WaitMeApprovalFragment()).commit();
		waitApproval.setOnClickListener(this);
		historyAproval.setOnClickListener(this);
		setTitle(getResources().getString(R.string.wait_approval));
	}

	private ObjectAnimator toWaitAnimator;
	private ObjectAnimator toHistoryAnimator;

	private ObjectAnimator waitNormalAnimator;
	private ObjectAnimator waitActiveAnimator;

	private ObjectAnimator hisNormalAnimator;
	private ObjectAnimator hisActiveAnimator;

	private void initViewAnimation() {
		toWaitAnimator = ObjectAnimator.ofFloat(scrollBar, "translationX",
				scrollBarWidth / 2, 0);
		toWaitAnimator.setDuration(300);
		toWaitAnimator.setInterpolator(new DecelerateInterpolator());
		toHistoryAnimator = ObjectAnimator.ofFloat(scrollBar, "translationX",
				0, scrollBarWidth / 2);
		toHistoryAnimator.setDuration(300);
		toHistoryAnimator.setInterpolator(new DecelerateInterpolator());

		waitNormalAnimator = ObjectAnimator.ofInt(waitApproval, "textColor",
				Color.parseColor("#ffffff"), Color.parseColor("#7a7774"));
		waitNormalAnimator.setDuration(300);
		waitNormalAnimator.setInterpolator(new DecelerateInterpolator());
		waitNormalAnimator.setEvaluator(new ArgbEvaluator());
		waitActiveAnimator = ObjectAnimator.ofInt(waitApproval, "textColor",
				Color.parseColor("#7a7774"), Color.parseColor("#ffffff"));
		waitActiveAnimator.setDuration(300);
		waitActiveAnimator.setInterpolator(new DecelerateInterpolator());
		waitActiveAnimator.setEvaluator(new ArgbEvaluator());

		hisNormalAnimator = ObjectAnimator.ofInt(historyAproval, "textColor",
				Color.parseColor("#ffffff"), Color.parseColor("#7a7774"));
		hisNormalAnimator.setDuration(300);
		hisNormalAnimator.setEvaluator(new ArgbEvaluator());
		hisNormalAnimator.setInterpolator(new DecelerateInterpolator());
		hisActiveAnimator = ObjectAnimator.ofInt(historyAproval, "textColor",
				Color.parseColor("#7a7774"), Color.parseColor("#ffffff"));
		hisActiveAnimator.setDuration(300);
		hisActiveAnimator.setInterpolator(new DecelerateInterpolator());
		hisActiveAnimator.setEvaluator(new ArgbEvaluator());
	}

	int scrollBarWidth = 0;

	private void initiScrollBar() {
		scrollBarContainer.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {

					@Override
					public void onGlobalLayout() {
						int width = scrollBarContainer.getWidth();
						scrollBarWidth = width;
						RelativeLayout.LayoutParams lp = (LayoutParams) scrollBar
								.getLayoutParams();
						lp.width = width / 2;
						scrollBar.setLayoutParams(lp);
						initViewAnimation();
						scrollBarContainer.getViewTreeObserver()
								.removeGlobalOnLayoutListener(this);
					}
				});
	}

	ChildBaseFragment mWaitMeApprovalFragment = new WaitMeApprovalFragment();
	ChildBaseFragment mHistoryApprovalFragment = new HistoryApprovalFragment();

	@Override
	public void onResume() {
		super.onResume();
	}

	private int currentClick = R.id.waitApproval;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.waitApproval:
			if (v.getId() == currentClick) {
				return;
			}
			// if (mHistoryApprovalFragment.isAdded()) {
			// getChildFragmentManager().beginTransaction()
			// .hide(mHistoryApprovalFragment)
			// .show(mWaitMeApprovalFragment).commit();
			// }
			getChildFragmentManager().beginTransaction()
					.replace(R.id.container, new WaitMeApprovalFragment())
					.commit();
			toWaitAnimator.start();
			waitActiveAnimator.start();
			hisNormalAnimator.start();
			currentClick = v.getId();
			break;
		case R.id.historyApproval:
			if (v.getId() == currentClick) {
				return;
			}
			// if (mHistoryApprovalFragment.isAdded()) {
			// getChildFragmentManager().beginTransaction()
			// .hide(mWaitMeApprovalFragment)
			// .show(mHistoryApprovalFragment).commit();
			// } else {
			// getChildFragmentManager().beginTransaction()
			// .hide(mWaitMeApprovalFragment)
			// .add(R.id.container, mHistoryApprovalFragment).commit();
			// }
			getChildFragmentManager().beginTransaction()
					.replace(R.id.container, new HistoryApprovalFragment())
					.commit();
			toHistoryAnimator.start();
			hisActiveAnimator.start();
			waitNormalAnimator.start();
			currentClick = v.getId();
			break;

		default:
			break;
		}
	}

	@Override
	public void rightOnClick() {

	}

	@Override
	protected Presenter createPresenter() {
		return new WaitMeApprPresenter(getActivity());
	}

	@Override
	protected int setContentViewId() {
		return R.layout.fragment_wait_me_appr;
	}

	@Override
	public void addChild(View view) {
		container.addView(view);
	}

	@Override
	public void removeChild(View view) {
		container.removeView(view);
	}

}
