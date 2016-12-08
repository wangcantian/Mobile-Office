package com.mo.mobileoffice.function.meeting.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.mo.mobileoffice.common.base.BaseDao;
import com.mo.mobileoffice.function.meeting.bean.FloorBean;

public class FloorDao extends BaseDao<FloorBean> {
	
	public static final String TABLE_NAME = "floor";
	
	public static final String KEY_ID = "id";
	public static final String KEY_FLOOR_NUM = "floor_num";
	public static final String KEY_FLOOR_NAME = "floor_name";
	
	public static final String TABLE_CREATE	 = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_NAME + " (" + KEY_ID + " integer primary key, " 
			+ KEY_FLOOR_NUM + " integer, " 
			+ KEY_FLOOR_NAME + " varchar(255)"
			+ ")";

	public FloorDao(Context context) {
		super(context);
	}

	@Override
	protected String getTableName() {
		return TABLE_NAME;
	}

	@Override
	protected ContentValues getContentValuesByData(FloorBean data) {
		if (data != null) {
			ContentValues values = new ContentValues();
			values.put(KEY_ID, data.getId());
			values.put(KEY_FLOOR_NUM, data.getFloor_num());
			values.put(KEY_FLOOR_NAME, data.getFloor_name());
			return values;
		}
		return null;
	}

	@Override
	protected FloorBean getDataByCursor(Cursor cursor) {
		if (cursor != null) {
			FloorBean bean = new FloorBean();
			bean.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
			bean.setFloor_num(cursor.getInt(cursor.getColumnIndex(KEY_FLOOR_NUM)));
			bean.setFloor_name(cursor.getString(cursor.getColumnIndex(KEY_FLOOR_NAME)));
			return bean;
		}
		return null;
	}

	@Override
	protected String getIDColumnName() {
		return KEY_ID;
	}

}
