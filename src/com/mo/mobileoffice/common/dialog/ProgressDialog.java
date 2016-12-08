package com.mo.mobileoffice.common.dialog;

import com.mo.mobileoffice.R;
import com.mo.mobileoffice.common.widget.RoundProgressBar;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class ProgressDialog extends Dialog {
	private RoundProgressBar mBar;
	private TextView mTextView;

	@SuppressLint("InflateParams") 
	public ProgressDialog(Context context) {
		super(context, R.style.myDialog);
		View view = LayoutInflater.from(context).inflate(R.layout.dialog_progress, null);
		mBar = (RoundProgressBar) view.findViewById(R.id.pb_progress);
		mTextView = (TextView) view.findViewById(R.id.tv_progress);
		setContentView(view);
		setProgress(1);
		setText(context.getResources().getString(R.string.wait));
	}

	public void show(boolean flag) {
		if (!flag) {
			this.setCancelable(flag);
		}
		super.show();
	}
	
	public void show(int progress, String text, boolean flag) {
		setProgress(progress);
		setText(text);
		show(flag);
	}
	
	public void setProgress(int progress) {
		mBar.setProgress(progress);
	}
	
	public void setText(String text) {
		mTextView.setText(text);
	}
}

