package com.mo.mobileoffice.function.meeting.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mo.mobileoffice.R;
import com.mo.mobileoffice.common.mvp.MvpIdeaFragment;
import com.mo.mobileoffice.function.meeting.bean.FloorBean;
import com.mo.mobileoffice.function.meeting.bean.MeetingApplyBean;
import com.mo.mobileoffice.function.meeting.bean.MeetingApprBean;
import com.mo.mobileoffice.function.meeting.bean.RoomBean;
import com.mo.mobileoffice.function.meeting.contract.MeetingApprDetailContract;
import com.mo.mobileoffice.function.meeting.contract.MeetingApprDetailContract.Presenter;
import com.mo.mobileoffice.function.meeting.presenter.MeetingApprDetailPresenter;

public class MeetingApprDetailFragment extends
		MvpIdeaFragment<MeetingApprDetailContract.Presenter> implements
		MeetingApprDetailContract.View {

	@Override
	protected void init() {
		getPresenter().init(getBundle());
	}

	@Override
	public void initMeetingAppr(MeetingApprBean bean) {
		mBean = bean;
		setTitle(getResources().getString(R.string.meeting_appr_of, bean.getUser_name()));
		((TextView) findViewById(R.id.tv_meeting_appr_of)).setText(getResources().getString(R.string.meeting_appr_of, bean.getUser_name()));
		((TextView) findViewById(R.id.tv_time)).setText(bean.getApp_time());
		((TextView) findViewById(R.id.tv_start)).setText(bean.getStart());
		((TextView) findViewById(R.id.tv_end)).setText(bean.getEnd());
		((TextView) findViewById(R.id.tv_title)).setText(bean.getTitle());
		((TextView) findViewById(R.id.tv_content)).setText(bean.getContent());
		((TextView)findViewById(R.id.tv_approver)).setText(bean.getApprover());
		RoomBean roomBean = getPresenter().getRoomBeanById(bean.getRoom_id());
		FloorBean floorBean = getPresenter().getFloorBeanById(roomBean.getFloor_id());
		String roomInfo = floorBean.getFloor_name() + "-" + roomBean.getRoom_num();
		((TextView) findViewById(R.id.tv_room)).setText(roomInfo);
		if (bean.getState() == 0) {
			((TextView) findViewById(R.id.tv_state)).setText(getResources().getString(R.string.not_approved));
			findViewById(R.id.ll_view_bar).setVisibility(View.VISIBLE);
			findViewById(R.id.agree).setOnClickListener(this);
			findViewById(R.id.refuse).setOnClickListener(this);
		} else if (bean.getState() == 1) {
			((TextView)findViewById(R.id.tv_approval_view)).setText(bean.getView());
			((TextView)findViewById(R.id.tv_approval_state)).setText(getResources().getString(R.string.agree));
			((TextView) findViewById(R.id.tv_state)).setText(getResources().getString(R.string.already_approval));
			findViewById(R.id.ll_view_bar).setVisibility(View.GONE);
		} else if (bean.getState() == 2) {
			((TextView)findViewById(R.id.tv_approval_view)).setText(bean.getView());
			((TextView)findViewById(R.id.tv_approval_state)).setText(getResources().getString(R.string.refuse));
			((TextView) findViewById(R.id.tv_state)).setText(getResources().getString(R.string.already_approval));
			findViewById(R.id.ll_view_bar).setVisibility(View.GONE);
		}
	}
	
	@Override
	public void initMeetingApply(MeetingApplyBean bean) {
		setTitle(getResources().getString(R.string.meeting_appr_of, bean.getUser_name()));
		findViewById(R.id.ll_view_bar).setVisibility(View.GONE);
		((TextView) findViewById(R.id.tv_meeting_appr_of)).setText(getResources().getString(R.string.meeting_appr_of, bean.getUser_name()));
		if (bean.getState() == 0) {
			((TextView) findViewById(R.id.tv_state)).setText(getResources().getString(R.string.not_approved));
		} else {
			((TextView) findViewById(R.id.tv_state)).setText(getResources().getString(R.string.already_approval));
		}
		((TextView) findViewById(R.id.tv_time)).setText(bean.getApp_time());
		((TextView) findViewById(R.id.tv_start)).setText(bean.getStart());
		((TextView) findViewById(R.id.tv_end)).setText(bean.getEnd());
		((TextView)findViewById(R.id.tv_approver)).setText(bean.getApprover());
		((TextView)findViewById(R.id.tv_approval_view)).setText(bean.getView());
		((TextView) findViewById(R.id.tv_title)).setText(bean.getTitle());
		((TextView) findViewById(R.id.tv_content)).setText(bean.getContent());
		if (bean.getState() == 1) {
			((TextView)findViewById(R.id.tv_approval_state)).setText(getResources().getString(R.string.agree));
		} else if (bean.getState() == 2) {
			((TextView)findViewById(R.id.tv_approval_state)).setText(getResources().getString(R.string.refuse));
		}
		RoomBean roomBean = getPresenter().getRoomBeanById(bean.getRoom_id());
		FloorBean floorBean = getPresenter().getFloorBeanById(roomBean.getFloor_id());
		String roomInfo = floorBean.getFloor_name() + "-" + roomBean.getRoom_num();
		((TextView) findViewById(R.id.tv_room)).setText(roomInfo);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.agree:
			showAgreeDialg(R.id.agree);
			break;
		case R.id.refuse:
			showAgreeDialg(R.id.refuse);
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
		return new MeetingApprDetailPresenter(getActivity());
	}

	@Override
	protected int setContentViewId() {
		return R.layout.fragment_meeting_approval_detail;
	}

	static SparseArray<String> hashMap = new SparseArray<String>();
	static {
		hashMap.put(R.id.agree, "请输入同意的原因");
		hashMap.put(R.id.refuse, "请输入拒绝的原因");
	}

	private MeetingApprBean mBean;
	@SuppressLint("InflateParams")
	private void showAgreeDialg(final int id) {
		AlertDialog.Builder buider = new AlertDialog.Builder(getActivity());
		buider.setTitle(hashMap.get(id));
		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.layout_approvaldetail_edit, null);
		final EditText edit = (EditText) view.findViewById(R.id.opinion);
		buider.setView(view);
		buider.setPositiveButton(getResources().getString(R.string.ok), new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				if (id == R.id.agree) {
					getPresenter().requestApprSubmit(true, mBean.getId(),
							edit.getText().toString());
				} else if (id == R.id.refuse) {
					getPresenter().requestApprSubmit(false, mBean.getId(),
							edit.getText().toString());
				}
			}
		});

		buider.setNegativeButton(getResources().getString(R.string.cancel), new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		buider.show();
	}

	@Override
	public void apprSuccess() {
		getActivity().setResult(1);
		getActivity().finish();
	}

}
