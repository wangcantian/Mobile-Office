package com.mo.mobileoffice.function.meeting.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.mo.mobileoffice.common.base.BaseDao;
import com.mo.mobileoffice.function.meeting.bean.RoomBean;

public class RoomDao extends BaseDao<RoomBean> {
	
	public static final String TABLE_NAME = "room";
	
	public static final String KEY_ID = "id";
	public static final String KEY_FLOOR_ID = "floor_id";
	public static final String KEY_ROOM_NUM = "room_num";
	public static final String KEY_WIFI = "wifi";
	public static final String KEY_PROJECTOR = "projector";
	public static final String KEY_AIR_CON = "air_con";
	public static final String KEY_SEAT = "seat";
	
	public static final String TABLE_CREATE	 = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_NAME + " (" + KEY_ID + " integer primary key, " 
			+ KEY_FLOOR_ID + " integer, " 
			+ KEY_ROOM_NUM + " varchar(255), "
			+ KEY_WIFI + " integer, "
			+ KEY_PROJECTOR + " integer, "
			+ KEY_AIR_CON + " integer, "
			+ KEY_SEAT + " integer "
			+ ")";

	public RoomDao(Context context) {
		super(context);
	}

	@Override
	protected String getTableName() {
		return TABLE_NAME;
	}

	@Override
	protected ContentValues getContentValuesByData(RoomBean data) {
		if (data != null) {
			ContentValues values = new ContentValues();
			values.put(KEY_ID, data.getId());
			values.put(KEY_FLOOR_ID, data.getFloor_id());
			values.put(KEY_ROOM_NUM, data.getRoom_num());
			values.put(KEY_WIFI, data.getWifi());
			values.put(KEY_PROJECTOR, data.getProjector());
			values.put(KEY_AIR_CON, data.getAir_con());
			values.put(KEY_SEAT, data.getSeat());
			return values;
		}
		return null;
	}

	@Override
	protected RoomBean getDataByCursor(Cursor cursor) {
		if (cursor != null) {
			RoomBean bean = new RoomBean();
			bean.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
			bean.setFloor_id(cursor.getInt(cursor.getColumnIndex(KEY_FLOOR_ID)));
			bean.setRoom_num(cursor.getString(cursor.getColumnIndex(KEY_ROOM_NUM)));
			bean.setProjector(cursor.getInt(cursor.getColumnIndex(KEY_PROJECTOR)));
			bean.setWifi(cursor.getInt(cursor.getColumnIndex(KEY_WIFI)));
			bean.setAir_con(cursor.getInt(cursor.getColumnIndex(KEY_AIR_CON)));
			bean.setSeat(cursor.getInt(cursor.getColumnIndex(KEY_SEAT)));
			return bean;
		}
		return null;
	}

	@Override
	protected String getIDColumnName() {
		return KEY_ID;
	}
	
	/** 根据楼id获得房间数据集合 **/
	public List<RoomBean> getRoomBeanByFloorId(String floor_id) {
		Cursor cursor = mDBHelper.getReadableDatabase().query(getTableName(), null, 
				KEY_FLOOR_ID + "=?", new String[]{floor_id}, null, null, null);
		if (cursor == null) {
			return null;
		}
		List<RoomBean> lists = new ArrayList<RoomBean>();
		while (cursor.moveToNext()) {
			lists.add(getDataByCursor(cursor));
		}
		cursor.close();
		return lists;
	}
}
