package com.mo.mobileoffice.function.announce.bean;

import java.io.Serializable;

public class AnnounceBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private String title;
	private String content;
	private String create_time;
	private int state;
	private String user_name;
	private String pic_url;
	private String ann_pic_url;

	public String getAnn_pic_url() {
		return ann_pic_url;
	}

	public void setAnn_pic_url(String ann_pic_url) {
		this.ann_pic_url = ann_pic_url;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getPic_url() {
		return pic_url;
	}

	public void setPic_url(String pic_url) {
		this.pic_url = pic_url;
	}

	@Override
	public String toString() {
		return "AnnounceBean [id=" + id + ", title=" + title + ", content="
				+ content + ", create_time=" + create_time + ", state=" + state
				+ ", user_name=" + user_name + ", pic_url=" + pic_url
				+ ", ann_pic_url=" + ann_pic_url + "]";
	}

}
