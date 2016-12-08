package com.mo.mobileoffice.function.user.ui;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mo.mobileoffice.R;
import com.mo.mobileoffice.common.app.FragmentEnum;
import com.mo.mobileoffice.common.mvp.MvpIdeaFragment;
import com.mo.mobileoffice.common.tool.PreTool;
import com.mo.mobileoffice.common.tool.StringTool;
import com.mo.mobileoffice.function.user.bean.UserBean;
import com.mo.mobileoffice.function.user.contract.PersInfoContract;
import com.mo.mobileoffice.function.user.contract.PersInfoContract.Presenter;
import com.mo.mobileoffice.function.user.presenter.PersInfoPresenter;
import com.squareup.picasso.Picasso;

/** 用户信息页 **/
public class PersInfoFragment extends MvpIdeaFragment<PersInfoContract.Presenter> implements PersInfoContract.View {
	private final int REQUESTCODE_PICSELECTOR = 1;
	private final int MAX_PIC_COUNT = 1;
	
	private LinearLayout ll_content;
	private ImageView iv_headpic;
	private TextView tv_name;
	private TextView tv_username;
	private TextView tv_email;
	private TextView tv_moblie;
	private TextView tv_sex;
	private TextView tv_birthday;
	
	private boolean mIsChange = false;

	@Override
	protected void init() {
		ll_content = findViewById(R.id.ll_content);
		iv_headpic = findViewById(R.id.iv_headpic);
		tv_name = findViewById(R.id.tv_name);
		tv_username = findViewById(R.id.tv_username);
		tv_email = findViewById(R.id.tv_email);
		tv_moblie = findViewById(R.id.tv_mobile);
		tv_sex = findViewById(R.id.tv_sex);
		tv_birthday = findViewById(R.id.tv_birthday);
		for (int i = 0; i < ll_content.getChildCount(); i++) {
			View rl = ll_content.getChildAt(i);
			rl.setTag(i);
			rl.setOnClickListener(this);
		}
		setTitle(getResources().getString(R.string.i));
		getPresenter().initView();
	}

	@Override
	public void refreshView(UserBean bean) {
		mIsChange = true;
		Picasso.with(getActivity()).load(bean.getPic_url()).placeholder(R.drawable.ico_default_headpic).into(iv_headpic);
		tv_name.setText(StringTool.isEmpty(bean.getName()) ? "" : bean.getName());
		tv_username.setText(PreTool.getString(getActivity(), "use_name", ""));
		tv_email.setText(StringTool.isEmpty(bean.getEmail()) ? "" : bean.getEmail());
		tv_moblie.setText(StringTool.isEmpty(bean.getMobile()) ? "" : bean.getMobile());
		tv_sex.setText(StringTool.isEmpty(bean.getSex()) ? "" : bean.getSex());
		tv_birthday.setText(StringTool.isEmpty(bean.getBirthday()) ? "" : bean.getBirthday());
	}
	
	@Override
	public void onClick(View v) {
		if (v instanceof RelativeLayout) {
			switch ((int)v.getTag()) {
			case 0:// 头像
				Bundle bundle = new Bundle();
				bundle.putInt("pic_count", MAX_PIC_COUNT);
				openIdeaActivityForResult(FragmentEnum.FRAGMENT_PIC_SELECTOR, REQUESTCODE_PICSELECTOR, bundle);
				break;
			case 9:// 性别
				showSingleChoiceItemsDialog();
				break;
			case 11:// 生日
				showDateDialog();
				break;
			case 2:// 姓名
				showEditTextDialog(R.string.input_name, EditorInfo.TYPE_CLASS_TEXT);
				break;
			case 8:// 手机
				showEditTextDialog(R.string.input_phone, EditorInfo.TYPE_CLASS_PHONE);
				break;
			default:
				break;
			}
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUESTCODE_PICSELECTOR && resultCode == Activity.RESULT_OK) {
			String path = data.getStringArrayListExtra("pic_list").get(0);
			getPresenter().uploadHead(path);
		}
	}

	@Override
	public void rightOnClick() {
		
	}

	@Override
	protected int setContentViewId() {
		return R.layout.fragment_pers_info;
	}
	
	private void showEditTextDialog(final int titleId, int inputType) {
		final EditText et = new EditText(getActivity());
		et.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		et.setInputType(inputType);
		new AlertDialog.Builder(getActivity()).setView(et)
								.setTitle(titleId)
								.setPositiveButton(getResources().getString(R.string.ok), 
										new OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {
										if (titleId == R.string.input_name) {
											getPresenter().changeName(et.getText().toString());
										} else if (titleId == R.string.input_phone) {
											getPresenter().changePhone(et.getText().toString());
										}
									}
								})
								.setNegativeButton(getResources().getString(R.string.cancel), 
										new OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {
										dialog.dismiss();
									}
								}).show();
	}

	private int mLastSelectedIndex = -1;

	private void showSingleChoiceItemsDialog() {
		final String male = getResources().getString(R.string.male);
		final String female = getResources().getString(R.string.female);
		new AlertDialog.Builder(getActivity()).setSingleChoiceItems(
				new String[] { male, female }, mLastSelectedIndex,
				new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						tv_sex.setText(which == 0 ? male : female);
						dialog.dismiss();
						if (mLastSelectedIndex != which) {
							getPresenter().changeSex(tv_sex.getText().toString());
						}
						mLastSelectedIndex = which;
					}
				}).show();
	}

	private void showDateDialog() {
		Calendar calendar = Calendar.getInstance();
		final DatePickerDialog mdialog = new DatePickerDialog(getActivity(), null, 
				calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));
		mdialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
		mdialog.setButton(DialogInterface.BUTTON_POSITIVE, 
				getResources().getString(R.string.finish), 
				new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				DatePicker dataPicker = mdialog.getDatePicker();
				tv_birthday.setText(StringTool.transformDate(dataPicker.getYear(), dataPicker.getMonth() + 1, dataPicker.getDayOfMonth()));
				mdialog.dismiss();
				// 请求修改用户生日数据
				getPresenter().changeBirthday(tv_birthday.getText().toString());
			}
		});
		mdialog.setButton(DialogInterface.BUTTON_NEGATIVE, 
				getResources().getString(R.string.cancel), 
				new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mdialog.dismiss();
			}
		});
		mdialog.show();
	}

	@Override
	protected Presenter createPresenter() {
		return new PersInfoPresenter(getActivity());
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (mIsChange)
			getActivity().setResult(1);
		getActivity().finish();
		return true;
	}
	
	@Override
	public void leftOnClick() {
		if (mIsChange)
			getActivity().setResult(1);
		super.leftOnClick();
	}

}
