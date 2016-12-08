package com.mo.mobileoffice.common.receiver;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.WindowManager;

import com.mo.mobileoffice.R;
import com.mo.mobileoffice.common.app.ActivityCollector;
import com.mo.mobileoffice.function.user.ui.LoginActivity;

/** 强制下线广播 **/
public class ForceOfflineBroadCaseReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(final Context context, Intent intent) {
		AlertDialog dialog = new AlertDialog.Builder(context)
						.setTitle(R.string.warn)
						.setMessage(R.string.warn_off_line)
						.setPositiveButton(R.string.ok, new OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								ActivityCollector.finishAll();
								Intent intent = new Intent(context, LoginActivity.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								context.startActivity(intent);
							}
						}).create();
		dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		dialog.show();
	}

}
