package com.mo.mobileoffice.common.widget;

import com.mo.mobileoffice.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

public class RoundProgressBar extends View {
	public static final int STROKE = 0;
	public static final int FILL = 1;

	private Paint paint;
	private int roundColor;
	private int roundProgressColor;
	private int textColor;
	private float textSize;
	private float roundWidth;
	private int max;
	private int progress;
	private boolean textIsDiaplayable;
	private int style;

	public RoundProgressBar(Context context) {
		super(context);
	}

	public RoundProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public RoundProgressBar(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}
	
	private void init(Context context, AttributeSet attrs) {
		paint = new Paint();
		
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressBar);
		
		roundColor = array.getColor(R.styleable.RoundProgressBar_roundColor,
				Color.RED);
		roundProgressColor = array.getColor(
				R.styleable.RoundProgressBar_roundProgressColor, Color.GREEN);
		textColor = array.getColor(R.styleable.RoundProgressBar_textColor,
				Color.GREEN);
		textSize = array
				.getDimension(R.styleable.RoundProgressBar_textSize, 15);
		roundWidth = array.getDimension(
				R.styleable.RoundProgressBar_roundWidth, 5);
		max = array.getInteger(R.styleable.RoundProgressBar_max, 100);
		textIsDiaplayable = array.getBoolean(
				R.styleable.RoundProgressBar_textIsDisplayable, true);
		style = array.getInt(R.styleable.RoundProgressBar_style, 0);
		
		array.recycle();
	}

	@SuppressLint("DrawAllocation") @Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// 画最外层的大园环
		int center = getWidth() / 2;
		int radius = (int) (center - roundWidth / 2);
		paint.setColor(roundColor);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(roundWidth);
		paint.setAntiAlias(true);
		canvas.drawCircle(center, center, radius, paint);

		// 画进度百分比
		paint.setStrokeWidth(0);   
        paint.setColor(textColor);  
        paint.setTextSize(textSize);
        paint.setTypeface(Typeface.DEFAULT_BOLD); //设置字体  
        int percent = (int)(((float)progress / (float)max) * 100);  //中间的进度百分比，先转换成float在进行除法运算，不然都为0  
        float textWidth = paint.measureText(percent + "%");   //测量字体宽度，我们需要根据字体的宽度设置在圆环中间  
          
        if(textIsDiaplayable && percent != 0 && style == STROKE){
            canvas.drawText(percent + "%", center - textWidth / 2, center + textSize/2, paint); //画出进度百分比  
        }
        
        //画圆弧 ，画圆环的进度 
        paint.setStrokeWidth(roundWidth); //设置圆环的宽度  
        paint.setColor(roundProgressColor);  //设置进度的颜色  
        RectF oval = new RectF(center - radius, center - radius, center + radius, center + radius);  //用于定义的圆弧的形状和大小的界限  
          
        switch (style) {
	        case STROKE:
	            paint.setStyle(Paint.Style.STROKE);  
	            canvas.drawArc(oval, -90, 360 * progress / max, false, paint);  //根据进度画圆弧  
	            break;  
	        case FILL:
	            paint.setStyle(Paint.Style.FILL_AND_STROKE);  
	            if (progress != 0) {
	                canvas.drawArc(oval, -90, 360 * progress / max, true, paint);  //根据进度画圆弧  
	            }
	            break;
        }  
	}

	public synchronized int getMax() {
		return max;
	}

	public synchronized void setMax(int max) {
		if (max < 0) {
			throw new IllegalArgumentException("max not less than 0");
		}
		this.max = max;
	}

	public synchronized int getProgress() {
		return progress;
	}

	public synchronized void setProgress(int progress) {
		if (progress < 0) {
			throw new IllegalArgumentException("progress not less than 0");
		}
		if (progress > max) {
			progress = max;
		}
		if (progress <= max) {
			this.progress = progress;
			postInvalidate();
		}
	}

	public int getCricleColor() {
		return roundColor;
	}

	public void setCricleColor(int cricleColor) {
		this.roundColor = cricleColor;
	}

	public int getCricleProgressColor() {
		return roundProgressColor;
	}

	public void setCricleProgressColor(int cricleProgressColor) {
		this.roundProgressColor = cricleProgressColor;
	}

	public int getTextColor() {
		return textColor;
	}

	public void setTextColor(int textColor) {
		this.textColor = textColor;
	}

	public float getTextSize() {
		return textSize;
	}

	public void setTextSize(float textSize) {
		this.textSize = textSize;
	}

	public float getRoundWidth() {
		return roundWidth;
	}

	public void setRoundWidth(float roundWidth) {
		this.roundWidth = roundWidth;
	}
}
