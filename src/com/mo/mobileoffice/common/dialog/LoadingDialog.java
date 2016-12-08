package com.mo.mobileoffice.common.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;

import com.mo.mobileoffice.R;

public class LoadingDialog extends Dialog {

	@SuppressLint("InflateParams")
	public LoadingDialog(Context context) {
		super(context, R.style.myDialog);
		View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
		ProgressBar pb = (ProgressBar) view.findViewById(R.id.pb_loading);
		Drawable d = context.getResources().getDrawable(R.drawable.icon_loading1);
		pb.setLayoutParams(new LayoutParams(d.getIntrinsicWidth(), d.getIntrinsicHeight()));
		setContentView(view);
	}

	public void show(boolean flag) {
		if (!flag) {
			this.setCancelable(flag);
		}
		super.show();
	}

}
