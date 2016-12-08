package com.mo.mobileoffice.common.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CricleImageView extends ImageView {

	public CricleImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public CricleImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CricleImageView(Context context) {
		super(context);
	}
	
	@Override
	public void setImageBitmap(Bitmap bm) {
		super.setImageBitmap(toRoundBitmap(bm));
	}
	
	private Bitmap toRoundBitmap(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		
		int r = 0;
		if (width >= height)
			r = height;
		else
			r = width;
		
		Bitmap backgroundBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(backgroundBitmap);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		RectF rect = new RectF(0, 0, r, r);
		canvas.drawRoundRect(rect, r / 2, r / 2, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, null, rect, paint);
		
		return backgroundBitmap;
	}
}
