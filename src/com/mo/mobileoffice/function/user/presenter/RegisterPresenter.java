package com.mo.mobileoffice.function.user.presenter;

import java.io.IOException;

import android.content.Context;

import com.mo.mobileoffice.R;
import com.mo.mobileoffice.common.base.CommBean;
import com.mo.mobileoffice.common.mvp.BaseMvpPresenter;
import com.mo.mobileoffice.common.net.RequestArr.ACTION;
import com.mo.mobileoffice.common.tool.GsonTool;
import com.mo.mobileoffice.common.tool.StringTool;
import com.mo.mobileoffice.function.user.bean.Register_Request;
import com.mo.mobileoffice.function.user.contract.RegisterContract;
import com.squareup.okhttp.Request;

public class RegisterPresenter extends BaseMvpPresenter<RegisterContract.View>
		implements RegisterContract.Presenter {

	public RegisterPresenter(Context context) {
		super(context);
	}

	@Override
	public void detachView(boolean retainInstance) {
		
	}

	@Override
	public void register(String usename, String pwd, String confirmPwd, String email) {
		boolean flag = checkRegisterInfo(usename, pwd, confirmPwd, email);
		if (!flag) return;
		showProgressDialog();
		Register_Request request = new Register_Request(usename, pwd, email);
		request(ACTION.ACTION_REGISTER_USER, request, new CallBack() {
			
			@Override
			public void onResponse(String responseStr) throws IOException {
				System.out.println(responseStr);
				dismissProgressDialog();
				CommBean<?> result = GsonTool.getData(responseStr, CommBean.class);
				if (result.getFlag() == 200) {
					toastShowOnUI(result.getMsg());
					getView().registerSuccess();
				} else {
					toastShowOnUI(result.getMsg());
				}
			}
			
			@Override
			public void onFailure(Request request, IOException exception) {
				dismissProgressDialog();
			}
		});
	}
	
	/**
	 * 检测用户输入信息是否准确
	 */
	private boolean checkRegisterInfo(String usename, String pwd, String confirmPwd, String email) {
		if (StringTool.isEmpty(usename) || StringTool.isEmpty(pwd)
				|| StringTool.isEmpty(confirmPwd) || StringTool.isEmpty(email)) {
			toastShowOnUI(mContext.getResources().getString(R.string.userinfo_not_null));
			return false;
		}
		if (!pwd.equals(confirmPwd)) {
			toastShowOnUI(mContext.getResources().getString(R.string.two_password_not_consistent));
			return false;
		}
		if (!StringTool.checkEmailInput(email)) {
			toastShowOnUI(mContext.getResources().getString(R.string.please_input_real_email));
			return false;
		}
		return true;
	}

}
