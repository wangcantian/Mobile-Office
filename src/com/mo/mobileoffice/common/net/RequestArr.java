package com.mo.mobileoffice.common.net;

import java.util.HashMap;

/** 请求接口 **/
public class RequestArr {
	public static final String requestArg = "args";
//	public static String mainUrl = "http://192.168.191.1/index.php?";
	public static String mainUrl = "http://110.64.211.42/index.php?";

	// public static final String mainUrl =
	// "http://office.itooben.com/index.php?";

	public enum ACTION {
		/** 发公告 **/
		ACTION_SEND_ANNO,

		/** 检查用户登录信息 **/
		ACTION_CHECK_USER_INFO,

		/** 注册用户 **/
		ACTION_REGISTER_USER,

		/** 请求公告 **/
		ACTION_GET_ANNO,

		/** 将公告标记为已读 **/
		ACTION_READ_ANNO,

		/** 删除公告 **/
		ACTION_DEL_ANNO,

		/** 修改用户资料 **/
		ACTION_MODIFY_USER_INFO,

		/** 签到 **/
		ACTION_CHECKIN,

		/** 记录签到历史 **/
		ACTION_CHECKINHISTORY,

		/** 楼层信息 **/
		ACTION_FLOOR_INFO,

		/** 房间信息 **/
		ACTION_ROOM_INFO,

		/** 签到历史记录页面 */
		ACTION_CHECKIN_HISTORYFRAGMENT,

		/** 查询最近签到的数据 */
		ACTION_CHECKIN_RECENT,

		/** 审批(请假、报销、出差)提交数据到服务器 */
		ACTION_APPROVAL,

		/** 审批(出差)提交数据到服务器 */
		ACTION_APPROVAL_EVECTION,
		/** 审批(出差)提交数据到服务器 */
		ACTION_APPROVAL_REIMBURSE,
		/** 查看(待我审批的单的数据) */
		ACTION_APPROVAL_DETAIL,
		/** 查看(我已审批的单的数据) */
		ACTION_APPROVAL_HISTORY,
		/** 提交审批的决定 */
		ACTION_APPROVAL_SUBMIT,
		/** 获取我申请过的 */
		ACTION_MYAPPROVAL,
		/** 通过条件查询房间 **/
		ACTION_FIND_ROOM,

		/** 查找会议信息 **/
		ACTION_FIND_MEETING,

		/** 申请会议室 **/
		ACTION_APPLY_MEETING,

		/** 判断会议室的状态 **/
		ACTION_JUDGE_ROOM,
		
		/** 查看会议待我审批 **/
		ACTION_MEETING_WAIT_APPR,

		/** 查看会议我已审批 **/
		ACTION_MEETING_ALREADY_APPR,

		/** 审批会议 **/
		ACTION_APPROVAL_MEETING,

		/** 上传头像 **/
		ACTION_UPLOAD_HEAD,

		/** 查看我的会议申请记录 **/
		ACTION_MY_MEETING_APPLY,
		
		/** 获取用户信息 **/
		ACTION_GET_USERINFO
	}

	public static HashMap<ACTION, String> mUrls = new HashMap<ACTION, String>();

	static {
		mUrls.put(ACTION.ACTION_SEND_ANNO, "c=announce&a=issue");
		mUrls.put(ACTION.ACTION_CHECK_USER_INFO, "c=login&a=login");
		mUrls.put(ACTION.ACTION_REGISTER_USER, "c=login&a=register");
		mUrls.put(ACTION.ACTION_GET_ANNO, "c=announce&a=see");
		mUrls.put(ACTION.ACTION_READ_ANNO, "c=announce&a=read");
		mUrls.put(ACTION.ACTION_DEL_ANNO, "c=announce&a=delete");
		mUrls.put(ACTION.ACTION_MODIFY_USER_INFO, "c=user&a=update");
		mUrls.put(ACTION.ACTION_CHECKIN, "c=Sign&a=sign");
		mUrls.put(ACTION.ACTION_CHECKINHISTORY, "c=Sign&a=date");
		mUrls.put(ACTION.ACTION_FLOOR_INFO, "c=Meeting&a=floors");
		mUrls.put(ACTION.ACTION_ROOM_INFO, "c=Meeting&a=rooms");
		mUrls.put(ACTION.ACTION_CHECKIN_HISTORYFRAGMENT, "c=Sign&a=see");
		mUrls.put(ACTION.ACTION_CHECKIN_RECENT, "c=Sign&a=see");
		mUrls.put(ACTION.ACTION_APPROVAL, "c=Approval&a=app_leave");
		mUrls.put(ACTION.ACTION_APPROVAL_EVECTION, "c=Approval&a=app_trip");
		mUrls.put(ACTION.ACTION_APPROVAL_REIMBURSE, "c=Approval&a=app_reim");
		mUrls.put(ACTION.ACTION_APPROVAL_DETAIL, "c=Approval&a=wait_approval");
		mUrls.put(ACTION.ACTION_APPROVAL_HISTORY, "c=Approval&a=already_approval");
		mUrls.put(ACTION.ACTION_APPROVAL_SUBMIT, "c=Approval&a=approval");
		mUrls.put(ACTION.ACTION_MYAPPROVAL, "c=Approval&a=see_apply");
		mUrls.put(ACTION.ACTION_FIND_ROOM, "c=Meeting&a=findRooms");
		mUrls.put(ACTION.ACTION_FIND_MEETING, "c=Meeting&a=seeMeeting");
		mUrls.put(ACTION.ACTION_APPLY_MEETING, "c=Meeting&a=apply");
		mUrls.put(ACTION.ACTION_JUDGE_ROOM, "c=Meeting&a=judge_room");
		mUrls.put(ACTION.ACTION_MEETING_WAIT_APPR, "c=Meeting&a=wait_approval");
		mUrls.put(ACTION.ACTION_MEETING_ALREADY_APPR, "c=Meeting&a=already_approval");
		mUrls.put(ACTION.ACTION_APPROVAL_MEETING, "c=Meeting&a=approval");
		mUrls.put(ACTION.ACTION_UPLOAD_HEAD, "c=User&a=head");
		mUrls.put(ACTION.ACTION_MY_MEETING_APPLY, "c=Meeting&a=myApply");
		mUrls.put(ACTION.ACTION_GET_USERINFO, "c=User&a=userInfo");
	}
}
