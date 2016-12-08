package com.mo.mobileoffice.function.approval.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class ApprovalDetail_Respond implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3235158558489065831L;
	private String flag;
	private String msg;
	private ArrayList<DetailData> data;

	public ApprovalDetail_Respond(String flag, String msg,
			ArrayList<DetailData> data) {
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

	public ArrayList<DetailData> getData() {
		return data;
	}

	public void setData(ArrayList<DetailData> data) {
		this.data = data;
	}

	public class DetailData implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 850404204677017613L;
		private String id;
		private String app_type;
		private String type;
		private String approver;
		private String admin;
		private String state;
		private String app_time;
		private String user_name;
		private String day;
		private String pic_url;
		private String total;
		private String reason;
		private String start;
		private String end;
		private ArrayList<ListData> explain;

		public DetailData(String id, String app_type, String approver,
				String admin, String state, String app_time, String user_name,
				String day, String pic_url, String total, String reason,
				String type, String start, String end,
				ArrayList<ListData> explain) {
			super();
			this.id = id;
			this.app_type = app_type;
			this.start = start;
			this.end = end;
			this.approver = approver;
			this.type = type;
			this.admin = admin;
			this.state = state;
			this.app_time = app_time;
			this.user_name = user_name;
			this.day = day;
			this.pic_url = pic_url;
			this.total = total;
			this.reason = reason;
			this.explain = explain;
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

		public String getReason() {
			return reason;
		}

		public void setReason(String reason) {
			this.reason = reason;
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

		public String getPic_url() {
			return pic_url;
		}

		public void setPic_url(String pic_url) {
			this.pic_url = pic_url;
		}

		public String getTotal() {
			return total;
		}

		public void setTotal(String total) {
			this.total = total;
		}

		public ArrayList<ListData> getExplain() {
			return explain;
		}

		public void setExplain(ArrayList<ListData> explain) {
			this.explain = explain;
		}

		public class ListData implements Serializable {
			/**
			 * 
			 */
			private static final long serialVersionUID = -2502425802858585818L;
			private String place;
			private String start;
			private String end;
			private String money;
			private String type;
			private String detail;

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

			public String getMoney() {
				return money;
			}

			public void setMoney(String money) {
				this.money = money;
			}

			public String getType() {
				return type;
			}

			public void setType(String type) {
				this.type = type;
			}

			public String getDetail() {
				return detail;
			}

			public void setDetail(String detail) {
				this.detail = detail;
			}

			public ListData(String place, String start, String end,
					String money, String type, String detail) {
				super();
				this.place = place;
				this.start = start;
				this.end = end;
				this.money = money;
				this.type = type;
				this.detail = detail;
			}

		}
	}
}
