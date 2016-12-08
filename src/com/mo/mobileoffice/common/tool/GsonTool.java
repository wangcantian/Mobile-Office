package com.mo.mobileoffice.common.tool;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mo.mobileoffice.common.base.CommBean;
import com.mo.mobileoffice.common.base.CommBeanList;

public class GsonTool {

	private static Gson mGson;
	private static GsonTool mGsonTool;

	private GsonTool() {
		mGson = new GsonBuilder().create();
	}

	public static GsonTool getInstance() {
		if (mGsonTool == null) {
			return new GsonTool();
		}
		return mGsonTool;
	}

	public static <T> List<T> getDatas(String datas, Type type) {
		getInstance();
		List<T> list = null;
		list = mGson.fromJson(datas, type);
		return list;
	}

	public static <T> T getData(String data, Type type) {
		getInstance();
		T obj = mGson.fromJson(data, type);
		return obj;
	}


	public static <T> T getData(String data, Class<T> cls) {
		getInstance();
		T obj = mGson.fromJson(data, cls);
		return obj;
	}

	public static <T> CommBean<T> getBaseBeanData(String data, Class<T> clazz) {
		getInstance();
		Type objectType = type(CommBean.class, clazz);
		return mGson.fromJson(data, objectType);
	}

	public static <T> CommBeanList<T> getBaseBeanListData(String data, Class<T> clazz) {
		getInstance();
		Type objectType = type(CommBeanList.class, clazz);
		return mGson.fromJson(data, objectType);
	}

	public static String toJson(Object obj) {
		getInstance();
		return mGson.toJson(obj);
	}

	public static String getContent(String jsonStr, String key) {
		try {
			JSONObject object = new JSONObject(jsonStr);
			return object.getString(key);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static int getInt(String jsonStr, String key) {
		try {
			JSONObject object = new JSONObject(jsonStr);
			return object.getInt(key);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static ParameterizedType type(final Class<?> raw, final Type... args) {
		return new ParameterizedType() {
			public Type getRawType() {
				return raw;
			}

			public Type[] getActualTypeArguments() {
				return args;
			}

			public Type getOwnerType() {
				return null;
			}
		};
	}
}
