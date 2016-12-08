package com.mo.mobileoffice.common.app;

import java.util.HashMap;

import com.mo.mobileoffice.common.base.BaseFragment;
import com.mo.mobileoffice.function.announce.ui.AnnoFragment;
import com.mo.mobileoffice.function.announce.ui.SendAnnoFragment;
import com.mo.mobileoffice.function.approval.ui.ApprOfMeFragment;
import com.mo.mobileoffice.function.approval.ui.ApprovalDetailFragment;
import com.mo.mobileoffice.function.approval.ui.ApprovalFragment;
import com.mo.mobileoffice.function.approval.ui.ApprovalHistoryDetailFragment;
import com.mo.mobileoffice.function.approval.ui.EvectionFragment;
import com.mo.mobileoffice.function.approval.ui.LeaveFragment;
import com.mo.mobileoffice.function.approval.ui.MyApprovalDetailFragment;
import com.mo.mobileoffice.function.approval.ui.ReimburseFragment;
import com.mo.mobileoffice.function.approval.ui.ShowImageFragment;
import com.mo.mobileoffice.function.approval.ui.WaitMeApprFragment;
import com.mo.mobileoffice.function.checkin.ui.CheckinFragment;
import com.mo.mobileoffice.function.checkin.ui.CheckinHistoryFragment;
import com.mo.mobileoffice.function.checkin.ui.MapFragment;
import com.mo.mobileoffice.function.comm.ui.ContentFragment;
import com.mo.mobileoffice.function.comm.ui.LeftMenuFragment;
import com.mo.mobileoffice.function.comm.ui.SettingFragment;
import com.mo.mobileoffice.function.meeting.ui.ApplyMeetingFragment;
import com.mo.mobileoffice.function.meeting.ui.MeetingApprDetailFragment;
import com.mo.mobileoffice.function.meeting.ui.MeetingApprFragment;
import com.mo.mobileoffice.function.meeting.ui.MeetingDetailFragment;
import com.mo.mobileoffice.function.meeting.ui.MeetingFragment;
import com.mo.mobileoffice.function.meeting.ui.MyMeetingApplyFragment;
import com.mo.mobileoffice.function.meeting.ui.RoomDetailFragment;
import com.mo.mobileoffice.function.upload.ui.PicPreviewFragment;
import com.mo.mobileoffice.function.upload.ui.PicSelectorFragment;
import com.mo.mobileoffice.function.user.ui.PersInfoFragment;
import com.mo.mobileoffice.function.user.ui.RegisterFragment;

public class FragmentFactory {

	private static HashMap<FragmentEnum, BaseFragment> mHashMap = new HashMap<FragmentEnum, BaseFragment>();
	private static FragmentFactory mFactory = null;

	private FragmentFactory() {
	}

	public static FragmentFactory getInstance() {
		if (mFactory == null) {
			mFactory = new FragmentFactory();
		}
		return mFactory;
	}

	@SuppressWarnings("unchecked")
	public static <T extends BaseFragment> T getFragment(FragmentEnum type) {
		getInstance();
		BaseFragment mBaseFragment = FragmentFactory.mHashMap.get(type);
		if (mBaseFragment == null) {
			switch (type) {
			case FRAGMENT_RADIO_ANNOUNCE:
				mBaseFragment = new AnnoFragment();
				break;
			case FRAGMENT_RADIO_CHECK_IN:
				mBaseFragment = new CheckinFragment();
				break;
			case FRAGMENT_RADIO_APPROVAL:
				mBaseFragment = new ApprovalFragment();
				break;
			case FRAGMENT_RADIO_MEETING_ROOM:
				mBaseFragment = new MeetingFragment();
				break;
			case FRAGMENT_CONTNET:
				mBaseFragment = new ContentFragment();
				break;
			case FRAGMENT_LEFTMENU:
				mBaseFragment = new LeftMenuFragment();
				break;
			case FRAGMENT_REGISTER:
				mBaseFragment = new RegisterFragment();
				break;
			case FRAGMENT_SEND_ANNO:
				mBaseFragment = new SendAnnoFragment();
				break;
			case FRAGMENT_LEAVE:
				mBaseFragment = new LeaveFragment();
				break;
			case FRAGMENT_CHECKIN_HISTORY:
				mBaseFragment = new CheckinHistoryFragment();
				break;
			case FRAGMENT_PIC_SELECTOR:
				mBaseFragment = new PicSelectorFragment();
				break;
			case FRAGMENT_PIC_PREVIEW:
				mBaseFragment = new PicPreviewFragment();
				break;
			case FRAGMENT_PERS_INFO:
				mBaseFragment = new PersInfoFragment();
				break;
			case FRAGMENT_MAP:
				mBaseFragment = new MapFragment();
				break;
			case FRAGMENT_MEETING_DETAIL:
				mBaseFragment = new MeetingDetailFragment();
				break;
			case FRAGMENT_APPLY_MEETING:
				mBaseFragment = new ApplyMeetingFragment();
				break;
			case FRAGMENT_MEETING_APPROVAL:
				mBaseFragment = new MeetingApprFragment();
				break;
			case FRAGMENT_ROOM_DETAIL:
				mBaseFragment = new RoomDetailFragment();
				break;
			case FRAGMENT_EVECTION:
				mBaseFragment = new EvectionFragment();
				break;
			case FRAGMENT_REIMBURSE:
				mBaseFragment = new ReimburseFragment();
				break;
			case FRAGMENT_WAIT_ME_APPR:
				mBaseFragment = new WaitMeApprFragment();
				break;
			case FANGMENT_APPR_OF_ME:
				mBaseFragment = new ApprOfMeFragment();
				break;
			case FRAGMENT_WAIT_ME_APPR_DETAIL:
				mBaseFragment = new ApprovalDetailFragment();
				break;
			case FRAGMENT_WAIT_ME_APPR_HISTORY:
				mBaseFragment = new ApprovalHistoryDetailFragment();
				break;
			case FRAGMENT_APPROVALDETAIL_IMAGE_LOOK:
				mBaseFragment = new ShowImageFragment();
				break;
			case FRAGMENT_MYPROVAL_DETAIL:
				mBaseFragment = new MyApprovalDetailFragment();
				break;
			case FRAGMENT_SETTING:
				mBaseFragment = new SettingFragment();
				break;
			case FRAGMENT_MEETING_APPR_DETAIL:
				mBaseFragment = new MeetingApprDetailFragment();
				break;
			case FRAGMENT_MY_MEETING_APPLY:
				mBaseFragment = new MyMeetingApplyFragment();
				break;
			default:
				return null;
			}
			mHashMap.put(type, mBaseFragment);
		}
		return (T) mBaseFragment;
	}

	public static void clear() {
		getInstance();
		FragmentFactory.mHashMap.clear();
	}

	public static void removeFrag(FragmentEnum type) {
		getInstance();
		FragmentFactory.mHashMap.remove(type);
	}

}
