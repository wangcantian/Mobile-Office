package com.mo.mobileoffice.common.dialog;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.mo.mobileoffice.common.adapter.CommonAdapter;
import com.mo.mobileoffice.common.adapter.ViewHolder;

public abstract class ListViewDialog<T> extends AlertDialog {

	public ListViewDialog(Context context, List<T> list, int resId, final OnItemClickListener listener) {
		super(context);
		
		CommonAdapter<T> adapter = new CommonAdapter<T>(context, list, resId) {

			@Override
			public void convert(ViewHolder holder, T item, int position) {
				ListViewDialog.this.convert(holder, item, position);
			}
		};
		
		final ListView listView = new ListView(context);
		listView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		listView.setFadingEdgeLength(0);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (listener != null) {
					listener.onItemClick(parent, view, position, id);
				}
				dismiss();
			}
		});
		
		LinearLayout ll = new LinearLayout(context);
		ll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		ll.addView(listView);
		setView(ll);
	}

	public abstract void convert(ViewHolder holder, T item, final int position);
}
