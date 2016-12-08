package com.mo.mobileoffice.common.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mo.mobileoffice.function.meeting.dao.FloorDao;
import com.mo.mobileoffice.function.meeting.dao.RoomDao;

public class DBHelper extends SQLiteOpenHelper {
	private static final String DB_NAME = "mobileoffice.db";
	private static final int VERSION = 1;

	public DBHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
	}

	public DBHelper(Context context, int version) {
		super(context, DB_NAME, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(FloorDao.TABLE_CREATE);
		db.execSQL(RoomDao.TABLE_CREATE);
		
		String create = "create table baoxiao(name varchar(50))";
		String[] s = new String[5];
		s[0] = "insert into baoxiao(name) values('出差广州到北京')";
		s[1] = "insert into baoxiao(name) values('去上海跟进项目')";
		s[2] = "insert into baoxiao(name) values('去广州参观学校')";
		s[3] = "insert into baoxiao(name) values('去广州参考')";
		s[4] = "insert into baoxiao(name) values('到深圳参考')";
		
		db.execSQL(create);
		for (int i = 0; i < s.length; i++) {
			db.execSQL(s[i]);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(FloorDao.TABLE_CREATE);
		db.execSQL(RoomDao.TABLE_CREATE);
	}

}
