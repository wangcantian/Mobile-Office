package com.mo.mobileoffice.function.approval.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class HistoryApproval_Respond {
	private String flag;
	private String msg;
	private ArrayList<HistoryData> data;

	public HistoryApproval_Respond(String flag, String msg,
			ArrayList<HistoryData> data) {
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

	public ArrayList<HistoryData> getData() {
		return data;
	}

	public void setData(ArrayList<HistoryData> data) {
		this.data = data;
	}

	public class HistoryData implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 154487642638328341L;
		private String id;
		private String app_type;
		private String approver;
		private String state;
		private String app_time;
		private String approval_time;
		private String user_name;
		private String type;
		private String start;
		private String end;
		private String day;
		private String reason;
		private String view;
		private String pic_url;
		private String total;
		private ArrayList<HistoryExplain> explain;

		public HistoryData(String id, String app_type, String approver,
				String state, String app_time, String approval_time,
				String user_name, String type, String start, String end,
				String day, String reason, String view, String pic_url,
				String total, ArrayList<HistoryExplain> explain) {
			super();
			this.id = id;
			this.approver = approver;
			this.state = state;
			this.app_time = app_time;
			this.approval_time = approval_time;
			this.user_name = user_name;
			this.type = type;
			this.start = start;
			this.end = end;
			this.app_type=app_type;
			this.day = day;
			this.reason = reason;
			this.view = view;
			this.pic_url = pic_url;
			this.total = total;
			this.explain = explain;
		}

		
		
		public String getApp_type() {
			return app_type;
		}



		public void setApp_type(String app_type) {
			this.app_type = app_type;
		}



		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}


		public String getApprover() {
			return approver;
		}

		public void setApprover(String approver) {
			this.approver = approver;
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

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
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

		public String getView() {
			return view;
		}

		public void setView(String view) {
			this.view = view;
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

		public ArrayList<HistoryExplain> getExplain() {
			return explain;
		}

		public void setExplain(ArrayList<HistoryExplain> explain) {
			this.explain = explain;
		}

		public class HistoryExplain implements Serializable {
			/**
			 * 
			 */
			private static final long serialVersionUID = -4498252148328548209L;
			private String money;
			private String type;
			private String detail;
			private String place;
			private String start;
			private String end;
			

			public HistoryExplain(String money, String type, String detail,
					String place, String start, String end) {
				super();
				this.money = money;
				this.type = type;
				this.detail = detail;
				this.place = place;
				this.start = start;
				this.end = end;
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

		}
	}

}
