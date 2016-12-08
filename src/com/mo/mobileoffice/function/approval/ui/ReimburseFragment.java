package com.mo.mobileoffice.function.approval.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.mo.mobileoffice.R;
import com.mo.mobileoffice.common.app.FragmentEnum;
import com.mo.mobileoffice.common.dialog.LoadingDialog;
import com.mo.mobileoffice.common.helper.DBHelper;
import com.mo.mobileoffice.common.mvp.MvpIdeaFragment;
import com.mo.mobileoffice.common.tool.FileTool;
import com.mo.mobileoffice.common.tool.ImageTool;
import com.mo.mobileoffice.function.approval.contract.ReimburseContract;
import com.mo.mobileoffice.function.approval.contract.ReimburseContract.Presenter;
import com.mo.mobileoffice.function.approval.presenter.ReimbursePresenter;

/**
 * 报销碎片
 * 
 * @author guo
 * 
 */
public class ReimburseFragment extends
		MvpIdeaFragment<ReimburseContract.Presenter> implements
		ReimburseContract.View {
	
	private GridLayout mGridLayout;
	private RelativeLayout picture;
	private TextView addDetail;
	private LinearLayout container;
	ArrayList<EditText> editextList = new ArrayList<>();
	private EditText mCost;
	private TextView costAll;
	private EditText re_cost;
	private EditText about_type;
	private EditText detail_re;
	private LinearLayout guanlianbaoxiao;
	private TextView tv_baoxiao;
	
	int costs = 0;
	int allCost = 0;
	
	@Override
	protected void init() {
		picture = findViewById(R.id.re_picture);
		mGridLayout = findViewById(R.id.reimburse_gl_pic);
		addDetail = findViewById(R.id.addreDetail);
		container = findViewById(R.id.container);
		mCost = findViewById(R.id.cost_re);
		costAll = findViewById(R.id.allcost);
		re_cost = findViewById(R.id.cost_re);
		about_type = findViewById(R.id.about_type);
		detail_re = findViewById(R.id.detail_re);
		guanlianbaoxiao = findViewById(R.id.guanlian);
		tv_baoxiao = findViewById(R.id.tv_baoxiao);
		
		setTitle("我的报销");
		setRight("提交");
		
		picture.setOnClickListener(this);
		addDetail.setOnClickListener(this);
		guanlianbaoxiao.setOnClickListener(this);
		mCost.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (s != null && !s.toString().equals("")) {
					int value = Integer.parseInt(String.valueOf(s));
					allCost += value;
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				if (s != null && !s.toString().equals("")) {
					int value = Integer.parseInt(String.valueOf(s));
					if (value > 0) {
						allCost -= value;
					}
				}
			}

			@Override
			public void afterTextChanged(Editable s) {
				costAll.setText(allCost + "");
			}
		});
	}

	private ArrayList<String> mPicPathList;
	private final int MAX_PIC_COUNT = 3; // 最多上传图pain数量
	private final int REQUESTCODE_PHOTO = 2;
	//统计view的数量
	private ArrayList<View> viewLists=new ArrayList<>();
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.guanlian:
			showBaoXiaoDialog();
			break;
		case R.id.re_picture:
			Bundle bundle = new Bundle();
			bundle.putStringArrayList("pic_list", mPicPathList);
			bundle.putInt("pic_count", MAX_PIC_COUNT);
			openIdeaActivityForResult(FragmentEnum.FRAGMENT_PIC_SELECTOR,
					REQUESTCODE_PHOTO, bundle);
			break;

		case R.id.addreDetail:
			View view = LayoutInflater.from(getActivity()).inflate(
					R.layout.include_detail_view, null);
			TextView tv = (TextView) view.findViewById(R.id.del);
			EditText mEditext = (EditText) view
					.findViewById(R.id.cost_reimburse);
			if (editextList != null && !editextList.contains(mEditext)) {
				editextList.add(mEditext);
			}
			//监听edittext的文本变化
			mEditext.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
					if (s != null && !s.toString().equals("")) {
						int value = Integer.parseInt(String.valueOf(s));
						allCost += value;
					}
				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
					if (s != null && !s.toString().equals("")) {
						int value = Integer.parseInt(String.valueOf(s));
						if (value > 0) {
							allCost -= value;
						}
					}
				}

				@Override
				public void afterTextChanged(Editable s) {
					costAll.setText(allCost + "");
				}
			});
			tv.setOnClickListener(this);
			container.addView(view);
			viewLists.add(view);
			break;
		case R.id.del:
			LinearLayout child = (LinearLayout) v.getParent().getParent();
			RelativeLayout rl = (RelativeLayout) child.getChildAt(1);
			EditText et = (EditText) rl.getChildAt(1);
			String str = et.getText().toString();
			if (!str.equals("")) {
				int value = Integer.parseInt(str);
				allCost -= value;
			}
			costAll.setText(allCost + "");
			container.removeView(child);
			viewLists.remove((View)(v.getParent().getParent()));
			break;
		}
	}
	
	public void showBaoXiaoDialog() {
		
		final List<String> list = new ArrayList<>();
		DBHelper dbHelper = new DBHelper(getActivity());
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.query("baoxiao", null, null, null, null, null, null);
		while(cursor.moveToNext()) {
			list.add(cursor.getString(0));
		}
		
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_baoxiao, null);
		ListView lv = (ListView) view.findViewById(R.id.baoxiao);
		final ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,
				android.R.id.text1, list);
		lv.setAdapter(adapter);
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("请选择出差");
		builder.setView(view);
		final AlertDialog dialog = builder.create();
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				tv_baoxiao.setText(list.get(position));
				dialog.dismiss();
			}
		});
		dialog.show();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK
				&& requestCode == REQUESTCODE_PHOTO) {
			mPicPathList = data.getStringArrayListExtra("pic_list");
			notifyGridLayoutDraw();
		}
	}
	
	HashMap<String, Bitmap> bitmapLists = new HashMap();
	

	@SuppressLint("InflateParams")
	private void notifyGridLayoutDraw() {
		mGridLayout.removeAllViews();
		if (bitmapLists != null && bitmapLists.size() > 0) {
			for (Entry<String, Bitmap> entry : bitmapLists.entrySet()) {
				bitmapLists.remove(entry.getValue());
			}
		}
		for (String path : mPicPathList) {
			View view = LayoutInflater.from(getActivity()).inflate(
					R.layout.listitem_del_pic, null);
			ImageView iv_pic = (ImageView) view.findViewById(R.id.iv_pic);
			final ImageView iv_del = (ImageView) view.findViewById(R.id.iv_del);
			iv_del.setTag(path);
			iv_del.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					String path = (String) iv_del.getTag();
					mPicPathList.remove(path);
					notifyGridLayoutDraw();
				}
			});
			bitmapLists.put(path, ImageTool.getCompressBitmap(path, 120, 120));
			iv_pic.setImageBitmap(bitmapLists.get(path));
			mGridLayout.addView(view);
		}
	}

	@Override
	public void rightOnClick() {
		if(checkCanSubmit()){
			if(mPicPathList!=null&&mPicPathList.size()>0){
				String[] list=FileTool.getPicCompressPaths(FileTool
						.getStringArray(mPicPathList));
				File[] files=new File[mPicPathList.size()];
				String[] fileNames=new String[mPicPathList.size()];
				for(int i=0;i<mPicPathList.size();i++){
					files[i]=new File(list[i]);
					fileNames[i]=files[i].getName();
				}
				getPresenter().doHttpSubmit(re_cost, about_type, detail_re, costAll, viewLists,files,fileNames);
			}else {
				getPresenter().doHttpSubmit(re_cost, about_type, detail_re, costAll, viewLists, null,null);
			}
		}
	}

	@Override
	protected int setContentViewId() {
		return R.layout.fragment_reimburse;
	}

	@Override
	protected Presenter createPresenter() {
		return new ReimbursePresenter(getActivity());
	}

	private boolean checkCanSubmit(){
		if(re_cost.getText().toString().equals("")){
			ToastShow("请请输入报销金额");
			return false;
		}
		else if(about_type.getText().toString().equals("")){
			ToastShow("请输入报销类别");
			return false;
		}else if(detail_re.getText().toString().equals("")){
			ToastShow("请输入报销明细");
			return false;
		}else if(Integer.parseInt(re_cost.getText().toString().trim())<0){
			ToastShow("报销金额不得小于0");
			return false;
		}
		for(int i=0;i<viewLists.size();i++){
			View view=viewLists.get(i);
			EditText cost=(EditText) view.findViewById(R.id.cost_reimburse);
			EditText type=(EditText) view.findViewById(R.id.type_reimburse);
			EditText detail=(EditText) view.findViewById(R.id.detail_reimburse);
			if(cost.getText().toString().equals("")){
				ToastShow("请请输入报销金额");
				return false;
			}else if(type.getText().toString().equals("")){
				ToastShow("请输入报销类别");
				return false;
			}
			else if(detail.getText().toString().equals("")){
				ToastShow("请输入报销明细");
				return false;
			}else if(Integer.parseInt(cost.getText().toString().trim())<0){
				ToastShow("报销金额不得小于0");
				return false;
			}
		}
		return true;
	}

	@Override
	public void showDialog() {
		AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
		mBuilder.setMessage("数据已成功发送");
		mBuilder.setCancelable(false);
		mBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				getActivity().finish();
			}
		});
		mBuilder.show();
	}

	private LoadingDialog mDialog;

	@Override
	public void showProgressDialog() {
		if (mDialog == null) {
			mDialog = new LoadingDialog(getActivity());
		}
		mDialog.show(false);
	}

	@Override
	public void dismissProgressDialog() {
		if (mDialog != null) {
			mDialog.dismiss();
		}
	}

	@Override
	public void showErrorDialog() {
		AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
		mBuilder.setMessage("数据发送失败");
		mBuilder.setCancelable(false);
		mBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		mBuilder.show();
	}


}
