package com.mo.mobileoffice.function.announce.bean;

public class SendAnno_Request {

	public String title;
	public String content;
	public String user_id;
	public String token;

	public SendAnno_Request(String title, String content, String user_id,
			String token) {
		super();
		this.title = title;
		this.content = content;
		this.user_id = user_id;
		this.token = token;
	}

}
