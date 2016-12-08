package com.mo.mobileoffice.function.user.ui;

import android.view.View;
import android.widget.EditText;

import com.mo.mobileoffice.R;
import com.mo.mobileoffice.common.mvp.MvpIdeaFragment;
import com.mo.mobileoffice.function.user.contract.RegisterContract;
import com.mo.mobileoffice.function.user.contract.RegisterContract.Presenter;
import com.mo.mobileoffice.function.user.presenter.RegisterPresenter;

public class RegisterFragment extends MvpIdeaFragment<RegisterContract.Presenter> implements RegisterContract.View {
	private EditText et_username;
	private EditText et_pwd;
	private EditText et_pwd_again;
	private EditText et_email;
	
	@Override
	protected void init() {
		et_username = findViewById(R.id.et_username);
		et_pwd = findViewById(R.id.et_pwd);
		et_pwd_again = findViewById(R.id.et_pwd_again);
		et_email = findViewById(R.id.et_email);
		setTitle(getResources().getString(R.string.reg_new_user));
		setRight(getResources().getString(R.string.submit));
	}
	
	@Override
	public void onClick(View v) {

	}

	@Override
	public void rightOnClick() {
		getPresenter().register(et_username.getText().toString(), et_pwd.getText().toString(),
				et_pwd_again.getText().toString(), et_email.getText().toString());
	}

	@Override
	protected int setContentViewId() {
		return R.layout.fragment_register;
	}

	@Override
	protected Presenter createPresenter() {
		return new RegisterPresenter(getActivity());
	}

	@Override
	public void registerSuccess() {
		getActivity().finish();
	}


}
