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
import com.mo.mobileoffice.function.approval.bean.HistoryApproval_Respond.HistoryData;
import com.mo.mobileoffice.function.approval.contract.HistoryApprovalContract;
import com.mo.mobileoffice.function.approval.contract.HistoryApprovalContract.Presenter;
import com.mo.mobileoffice.function.approval.presenter.HistoryApprovalPresenter;

public class HistoryApprovalFragment extends ChildBaseFragment implements
		HistoryApprovalContract.View {

	@Override
	protected int setContentViewId() {
		return R.layout.layout_history_approval_fragment;
	}

	@Override
	public void onClick(View v) {

	}

	ListView hisListView;
	private CommonAdapter<HistoryData> adapter;
	private Presenter presenter;

	@Override
	protected void init() {
		hisListView = findViewById(R.id.hisApprovalList);
		presenter = new HistoryApprovalPresenter(getActivity());
		presenter.attachView(this);
	}

	@Override
	public void onResume() {
		super.onResume();
		presenter.doHttpGet();
	}

	private String[] arr = new String[] { "的请假申请", "的报销申请", "的出差申请" };
	private String[] stateArr = new String[] { "同意", "拒绝" };
	private ArrayList<HistoryData> list = null;

	@Override
	public void doHttpRespond(ArrayList<HistoryData> list) {
		this.list = list;
		if (adapter == null) {
			adapter = new CommonAdapter<HistoryData>(getActivity(), list,
					R.layout.layout_hisapproval_item) {

				@Override
				public void convert(ViewHolder holder, HistoryData item,
						int position) {
					holder.setText(R.id.resummerName, item.getUser_name());
					holder.setText(R.id.resumeType, item.getUser_name()
							+ arr[Integer.parseInt(item.getApp_type()) - 1]);
					holder.setText(R.id.resume_state, "审批完成("
							+ stateArr[Integer.parseInt(item.getState()) - 1]
							+ ")");
					holder.setText(R.id.resumeTime, item.getApp_time());
				}
			};
			hisListView.setAdapter(adapter);
			hisListView.setOnItemClickListener(this);
		} else {
			adapter.setList(list);
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (list != null) {
			HistoryData data = list.get(position);
			Bundle bundle = new Bundle();
			bundle.putSerializable("historyData", data);
			openIdeaActivityForResult(
					FragmentEnum.FRAGMENT_WAIT_ME_APPR_HISTORY, 1, bundle);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

}
