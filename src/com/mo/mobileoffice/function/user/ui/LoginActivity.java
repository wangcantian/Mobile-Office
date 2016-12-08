package com.mo.mobileoffice.function.user.ui;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mo.mobileoffice.R;
import com.mo.mobileoffice.common.app.FragmentEnum;
import com.mo.mobileoffice.common.dialog.LoadingDialog;
import com.mo.mobileoffice.common.mvp.MvpActivity;
import com.mo.mobileoffice.common.tool.DisplayTool;
import com.mo.mobileoffice.common.tool.ImageTool;
import com.mo.mobileoffice.common.tool.PreTool;
import com.mo.mobileoffice.common.widget.CricleImageView;
import com.mo.mobileoffice.function.comm.ui.MainActivity;
import com.mo.mobileoffice.function.user.contract.LoginContract;
import com.mo.mobileoffice.function.user.contract.LoginContract.Presenter;
import com.mo.mobileoffice.function.user.presenter.LoginPresenter;

public class LoginActivity extends MvpActivity<LoginContract.Presenter> implements LoginContract.View {
	
	private CricleImageView iv_head;
	private EditText et_usename;
	private EditText et_pwd;
	private Button btn_login;
	private TextView tv_register;
	private LoadingDialog mDialog;
	
	@Override
	protected void init() {
		// 每当到登陆页面，标记用户未登陆状态
		getPresenter().signUnLogin();
		
		mDialog = new LoadingDialog(this);
		iv_head = findViewByIds(R.id.iv_head);
		et_usename = findViewByIds(R.id.et_username);
		et_pwd = findViewByIds(R.id.et_pwd);
		btn_login = findViewByIds(R.id.btn_login);
		tv_register = findViewByIds(R.id.tv_register);
		
		btn_login.setOnClickListener(this);
		tv_register.setOnClickListener(this);
		
		int s = DisplayTool.dp2px(this, 60);
		Drawable d = getResources().getDrawable(R.drawable.headpic);
		BitmapDrawable bm = (BitmapDrawable) ImageTool.changeBitmapSize(d, s, s);
		iv_head.setImageBitmap(bm.getBitmap());
		
		et_usename.setText(PreTool.getString(this, "use_name", ""));
		et_pwd.setText(PreTool.getString(this, "use_pwd", ""));
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_login:
			String usename = et_usename.getText().toString().trim();
			String pwd = et_pwd.getText().toString().trim();
			getPresenter().login(usename, pwd);
			break;
		case R.id.tv_register:
			openIdeaActivity(FragmentEnum.FRAGMENT_REGISTER);
			break;
		default:
			break;
		}
	}

	@Override
	protected int setContentViewId() {
		return R.layout.activity_login;
	}
	
	@Override
	protected Presenter createPresenter() {
		return new LoginPresenter(this);
	}

	@Override
	public void loginSuccess() {
		openActivity(MainActivity.class);
		finish();
	}

	@Override
	public void showLoadingDialog() {
		mDialog.show(false);
	}

	@Override
	public void dismissLoadingDialog() {
		if (mDialog.isShowing()) {
			mDialog.dismiss();
		}
	}

	
}
