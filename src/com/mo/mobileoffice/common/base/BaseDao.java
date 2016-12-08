package com.mo.mobileoffice.common.base;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.mo.mobileoffice.common.helper.DBHelper;

public abstract class BaseDao<T> {

	protected Context mContext;
	protected DBHelper mDBHelper;
	
	public BaseDao(Context context) {
		this.mContext = context;
		mDBHelper = new DBHelper(context);
	}
	
	/** 获取表id **/
	protected abstract String getIDColumnName();
	
	/** 获取表名 **/
	protected abstract String getTableName();
	
	/** 将Object转成ContentValues **/
	protected abstract ContentValues getContentValuesByData(T data);
	
	/** 从游标中解析出对象 **/
	protected abstract T getDataByCursor(Cursor cursor);
	
	/** 添加一行数据 **/
	public boolean insertData(T data) {
		if (data != null) {
			return mDBHelper.getWritableDatabase().insert(getTableName(), null, getContentValuesByData(data)) > 0;
		}
		return false;
	}
	
	/** 添加多行数据 **/
	public boolean insertDatas(List<T> datas) {
		if (datas != null) {
			for (T data : datas) {
				if (!insertData(data))
					return false;
			}
			return true;
		}
		return false;
	}
	
	/** 根据条件更新行 **/
	public boolean updateData(T data, String whereClause, String whereArgs) {
		boolean ret = false;
		if (data != null) {
			ContentValues values = getContentValuesByData(data);
			if (mDBHelper.getWritableDatabase().update(getTableName(), values,
					whereClause == null ? null : whereClause + "=?",
					new String[] { whereArgs }) > 0) {
				ret = true;
			} else {
				ret = false;
			}
		}
		return ret;
	}
	
	/** 根据id更新行 **/
	public boolean updateDataById(T data, String id) {
		return updateData(data, getIDColumnName(), id);
	}
	
	/** 删除所有行 **/
	public boolean deleteAllDatas() {
		return mDBHelper.getWritableDatabase().delete(getTableName(), null, null) > 0;
	}
	
	/** 删除指定id的行 **/
	public boolean deleteDataById(String id) {
		return mDBHelper.getWritableDatabase().delete(getTableName(), getIDColumnName() + "=?", new String[]{id}) > 0;
	}
	
	/** 读取所有行并返回数据集合 **/
	public List<T> selectAllDatas() {
		List<T> lists = new ArrayList<T>();
		Cursor cursor = loadAllDatas();
		while (cursor.moveToNext()) {
			T bean = getDataByCursor(cursor);
			lists.add(bean);
		}
		cursor.close();
		return lists;
	}
	
	/** 通过id读取行 **/
	public T selectDataById(String id) {
		T bean = null;
		Cursor cursor = mDBHelper.getReadableDatabase().query(getTableName(), null, 
				getIDColumnName() + "=?", new String[]{id}, null, null, null);
		if (cursor.moveToNext()) {
			bean = getDataByCursor(cursor);
		}
		cursor.close();
		return bean;
	}
	
	/** 读取所有行 **/
	public Cursor loadAllDatas() {
		return mDBHelper.getReadableDatabase().query(getTableName(), null, null, null, null, null, null);
	}
	
	/** 是否有行数据 **/
	public boolean isHasData() {
		Cursor cursor = loadAllDatas();
		boolean ret = cursor.getCount() > 0;
		cursor.close();
		return ret;
	}
	
	/** 关闭所有数据库对象 **/
	public void close() {
		mDBHelper.close();
	}
}
