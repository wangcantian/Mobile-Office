package com.mo.mobileoffice.function.upload.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mo.mobileoffice.R;
import com.mo.mobileoffice.common.adapter.CommonAdapter;
import com.mo.mobileoffice.common.adapter.ViewHolder;
import com.mo.mobileoffice.common.app.FragmentEnum;
import com.mo.mobileoffice.common.mvp.MvpIdeaFragment;
import com.mo.mobileoffice.common.tool.DisplayTool;
import com.mo.mobileoffice.common.tool.FileTool;
import com.mo.mobileoffice.common.widget.SlipPopWin;
import com.mo.mobileoffice.function.upload.adapter.PicSeletorAdapter;
import com.mo.mobileoffice.function.upload.adapter.PicSeletorAdapter.OnPicSelectedListener;
import com.mo.mobileoffice.function.upload.bean.PicFolder;
import com.mo.mobileoffice.function.upload.contract.PicSelectorContract;
import com.mo.mobileoffice.function.upload.contract.PicSelectorContract.Presenter;
import com.mo.mobileoffice.function.upload.presenter.PicSeletorPresenter;
import com.mo.mobileoffice.function.upload.tool.ImageLoader;

public class PicSelectorFragment extends
		MvpIdeaFragment<PicSelectorContract.Presenter> implements
		PicSelectorContract.View, OnPicSelectedListener, OnItemClickListener {
	private final int MAC_PIC_COUNT = 9;
	private final int REQUESTCODE_PIC_PERVIEW = 1;
	private final int REQUESTCODE_CAMERA = 2;

	private GridView mGridView;
	private TextView tv_preview;
	private TextView tv_folders;
	private SlipPopWin mPop;

	private int mMaxPicCount;
	private int mFolderPosition = 0;
	private PicSeletorAdapter mAdapter;
	private List<String> mAdapterList;
	private List<PicFolder> mFolderList;
	private ArrayList<String> mSeletedPathList;

	@Override
	protected void init() {
		mAdapterList = new ArrayList<String>();
		mSeletedPathList = getBundle().getStringArrayList("pic_list");
		if (mSeletedPathList == null) {
			mSeletedPathList = new ArrayList<String>();
		}
		mMaxPicCount = getBundle().getInt("pic_count", 1);
		if (mMaxPicCount > MAC_PIC_COUNT || mMaxPicCount <= 0)
			mMaxPicCount = MAC_PIC_COUNT;

		mGridView = findViewById(R.id.gv_pic_list);
		tv_preview = findViewById(R.id.tv_preview);
		tv_folders = findViewById(R.id.tv_folders);
		tv_preview.setOnClickListener(this);
		tv_folders.setOnClickListener(this);
		mGridView.setOnItemClickListener(this);
		notifyHintRefresh();
		setRight(getResources().getString(R.string.ok));

		new Thread() {
			public void run() {
				mFolderList = getPresenter().getAllPicFolders();;
				getActivity().runOnUiThread(new Runnable() {

					@Override
					public void run() {
						mAdapterList.addAll(mFolderList.get(mFolderPosition)
								.getPicPaths());
						mAdapter = new PicSeletorAdapter(getActivity(),
								mAdapterList, PicSelectorFragment.this);
						mGridView.setAdapter(mAdapter);
					}
				});
			};
		}.start();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_preview:
			if (mSeletedPathList.size() > 0) {
				Bundle bundle = new Bundle();
				bundle.putStringArrayList("pic_list", mSeletedPathList);
				openIdeaActivityForResult(FragmentEnum.FRAGMENT_PIC_PREVIEW,
						REQUESTCODE_PIC_PERVIEW, bundle);
			}
			break;
		case R.id.tv_folders:
			showFolderPop();
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (parent instanceof ListView) {
			mPop.dismiss();
			mFolderPosition = position;
			getPresenter().updataAndNotifyAdapter(mAdapter, mAdapterList, mFolderList
					.get(position).getPicPaths());
			mGridView.smoothScrollToPosition(0);
		} else if (parent instanceof GridView) {
			if (position == 0) {
				openCamera();
			} else {
				CheckBox check = (CheckBox) view.getTag();
				check.setChecked(!check.isChecked());
			}
		}
	}

	private String mCameraPath;

	// 打开系统相机
	private void openCamera() {
		File file = FileTool.getPublicCameraUri();
		mCameraPath = file.getPath();
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
		startActivityForResult(intent, REQUESTCODE_CAMERA);
	}

	// 滑出文件夹选择框
	private void showFolderPop() {
		ListView lv = new ListView(getActivity());
		lv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		lv.setAdapter(new CommonAdapter<PicFolder>(getActivity(), mFolderList,
				R.layout.listitem_pic_folder) {

			@Override
			public void convert(ViewHolder holder, PicFolder item, int position) {
				if (item.getType() != PicFolder.ALL) {
					int wh = DisplayTool.dp2px(getActivity(), 50);
					ImageView iv = holder.getView(R.id.iv_firth_pic);
					ImageLoader.getInstance().loadImageFormDisk(iv,
							item.getFirstPicPath(), R.drawable.bg_default_pic,
							wh, wh);
				}

				holder.setText(R.id.tv_filename, position == 0 ? getResources()
						.getString(R.string.all_pic) : item.getFilename());
				holder.setText(R.id.tv_count, item.getCount() + "");
			}
		});
		lv.setOnItemClickListener(this);

		mPop = new SlipPopWin(getActivity());
		mPop.setMainView(lv);
		mPop.showAtLocation(tv_folders, Gravity.CENTER_VERTICAL | Gravity.TOP,
				0, 0);
	}

	@Override
	public void rightOnClick() {
		Intent intent = new Intent();
		intent.putStringArrayListExtra("pic_list", mSeletedPathList);
		getActivity().setResult(Activity.RESULT_OK, intent);
		getActivity().finish();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == REQUESTCODE_PIC_PERVIEW) {
				getPresenter().updataAndNotifyAdapter(null, mSeletedPathList,
						data.getStringArrayListExtra("pic_list"));
				rightOnClick();
			} else if (requestCode == REQUESTCODE_CAMERA) {
				mSeletedPathList.add(mCameraPath);
				getPresenter().addPicToPicFolder(mFolderList, mCameraPath);
				getPresenter().updataAndNotifyAdapter(mAdapter, mAdapterList,
						mFolderList.get(mFolderPosition).getPicPaths());
				notifyHintRefresh();
			}
		} else if (resultCode == Activity.RESULT_CANCELED
				&& requestCode == REQUESTCODE_PIC_PERVIEW) {
			getPresenter().updataAndNotifyAdapter(mAdapter, mSeletedPathList,
					data.getStringArrayListExtra("pic_list"));
		}
	}

	@Override
	protected int setContentViewId() {
		return R.layout.fragment_pic_selector;
	}

	@Override
	public boolean onPicSelected(String path) {
		return addSelectedSet(path);
	}

	@Override
	public void onPicRemove(String path) {
		removeSelectedSet(path);
	}

	@Override
	public boolean isPicPathSelected(String path) {
		return mSeletedPathList.contains(path);
	}

	/** 移除选择图片的路径 **/
	private void removeSelectedSet(String path) {
		mSeletedPathList.remove(path);
		notifyHintRefresh();
	}

	/** 添加选择图片的路径 **/
	private boolean addSelectedSet(String path) {
		if (mSeletedPathList.contains(path)) {
			mSeletedPathList.remove(path);
		}
		mSeletedPathList.add(path);
		if (mSeletedPathList.size() <= mMaxPicCount) {
			notifyHintRefresh();
			return true;
		} else {
			removeSelectedSet(path);
			ToastShow(String.format(
					getResources().getString(R.string.max_sel_pic_count),
					mMaxPicCount));
			return false;
		}
	}

	/** 通知标题以及预览提示更新 **/
	private void notifyHintRefresh() {
		setTitle(mSeletedPathList.size() + "/" + mMaxPicCount);
		setPreviewText(mSeletedPathList.size());
	}

	@Override
	protected void setTitle(String title) {
		super.setTitle(String.format(
				getResources().getString(R.string.sel_pic_count), title));
	}

	private void setPreviewText(int size) {
		tv_preview.setText(String.format(
				getResources().getString(R.string.preview), size));
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		// 退出释放内存以及取消所有任务
		System.out.println("onDestroyView");
		ImageLoader.getInstance().cancelTask();
		ImageLoader.getInstance().clearCache();
	}

	@Override
	protected Presenter createPresenter() {
		return new PicSeletorPresenter(getActivity());
	}

}
