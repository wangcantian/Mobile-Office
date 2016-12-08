package com.mo.mobileoffice.function.approval.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class MyApproval_Respond implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -606844749479130630L;
	private String flag;
	private String msg;
	private ArrayList<MyApprovalData> data;

	public MyApproval_Respond(String flag, String msg,
			ArrayList<MyApprovalData> data) {
		super();
		this.flag = flag;
		this.msg = msg;
		this.data = data;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public ArrayList<MyApprovalData> getData() {
		return data;
	}

	public void setData(ArrayList<MyApprovalData> data) {
		this.data = data;
	}

	public class MyApprovalData implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = -514006911409779913L;
		private String id;
		private String app_type;
		private String approver;
		private String admin;
		private String state;
		private String app_time;
		private String approval_time;
		private String user_name;
		private String day;
		private String reason;
		private String pic_url;
		private String view;
		private String start;
		private String end;
		private String total;
		private ArrayList<ExplainData> explain;

		public MyApprovalData(String id, String app_type, String approver,
				String admin, String state, String app_time, String total,
				String approval_time, String user_name, String day,
				String reason, String pic_url, String view, String start,
				String end, ArrayList<ExplainData> explain) {
			super();
			this.total = total;
			this.start = start;
			this.end = end;
			this.id = id;
			this.app_type = app_type;
			this.approver = approver;
			this.admin = admin;
			this.state = state;
			this.app_time = app_time;
			this.approval_time = approval_time;
			this.user_name = user_name;
			this.day = day;
			this.reason = reason;
			this.pic_url = pic_url;
			this.view = view;
			this.explain = explain;
		}

		public String getTotal() {
			return total;
		}

		public void setTotal(String total) {
			this.total = total;
		}

		public String getStart() {
			return start;
		}

		public void setStart(String start) {
			this.start = start;
		}

		public String getEnd() {
			return end;
		}

		public void setEnd(String end) {
			this.end = end;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getApp_type() {
			return app_type;
		}

		public void setApp_type(String app_type) {
			this.app_type = app_type;
		}

		public String getApprover() {
			return approver;
		}

		public void setApprover(String approver) {
			this.approver = approver;
		}

		public String getAdmin() {
			return admin;
		}

		public void setAdmin(String admin) {
			this.admin = admin;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public String getApp_time() {
			return app_time;
		}

		public void setApp_time(String app_time) {
			this.app_time = app_time;
		}

		public String getApproval_time() {
			return approval_time;
		}

		public void setApproval_time(String approval_time) {
			this.approval_time = approval_time;
		}

		public String getUser_name() {
			return user_name;
		}

		public void setUser_name(String user_name) {
			this.user_name = user_name;
		}

		public String getDay() {
			return day;
		}

		public void setDay(String day) {
			this.day = day;
		}

		public String getReason() {
			return reason;
		}

		public void setReason(String reason) {
			this.reason = reason;
		}

		public String getPic_url() {
			return pic_url;
		}

		public void setPic_url(String pic_url) {
			this.pic_url = pic_url;
		}

		public String getView() {
			return view;
		}

		public void setView(String view) {
			this.view = view;
		}

		public ArrayList<ExplainData> getExplain() {
			return explain;
		}

		public void setExplain(ArrayList<ExplainData> explain) {
			this.explain = explain;
		}

		public class ExplainData implements Serializable {
			/**
			 * 
			 */
			private static final long serialVersionUID = -8835795183382827740L;
			private String place;
			private String start;
			private String end;
			private String type;
			private String money;
			private String detail;

			public ExplainData(String place, String start, String end,
					String type, String money, String detail) {
				super();
				this.place = place;
				this.start = start;
				this.end = end;
				this.type = type;
				this.money = money;
				this.detail = detail;
			}

			public String getPlace() {
				return place;
			}

			public void setPlace(String place) {
				this.place = place;
			}

			public String getStart() {
				return start;
			}

			public void setStart(String start) {
				this.start = start;
			}

			public String getEnd() {
				return end;
			}

			public void setEnd(String end) {
				this.end = end;
			}

			public String getType() {
				return type;
			}

			public void setType(String type) {
				this.type = type;
			}

			public String getMoney() {
				return money;
			}

			public void setMoney(String money) {
				this.money = money;
			}

			public String getDetail() {
				return detail;
			}

			public void setDetail(String detail) {
				this.detail = detail;
			}

		}
	}
}
