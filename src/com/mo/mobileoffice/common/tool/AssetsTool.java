package com.mo.mobileoffice.common.tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;

public class AssetsTool {
	/**
	 * 读取assets
	 * 
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static String readAssets(Context context, String fileName) {
		String result = "";
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(context
					.getAssets().open(fileName)));
			String line = null;
			while ((line = reader.readLine()) != null) {
				result += line;
			}
		} catch (IOException e) {
			e.printStackTrace();
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return result;
	}
}
