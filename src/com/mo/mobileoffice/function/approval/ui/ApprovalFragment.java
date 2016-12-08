package com.mo.mobileoffice.function.approval.ui;

import android.view.View;
import android.widget.TextView;

import com.mo.mobileoffice.R;
import com.mo.mobileoffice.common.app.FragmentEnum;
import com.mo.mobileoffice.common.mvp.MvpRadioFragment;
import com.mo.mobileoffice.function.approval.contract.ApprovalContract;
import com.mo.mobileoffice.function.approval.contract.ApprovalContract.Presenter;
import com.mo.mobileoffice.function.approval.presenter.ApprovalPresenter;


public class ApprovalFragment extends 
	MvpRadioFragment<ApprovalContract.Presenter> implements 
	ApprovalContract.View {
	private TextView tv_wait_approval;
	private TextView tv_send_approval;
	private TextView tv_leave;
	private TextView tv_expense;
	private TextView tv_on_business;

	@Override
	protected void init() {
		tv_wait_approval = findViewById(R.id.tv_wait_approval);
		tv_send_approval = findViewById(R.id.tv_send_approval);
		tv_leave = findViewById(R.id.tv_leave);
		tv_expense = findViewById(R.id.tv_reimburse);
		tv_on_business = findViewById(R.id.tv_evection);
		
		tv_wait_approval.setOnClickListener(this);
		tv_send_approval.setOnClickListener(this);
		tv_leave.setOnClickListener(this);
		tv_expense.setOnClickListener(this);
		tv_on_business.setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_leave:
			openIdeaActivity(FragmentEnum.FRAGMENT_LEAVE);
			break;
		case R.id.tv_reimburse:
			openIdeaActivity(FragmentEnum.FRAGMENT_REIMBURSE);
			break;
		case R.id.tv_evection:
			openIdeaActivity(FragmentEnum.FRAGMENT_EVECTION);
			break;
		case R.id.tv_wait_approval:
			openIdeaActivity(FragmentEnum.FRAGMENT_WAIT_ME_APPR);
			break;
		case R.id.tv_send_approval:
			openIdeaActivity(FragmentEnum.FANGMENT_APPR_OF_ME);
			break;
		default:
			break;
		}
	}

	@Override
	protected int setContentViewId() {
		return R.layout.fragment_approval;
	}

	@Override
	public void RightOnClick() {
		
	}

	@Override
	protected Presenter createPresenter() {
		return new ApprovalPresenter(getActivity());
	}

}
