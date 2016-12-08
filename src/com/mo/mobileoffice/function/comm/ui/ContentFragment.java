package com.mo.mobileoffice.function.comm.ui;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.mo.mobileoffice.R;
import com.mo.mobileoffice.common.app.FragmentEnum;
import com.mo.mobileoffice.common.app.FragmentFactory;
import com.mo.mobileoffice.common.base.BaseFragment;
import com.mo.mobileoffice.common.mvp.MvpRadioFragment;
import com.mo.mobileoffice.common.tool.DisplayTool;
import com.mo.mobileoffice.common.tool.ImageTool;
import com.mo.mobileoffice.function.user.model.UserModel;

public class ContentFragment extends BaseFragment implements OnCheckedChangeListener {

	private int mRadioButtonDrawHeight;

	private RadioGroup mRadioGroup;
	private RadioButton[] mButtons;
	private ImageView iv_left;
	private ImageView iv_right;
	private TextView tv_center;

	private BaseFragment mCurrentFragment;
	private UserModel mUserModel;
	
	@Override
	protected void init() {
		mUserModel = new UserModel(getActivity());
		mRadioButtonDrawHeight = (int) (DisplayTool.getScreenHeight(getActivity()) * 0.05f);
		
		mRadioGroup = findViewById(R.id.radioContainer);
		mButtons = new RadioButton[mRadioGroup.getChildCount()];
		for (int i = 0; i < mRadioGroup.getChildCount(); i++) {
			mButtons[i] = (RadioButton) mRadioGroup.getChildAt(i);
			resetDrawable(mButtons[i]);
		}
		
		iv_left = findViewById(R.id.iv_left);
		iv_right = findViewById(R.id.tv_right);
		tv_center = findViewById(R.id.tv_center);
		
		iv_left.setOnClickListener(this);
		iv_right.setOnClickListener(this);
		mRadioGroup.setOnCheckedChangeListener(this);
		mButtons[0].setChecked(true);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_left:
			((MainActivity) getActivity()).toggle();
			break;
		case R.id.tv_right:
			((MvpRadioFragment<?>) mCurrentFragment).RightOnClick();
			break;
		default:
			break;
		}
	}

	@Override
	protected int setContentViewId() {
		return R.layout.fragment_content;
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		iv_right.setEnabled(mUserModel.isCanSendAnno());
		switch (checkedId) {
		case R.id.radio_announce:
			tv_center.setText(getResources().getString(R.string.radio_announce));
			iv_right.setImageDrawable(mUserModel.isCanSendAnno() ? getResources().getDrawable(R.drawable.ico_send) : null);
			setFragmentToFrameLayout(FragmentEnum.FRAGMENT_RADIO_ANNOUNCE);
			break;
		case R.id.radio_check_in:
			tv_center.setText(getResources().getString(R.string.radio_check_in));
			iv_right.setImageDrawable(null);
			setFragmentToFrameLayout(FragmentEnum.FRAGMENT_RADIO_CHECK_IN);
			break;
		case R.id.radio_approval:
			tv_center.setText(getResources().getString(R.string.radio_approval));
			iv_right.setImageDrawable(null);
			setFragmentToFrameLayout(FragmentEnum.FRAGMENT_RADIO_APPROVAL);
			break;
		case R.id.radio_meeting_room:
			tv_center.setText(getResources().getString(R.string.radio_meeting_room));
			iv_right.setImageDrawable(getResources().getDrawable(R.drawable.ico_send));
			setFragmentToFrameLayout(FragmentEnum.FRAGMENT_RADIO_MEETING_ROOM);
			break;
		default:
			break;
		}
	}

	private void resetDrawable(RadioButton mButton) {
		Drawable[] compoundDrawables = mButton.getCompoundDrawables();
		Drawable drawable = ImageTool.changeBitmapSize(compoundDrawables[1], mRadioButtonDrawHeight, mRadioButtonDrawHeight);
		mButton.setCompoundDrawables(compoundDrawables[0], drawable, compoundDrawables[2], compoundDrawables[3]);
	}

	private void setFragmentToFrameLayout(FragmentEnum type) {
		BaseFragment fragment = FragmentFactory.getFragment(type);
		changeFragment(R.id.fragment_Container, fragment);
	}

	private void changeFragment(int id, BaseFragment fragment) {
		if (!fragment.isAdded()) {
			if (mCurrentFragment != null) {
				getActivity().getSupportFragmentManager().beginTransaction().hide(mCurrentFragment).add(id, fragment).commit();
			} else {
				getActivity().getSupportFragmentManager().beginTransaction().add(id, fragment).commit();
			}
		} else {
			getActivity().getSupportFragmentManager().beginTransaction().hide(mCurrentFragment).show(fragment).commit();
		}
		mCurrentFragment = fragment;
	}


}
