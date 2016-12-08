package com.mo.mobileoffice.function.meeting.presenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.mo.mobileoffice.R;
import com.mo.mobileoffice.common.base.CommBeanList;
import com.mo.mobileoffice.common.mvp.BaseMvpPresenter;
import com.mo.mobileoffice.common.net.RequestArr.ACTION;
import com.mo.mobileoffice.common.tool.GsonTool;
import com.mo.mobileoffice.common.tool.StringTool;
import com.mo.mobileoffice.function.meeting.bean.FindRoom_Request;
import com.mo.mobileoffice.function.meeting.bean.FloorBean;
import com.mo.mobileoffice.function.meeting.bean.RoomBean;
import com.mo.mobileoffice.function.meeting.bean.SearchInfo;
import com.mo.mobileoffice.function.meeting.contract.MeetingContract;
import com.mo.mobileoffice.function.meeting.dao.FloorDao;
import com.mo.mobileoffice.function.meeting.dao.RoomDao;
import com.mo.mobileoffice.function.user.model.UserModel;
import com.squareup.okhttp.Request;

public class MeetingPresenter extends BaseMvpPresenter<MeetingContract.View>
		implements MeetingContract.Presenter {

	private List<FloorBean> mFloorAdapterList;
	private List<RoomBean> mRoomAdapterList;
	private RoomDao mRoomDao;
	private UserModel mUserModel;

	public MeetingPresenter(Context context) {
		super(context);
		mRoomDao = new RoomDao(mContext);
		mUserModel = new UserModel(mContext);
		mFloorAdapterList = new FloorDao(mContext).selectAllDatas();
	}

	@Override
	public void initTabViews() {
		// 初始化添加“全部”楼层item
		mFloorAdapterList.add(0, new FloorBean(-1, 0, "全部"));
		getView().initTabViews(mFloorAdapterList);
	}

	@Override
	public void initContainerView() {
		mRoomAdapterList = new ArrayList<RoomBean>();
		mRoomAdapterList.addAll(mRoomDao.selectAllDatas());
		getView().initContainerView(mRoomAdapterList);
	}

	/** 通过楼层id找对应的楼名 **/
	@Override
	public String searchFloorNameById(int floor_id) {
		for (FloorBean bean : mFloorAdapterList) {
			if (bean.getId() == floor_id) {
				return bean.getFloor_name();
			}
		}
		return null;
	}

	@Override
	public List<String> getTabTexts() {
		List<String> tabTexts = new ArrayList<String>();
		tabTexts.add(mContext.getResources().getString(R.string.floor));
		tabTexts.add(mContext.getResources().getString(R.string.time));
		tabTexts.add(mContext.getResources().getString(R.string.more));
		return tabTexts;
	}

	@Override
	public void searchByCondition(SearchInfo searchInfo) {
		if (searchInfo.isAll) {
			if (searchInfo.floor_num == 0) {
				mRoomAdapterList.clear();
				mRoomAdapterList.addAll(mRoomDao.selectAllDatas());
			} else {
				mRoomAdapterList.clear();
				mRoomAdapterList.addAll(mRoomDao.getRoomBeanByFloorId(searchInfo.floor_id + ""));
			}
			screenPhysicalInfo(mRoomAdapterList, searchInfo.isWIFI, searchInfo.isProjector, searchInfo.isAir_Conditioner);
			getView().notifyViewUpdate();
		} else {
			getView().showDialog();
			FindRoom_Request request = new FindRoom_Request(mUserModel.getUserId(), mUserModel.getUserToken(),
					searchInfo.floor_id, StringTool.DateToString1(searchInfo.time.getTime()), searchInfo.minTime);
			request(ACTION.ACTION_FIND_ROOM, request, new CallBack() {
				
				@Override
				public void onResponse(String responseStr) throws IOException {
					CommBeanList<Integer> bean = GsonTool.getBaseBeanListData(responseStr, Integer.class);
					if (bean.getFlag() == SUCCESS) {
						mRoomAdapterList.clear();
						for (int id : bean.getData()) {
							mRoomAdapterList.add(mRoomDao.selectDataById(id + ""));
						}
					} else {
						toastShowOnUI(bean.getMsg());
					}
					getUIHandler().post(new Runnable() {
						
						@Override
						public void run() {
							getView().dismissDialog();
							getView().notifyViewUpdate();
						}
					});
				}
				
				@Override
				public void onFailure(Request request, IOException exception) {
					getUIHandler().post(new Runnable() {
						
						@Override
						public void run() {
							getView().dismissDialog();
						}
					});
				}
			});
		}
	}
	
	/** 对房间的物理信息再进行筛选 **/
	private void screenPhysicalInfo(List<RoomBean> lists, int isWIFI, int isPro, int isAir) {
		List<RoomBean> tempLists = new ArrayList<RoomBean>();
		tempLists.addAll(lists);
		for (RoomBean bean : tempLists) {
			if (isWIFI != -1 && isWIFI != bean.getWifi()) {
				lists.remove(bean); continue;
			}
			if (isPro != -1 && isPro != bean.getProjector()) {
				lists.remove(bean); continue;
			}
			if (isAir != -1 && isAir != bean.getAir_con()) {
				lists.remove(bean); continue;
			}
		}
	}

	@Override
	public void detachView(boolean retainInstance) {
		// 视图销毁，关闭所有数据库连接
		mRoomDao.close();
		unBinding();
	}
}
