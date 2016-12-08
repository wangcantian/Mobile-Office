package com.mo.mobileoffice.function.announce.presenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.mo.mobileoffice.R;
import com.mo.mobileoffice.common.base.CommBean;
import com.mo.mobileoffice.common.base.CommBeanList;
import com.mo.mobileoffice.common.mvp.BaseMvpPresenter;
import com.mo.mobileoffice.common.net.RequestArr.ACTION;
import com.mo.mobileoffice.common.tool.GsonTool;
import com.mo.mobileoffice.function.announce.bean.AnnounceBean;
import com.mo.mobileoffice.function.announce.bean.DelAnno_Request;
import com.mo.mobileoffice.function.announce.bean.GetAnno_Request;
import com.mo.mobileoffice.function.announce.bean.ReadAnno_Request;
import com.mo.mobileoffice.function.announce.contract.AnnoContract;
import com.mo.mobileoffice.function.user.model.UserModel;
import com.squareup.okhttp.Request;

public class AnnoPresenter extends BaseMvpPresenter<AnnoContract.View>
		implements AnnoContract.Presenter {
	private UserModel mUserModel;
	private List<AnnounceBean> mAdapterLists;// 适配器的公共数据
	private List<AnnounceBean> mAllLists;// 所有的公共数据（没有分类已读未读）
	
	public AnnoPresenter(Context context) {
		super(context);
		mUserModel = new UserModel(context);
	}

	@Override
	public void detachView(boolean retainInstance) {

	}

	private int mLastSwitchTextId = -1;
	@Override
	public void onSwitchTextClick(int id) {
		if (mAllLists == null || mAdapterLists == null) return;
		mAdapterLists.clear();
		if (id == R.id.tv_anno_all) {
			mAdapterLists.addAll(mAllLists);
		} else if (id == R.id.tv_anno_readed) {
			mAdapterLists.addAll(getReadedAnnounce(mAllLists));
		} else if (id == R.id.tv_anno_unread) {
			mAdapterLists.addAll(getUnReadAnnounce(mAllLists));
		}
		mLastSwitchTextId = id;
		if (getView() != null) {
			getView().notifyDataSetChange();
			getView().setTextViewChecked(id);
		}
	}
	
	// 请求所有的公告
	@Override
	public void requestAllAnno(final boolean isPullToRefresh) {
		GetAnno_Request request = new GetAnno_Request(mUserModel.getUserId(), mUserModel.getUserToken());
		request(ACTION.ACTION_GET_ANNO, request, new CallBack() {
			
			@Override
			public void onResponse(String responseStr) throws IOException {
				CommBeanList<AnnounceBean> list = GsonTool.getBaseBeanListData(responseStr, AnnounceBean.class);
				if (list.getFlag() == SUCCESS) {
					mAllLists = getSortList(list.getData());
					getUIHandler().post(new Runnable() {
						
						@Override
						public void run() {
							if (getView() != null) {
								onSwitchTextClick(mLastSwitchTextId == -1 ? R.id.tv_anno_all : mLastSwitchTextId);
								if (isPullToRefresh) getView().resetListViewHead();
							}
						}
					});
				}
				toastShowOnUI(list.getMsg());
			}
			
			@Override
			public void onFailure(Request request, IOException exception) {
				if (isPullToRefresh && getView() != null) {
					getUIHandler().post(new Runnable() {
						
						@Override
						public void run() {
							getView().resetListViewHead();
						}
					});
				}
			}
		});
	}

	// 请求改变公告状态
	@Override
	public void requestChangeAnnoState(final int anno_id) {
		ReadAnno_Request request = new ReadAnno_Request(mUserModel.getUserId(), anno_id + "", mUserModel.getUserToken());
		request(ACTION.ACTION_READ_ANNO, request, new CallBack() {
			
			@Override
			public void onResponse(String responseStr) throws IOException {
				CommBean<?> result = GsonTool.getData(responseStr, CommBean.class);
				if (result.getFlag() == 200) {// 200表示登陆成功
					mAllLists = changeAnnoState(mAllLists, anno_id + "");
					getUIHandler().post(new Runnable() {
						
						@Override
						public void run() {
							if (getView() != null) {
								onSwitchTextClick(mLastSwitchTextId);
							}
						}
					});
				}
			}
			
			@Override
			public void onFailure(Request request, IOException exception) {
				
			}
		});
	}
	
	/**
	 * 获得已读的公告数据集合
	 */
	public List<AnnounceBean> getReadedAnnounce(List<AnnounceBean> lists) {
		List<AnnounceBean> list = new ArrayList<AnnounceBean>();
		for (AnnounceBean anno : lists) {
			if (anno.getState() == 1) {
				list.add(anno);
			}
		}
		return list;
	}

	/**
	 * 获得未读的公告数据集合
	 */
	public List<AnnounceBean> getUnReadAnnounce(List<AnnounceBean> lists) {
		List<AnnounceBean> list = new ArrayList<AnnounceBean>();
		for (AnnounceBean anno : lists) {
			if (anno.getState() == 0) {
				list.add(anno);
			}
		}
		return list;
	}

	/**
	 * 获得按时间先后顺序排列的公告数据集合
	 */
	public List<AnnounceBean> getSortList(List<AnnounceBean> lists) {
		for (int i = lists.size() - 1; i > 0; i--) {
			for (int j = 0; j < i; j++) {
				if (lists.get(j).getCreate_time().compareTo(lists.get(j + 1).getCreate_time()) < 0) {
					AnnounceBean anno = lists.get(j);
					lists.set(j, lists.get(j + 1));
					lists.set(j + 1, anno);
				}
			}
		}
		return lists;
	}
	
	public List<AnnounceBean> changeAnnoState(List<AnnounceBean> lists, String anno_id) {
		for (AnnounceBean anno : lists) {
			if (anno_id.equals(anno.getId() + "")) {
				anno.setState(1);
				break;
			}
		}
		return lists;
	}
	
	public List<AnnounceBean> delAnno(List<AnnounceBean> lists, String anno_id) {
		for (AnnounceBean anno : lists) {
			if (anno_id.equals(anno.getId() + "")) {
				lists.remove(anno);
				break;
			}
		}
		return lists;
	}

	@Override
	public void initAdapter() {
		mAdapterLists = new ArrayList<AnnounceBean>();
		getView().initAdapter(mAdapterLists);
	}

	@Override
	public void requestDelAnno(final int id) {
		showProgressDialog();
		DelAnno_Request request = new DelAnno_Request(mUserModel.getUserId(), id + "", mUserModel.getUserToken());
		request(ACTION.ACTION_DEL_ANNO, request, new CallBack() {
			
			@Override
			public void onResponse(String responseStr) throws IOException {
				CommBean<?> result = GsonTool.getData(responseStr, CommBean.class);
				if (result.getFlag() == 200) {// 200表示登陆成功
					mAllLists = delAnno(mAllLists, id + "");
					getUIHandler().post(new Runnable() {
						
						@Override
						public void run() {
							if (getView() != null) {
								onSwitchTextClick(mLastSwitchTextId);
							}
						}
					});
				}
				dismissProgressDialog();
			}
			
			@Override
			public void onFailure(Request request, IOException exception) {
				dismissProgressDialog();
			}
		});
	}


}
