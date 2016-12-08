package com.mo.mobileoffice.function.approval.ui;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mo.mobileoffice.R;
import com.mo.mobileoffice.common.adapter.CommonAdapter;
import com.mo.mobileoffice.common.adapter.ViewHolder;
import com.mo.mobileoffice.common.app.FragmentEnum;
import com.mo.mobileoffice.function.approval.bean.ApprovalDetail_Respond.DetailData;
import com.mo.mobileoffice.function.approval.contract.WaitMeApprovalContract;
import com.mo.mobileoffice.function.approval.presenter.WaitMeApprovalPresenter;

public class WaitMeApprovalFragment extends ChildBaseFragment
		implements
		com.mo.mobileoffice.function.approval.contract.WaitMeApprovalContract.View {

	@Override
	protected int setContentViewId() {
		return R.layout.layout_waime_fragment;
	}

	@Override
	public void onClick(View v) {

	}

	ListView waitListView;
	private CommonAdapter<DetailData> adapter;

	WaitMeApprovalContract.Presenter presenter;

	@Override
	protected void init() {

		waitListView = findViewById(R.id.waitApprovalList);
		presenter = new WaitMeApprovalPresenter(getActivity());
		presenter.attachView(this);
	}

	@Override
	public void onResume() {
		super.onResume();
		presenter.doHttpGet();
	}

	private String[] type = new String[] { "的请假申请", "的报销申请", "的出差申请" };
	private ArrayList<DetailData> detailList = null;

	@Override
	public void doHttpRespond(ArrayList<DetailData> list) {
		if (adapter == null) {
			if (detailList == null) {
				detailList = list;
			}
			adapter = new CommonAdapter<DetailData>(getActivity(), list,
					R.layout.layout_waitapproval_item) {

				@Override
				public void convert(ViewHolder holder, DetailData item,
						int position) {
					holder.setText(R.id.resummerName, item.getUser_name());
					holder.setText(R.id.resumeType, item.getUser_name()
							+ type[Integer.parseInt(item.getApp_type()) - 1]);
					holder.setText(R.id.resume_state, "未审批");
					holder.setText(R.id.resumeTime, item.getApp_time());
				}
			};
			waitListView.setAdapter(adapter);
			waitListView.setOnItemClickListener(this);
		} else {
			detailList = list;
			adapter.setList(list);
			adapter.notifyDataSetChanged();
		}
	}

	public void removeAllView() {
		if (waitListView != null && waitListView.getChildCount() > 0) {
			if (detailList.size() == 1) {
				detailList.remove(0);
				adapter.setList(detailList);
				adapter.notifyDataSetChanged();
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (detailList != null) {
			DetailData data = detailList.get(position);
			Bundle bundle = new Bundle();
			bundle.putSerializable("detailData", data);
			openIdeaActivityForResult(
					FragmentEnum.FRAGMENT_WAIT_ME_APPR_DETAIL, 1, bundle);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	}
}
