package com.mo.mobileoffice.function.announce.ui;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.mo.mobileoffice.R;
import com.mo.mobileoffice.common.adapter.CommonAdapter;
import com.mo.mobileoffice.common.adapter.ViewHolder;
import com.mo.mobileoffice.common.app.FragmentEnum;
import com.mo.mobileoffice.common.mvp.MvpRadioFragment;
import com.mo.mobileoffice.common.tool.StringTool;
import com.mo.mobileoffice.common.widget.DefaultListView;
import com.mo.mobileoffice.common.widget.DefaultListView.RequestRefreshListener;
import com.mo.mobileoffice.common.widget.SlipPopWin;
import com.mo.mobileoffice.function.announce.bean.AnnounceBean;
import com.mo.mobileoffice.function.announce.contract.AnnoContract;
import com.mo.mobileoffice.function.announce.contract.AnnoContract.Presenter;
import com.mo.mobileoffice.function.announce.popview.AnnoDetailView;
import com.mo.mobileoffice.function.announce.presenter.AnnoPresenter;
import com.mo.mobileoffice.function.user.model.UserModel;
import com.squareup.picasso.Picasso;

public class AnnoFragment extends 
	MvpRadioFragment<AnnoContract.Presenter> implements
	OnItemClickListener, RequestRefreshListener, AnnoContract.View, OnItemLongClickListener {
	private static final int REQUEST_CODE = 2;
	
	private DefaultListView mListView;// 公告listview
	private TextView[] mReadStateGroup;// 全部，已读，未读
	private Drawable[] mStateDraw = new Drawable[2];// 已读未读图标
	private CommonAdapter<AnnounceBean> mAdapter;
	
	@Override
	protected void init() {
		mStateDraw[0] = getActivity().getResources().getDrawable(R.drawable.ico_unreaded);
		mStateDraw[1] = getActivity().getResources().getDrawable(R.drawable.ico_readed);
		mReadStateGroup = new TextView[3];
		mReadStateGroup[0] = findViewById(R.id.tv_anno_all);
		mReadStateGroup[1] = findViewById(R.id.tv_anno_unread);
		mReadStateGroup[2] = findViewById(R.id.tv_anno_readed);
		mListView = findViewById(R.id.lv_announce);
		mListView.setOnItemClickListener(this);
		mListView.setRequestRefreshListView(this);
		if (new UserModel(getActivity()).getUserLevel().equals("1")) {
			mListView.setOnItemLongClickListener(this);
		}
		getPresenter().initAdapter();
		for (TextView tv : mReadStateGroup) {
			tv.setOnClickListener(this);
		}
		getPresenter().requestAllAnno(false);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_anno_all:
			getPresenter().onSwitchTextClick(R.id.tv_anno_all);
			break;
		case R.id.tv_anno_readed:
			getPresenter().onSwitchTextClick(R.id.tv_anno_readed);
			break;
		case R.id.tv_anno_unread:
			getPresenter().onSwitchTextClick(R.id.tv_anno_unread);
			break;
		default:
			break;
		}
	}

	@Override
	protected int setContentViewId() {
		return R.layout.fragment_announce;
	}

	@SuppressLint("InflateParams") @Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Bundle bundle = new Bundle();
		bundle.putSerializable("anno", mAdapter.getItem(position));
		
		// 开始展示详情
		SlipPopWin popWin = new SlipPopWin(getActivity());
		popWin.setPopView(new AnnoDetailView(getActivity(), bundle));
		popWin.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
		
		// 点开详情后请求标记为已读
		if (mAdapter.getItem(position).getState() == 0) {
			getPresenter().requestChangeAnnoState(mAdapter.getItem(position).getId());
		}
	}
	
	@Override
	public void setTextViewChecked(int id) {
		for (int i = 0; i < mReadStateGroup.length; i++) {
			if (mReadStateGroup[i].getId() == id)
				mReadStateGroup[i].setSelected(true);
			else 
				mReadStateGroup[i].setSelected(false);
		}
	}

	@Override
	public void RightOnClick() {
		openIdeaActivityForResult(FragmentEnum.FRAGMENT_SEND_ANNO, REQUEST_CODE);
	}

	// listview 下拉刷新回调
	@Override
	public void onRefresh() {
		getPresenter().requestAllAnno(true);
	}

	@Override
	protected Presenter createPresenter() {
		return new AnnoPresenter(getActivity());
	}

	@Override
	public void initAdapter(List<AnnounceBean> lists) {
		mAdapter = new CommonAdapter<AnnounceBean>(getActivity(), lists, R.layout.listitem_announce) {
			
			@Override
			public void convert(ViewHolder holder, AnnounceBean item, int position) {
				if (StringTool.isNotEmpty(item.getPic_url()))
					Picasso.with(mContext).load(item.getPic_url()).into((ImageView) holder.getView(R.id.iv_headpic));
				else 
					holder.setImageResId(R.id.iv_headpic, R.drawable.ico_default_headpic);
				
				holder.setText(R.id.tv_title, item.getTitle());
				holder.setText(R.id.tv_author, item.getUser_name() + "   " + item.getCreate_time());
				holder.setText(R.id.tv_content, item.getContent());
				holder.setImageDrawable(R.id.iv_readstate, mStateDraw[item.getState()]);
			}
			
		};
		mListView.setAdapter(mAdapter);
	}

	@Override
	public void notifyDataSetChange() {
		if (mAdapter != null) {
			mAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public void resetListViewHead() {
		mListView.resetHeaderView();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE && resultCode == 1) {
			getPresenter().requestAllAnno(false);
		}
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		showSelectDialog(position);
		return false;
	}
	
	private void showSelectDialog(final int position) {
		new AlertDialog.Builder(getActivity()).setMessage(R.string.is_delete_anoo)
					.setPositiveButton(R.string.ok, new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							getPresenter().requestDelAnno(mAdapter.getItem(position).getId());
						}
					})
					.setNegativeButton(R.string.cancel, new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					}).show();
	}

}
