package com.mo.mobileoffice.function.approval.ui;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.mo.mobileoffice.R;
import com.mo.mobileoffice.common.adapter.CommonAdapter;
import com.mo.mobileoffice.common.adapter.ViewHolder;
import com.mo.mobileoffice.common.app.FragmentEnum;
import com.mo.mobileoffice.common.mvp.MvpIdeaFragment;
import com.mo.mobileoffice.function.approval.bean.MyApproval_Respond;
import com.mo.mobileoffice.function.approval.bean.MyApproval_Respond.MyApprovalData;
import com.mo.mobileoffice.function.approval.contract.ApprOfMeContract;
import com.mo.mobileoffice.function.approval.contract.ApprOfMeContract.Presenter;
import com.mo.mobileoffice.function.approval.presenter.ApprOfMePresenter;

public class ApprOfMeFragment extends
		MvpIdeaFragment<ApprOfMeContract.Presenter> implements
		ApprOfMeContract.View, OnItemClickListener {

	private ListView listView;

	@Override
	protected void init() {
		listView = findViewById(R.id.listView);
	}

	@Override
	public void onResume() {
		super.onResume();
		setTitle("我发起的审批");
		getPresenter().doHttpSubmit();
	}

	@Override
	public void onClick(View v) {

	}

	@Override
	public void rightOnClick() {

	}

	@Override
	protected void setTitle(String title) {
		super.setTitle(title);
	}

	@Override
	protected Presenter createPresenter() {
		return new ApprOfMePresenter(getActivity());
	}

	@Override
	protected int setContentViewId() {
		return R.layout.fragment_appr_of_me;
	}

	private String[] arr = new String[] { "的请假申请", "的报销申请", "的出差申请" };
	private String[] stateArr = new String[] { "同意", "拒绝" };
	private ArrayList<MyApprovalData> list = null;

	CommonAdapter<MyApprovalData> adapter;

	@Override
	public void adapterRespondData(ArrayList<MyApprovalData> list) {
		if (adapter == null) {
			this.list = list;
			adapter = new CommonAdapter<MyApproval_Respond.MyApprovalData>(
					getActivity(), list, R.layout.layout_hisapproval_item) {

				@Override
				public void convert(ViewHolder holder, MyApprovalData item,
						int position) {
					holder.setText(R.id.resummerName, item.getUser_name());
					holder.setText(R.id.resumeType,
							"我" + arr[Integer.parseInt(item.getApp_type()) - 1]);
					if (item.getState().equals("0")) {
						holder.setText(R.id.resume_state,
								"等待" + item.getApprover() + "审批");
					} else {
						holder.setText(
								R.id.resume_state,
								"审批完成("
										+ stateArr[Integer.parseInt(item
												.getState()) - 1] + ")");
					}
					holder.setText(R.id.resumeTime, item.getApp_time());
				}
			};
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(this);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		MyApprovalData data = list.get(position);
		Bundle bundle = new Bundle();
		bundle.putSerializable("data", data);
		openIdeaActivity(FragmentEnum.FRAGMENT_MYPROVAL_DETAIL, bundle);
	}
}
