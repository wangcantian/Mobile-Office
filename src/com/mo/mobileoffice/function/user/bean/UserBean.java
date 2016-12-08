package com.mo.mobileoffice.function.user.bean;

public class UserBean {
	private String role_name;
	private String level;
	private String id;
	private String name;
	private String email;
	private String mobile;
	private String sex;
	private String birthday;
	private String token;
	private String pic_url;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPic_url() {
		return pic_url;
	}

	public void setPic_url(String pic_url) {
		this.pic_url = pic_url;
	}

	@Override
	public String toString() {
		return "UserInfo [role_name=" + role_name + ", level=" + level
				+ ", id=" + id + ", name=" + name + ", email=" + email
				+ ", mobile=" + mobile + ", sex=" + sex + ", birthday="
				+ birthday + ", token=" + token + ", pic_url=" + pic_url + "]";
	}

}
