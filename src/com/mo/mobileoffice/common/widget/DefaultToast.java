package com.mo.mobileoffice.common.widget;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.mo.mobileoffice.R;

public class DefaultToast extends Toast {

	public static void show(Context context, String msg) {
		DefaultToast toast = new DefaultToast(context, msg);
		toast.setDuration(LENGTH_SHORT);
		toast.show();
	}

	public DefaultToast(Context context, String msg) {
		super(context);
		init(context, msg);
	}

	private void init(Context context, String msg) {
		RelativeLayout rl = new RelativeLayout(context);
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		rl.setLayoutParams(params);
		rl.setPadding(25, 15, 25, 15);
		rl.setBackgroundResource(R.drawable.shape_toast_bg);
		// 创建TextView
		TextView tv = new TextView(context);
		tv.setTextColor(context.getResources().getColor(R.color.white));
		android.view.ViewGroup.LayoutParams p = new android.view.ViewGroup.LayoutParams(
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		tv.setLayoutParams(p);
		tv.setText(msg);

		rl.addView(tv);
		setView(rl);
	}

}
