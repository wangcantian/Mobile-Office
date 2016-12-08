package com.mo.mobileoffice.function.comm.ui;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mo.mobileoffice.R;
import com.mo.mobileoffice.common.app.FragmentEnum;
import com.mo.mobileoffice.common.base.BaseFragment;
import com.mo.mobileoffice.common.tool.PreTool;
import com.mo.mobileoffice.common.tool.StringTool;
import com.mo.mobileoffice.function.user.bean.UserBean;
import com.mo.mobileoffice.function.user.model.UserModel;
import com.squareup.picasso.Picasso;

/** 左侧滑菜单 **/
public class LeftMenuFragment extends BaseFragment {
	private static final int REQUEST_CODE = 1;
	
	private ImageView iv_headpic;// 头像
	private TextView tv_name;// 姓名
	private TextView tv_position;// 职位
	private TextView tv_exit;
	private LinearLayout ll_checkin_history;// 签到历史
	private LinearLayout ll__headinfo;// 个人信息
	private LinearLayout ll_meeting_approval;
	private LinearLayout ll_my_meeting_apply;
	
	@Override
	protected void init() {
		iv_headpic = findViewById(R.id.iv_headpic);
		tv_name = findViewById(R.id.tv_name);
		tv_position = findViewById(R.id.tv_position);
		tv_exit = findViewById(R.id.tv_exit);
		ll_checkin_history = findViewById(R.id.ll_checkin_history);
		ll__headinfo = findViewById(R.id.ll_headinfo);
		ll_meeting_approval = findViewById(R.id.ll_meeting_approval);
		ll_my_meeting_apply = findViewById(R.id.ll_my_meeting_apply);
		
		tv_exit.setOnClickListener(this);
		ll_checkin_history.setOnClickListener(this);
		ll__headinfo.setOnClickListener(this);
		ll_my_meeting_apply.setOnClickListener(this);
		updataUserInfo(new UserModel(getActivity()).getCurrUserInfo());
	}
	
	private void updataUserInfo(UserBean bean) {
		if (bean.getLevel().equals("1")) {
			ll_meeting_approval.setVisibility(View.VISIBLE);
			ll_meeting_approval.setOnClickListener(this);
			tv_position.setText(String.format(getResources().getString(R.string.position), "管理员"));
		} else {
			ll_meeting_approval.setVisibility(View.GONE);
			tv_position.setText(String.format(getResources().getString(R.string.position), "普通员工"));
		}
		tv_name.setText(StringTool.isEmpty(bean.getName()) ? 
				PreTool.getString(getActivity(), "use_name", "") : bean.getName());
		Picasso.with(getActivity()).load(bean.getPic_url()).into(iv_headpic);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_checkin_history:
			openIdeaActivity(FragmentEnum.FRAGMENT_CHECKIN_HISTORY);
			break;
		case R.id.ll_headinfo:
			openIdeaActivityForResult(FragmentEnum.FRAGMENT_PERS_INFO, REQUEST_CODE);
			break;
		case R.id.ll_meeting_approval:
			openIdeaActivity(FragmentEnum.FRAGMENT_MEETING_APPROVAL);
			break;
		case R.id.tv_exit:
			openIdeaActivity(FragmentEnum.FRAGMENT_SETTING);
			break;
		case R.id.ll_my_meeting_apply:
			openIdeaActivity(FragmentEnum.FRAGMENT_MY_MEETING_APPLY);
		default:
			break;
		}
	}

	@Override
	protected int setContentViewId() {
		return R.layout.fragment_left_menu;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE && resultCode == 1) {
			updataUserInfo(new UserModel(getActivity()).getCurrUserInfo());
		}
	}

}
