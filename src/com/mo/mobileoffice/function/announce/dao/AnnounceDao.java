package com.mo.mobileoffice.function.announce.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mo.mobileoffice.common.base.BaseDao;
import com.mo.mobileoffice.function.announce.bean.AnnounceBean;

/** 公告Dao **/
public class AnnounceDao extends BaseDao<AnnounceBean> {
	
	public static final String TABLE_NAME = "announce";
	
	public static final String KEY_ID = "id";
	public static final String KEY_TITLE = "title";
	public static final String KEY_CONTENT = "content";
	public static final String KEY_CREATE_TIME = "create_time";
	public static final String KEY_STATE = "state";
	public static final String KEY_USER_NAME = "user_name";
	public static final String KEY_PIC_URL = "pic_url";
	
	public static final String TABLE_CREATE	 = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_NAME + " (" + KEY_ID + " integer primary key, " 
			+ KEY_TITLE + " varchar(255), " 
			+ KEY_CONTENT + " varchar(2048), "
			+ KEY_CREATE_TIME + " varchar(20), "
			+ KEY_STATE + " int, "
			+ KEY_USER_NAME + " varchar(20), "
			+ KEY_PIC_URL + " varchar(64) "
			+ ");";

	public AnnounceDao(Context context) {
		super(context);
	}

	@Override
	protected String getTableName() {
		return TABLE_NAME;
	}

	@Override
	protected ContentValues getContentValuesByData(AnnounceBean data) {
		if (data != null) {
			ContentValues values = new ContentValues();
			values.put(KEY_ID, data.getId());
			values.put(KEY_TITLE, data.getTitle());
			values.put(KEY_CONTENT, data.getContent());
			values.put(KEY_CREATE_TIME, data.getCreate_time());
			values.put(KEY_STATE, data.getState());
			values.put(KEY_USER_NAME, data.getUser_name());
			values.put(KEY_PIC_URL, data.getPic_url());
			return values;
		}
		return null;
	}

	@Override
	protected AnnounceBean getDataByCursor(Cursor cursor) {
		if (cursor != null) {
			AnnounceBean anno = new AnnounceBean();
			anno.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
			anno.setTitle(cursor.getString(cursor.getColumnIndex(KEY_TITLE)));
			anno.setContent(cursor.getString(cursor.getColumnIndex(KEY_CONTENT)));
			anno.setCreate_time(cursor.getString(cursor.getColumnIndex(KEY_CREATE_TIME)));
			anno.setState(cursor.getInt(cursor.getColumnIndex(KEY_STATE)));
			anno.setUser_name(cursor.getString(cursor.getColumnIndex(KEY_USER_NAME)));
			anno.setPic_url(cursor.getString(cursor.getColumnIndex(KEY_PIC_URL)));
			return anno;
		}
		return null;
	}

	/** 最新公告的id，放回-1表示没有 **/
	public int getAnnounceIdOfNew() {
		int ret = -1;
		SQLiteDatabase database = mDBHelper.getReadableDatabase();
		String sql = "select " + KEY_ID + " from " + getTableName() + " order by " + KEY_ID + " desc";
		Cursor cursor = database.rawQuery(sql, null);
		if (cursor.moveToFirst()) {
			ret = cursor.getInt(cursor.getColumnIndex(KEY_ID));
		}
		return ret;
	}
	
	/** 根据公共id标记为已读 **/
	public void updateAnnoStateToRead(String anno_id) {
		SQLiteDatabase database = mDBHelper.getReadableDatabase();
		String sql = "update " + getTableName() + " set " + KEY_STATE + "=1 where " 
				+ KEY_ID + "=" + anno_id;
		database.execSQL(sql);
	}

	@Override
	protected String getIDColumnName() {
		return KEY_ID;
	}

}
