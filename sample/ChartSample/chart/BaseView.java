package com.opower.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 
 * @author dexter
 *
 */
public class BaseView extends View {
	protected Context context;
	protected RelativeLayout drawLayout;
	protected Canvas canvas;
	protected int screenWidth;
	protected int screenHeight;
	protected int mGray;
	protected String strUnit;

	public BaseView(Context context, ScreenSize screen,
			RelativeLayout drawLayout) {
		super(context);
		this.context = context;
		this.drawLayout = drawLayout;
		screenWidth = screen.getScreenWidth();
		screenHeight = screen.getScreenHeight();
		this.setMinimumHeight(480);
		this.setMinimumWidth(300);
	}
	
	public Paint getNewPaint(int color) {
		Paint p = new Paint();
		p.setStyle(Paint.Style.FILL);// 充滿
		p.setAntiAlias(true);// 鋸齒效果
		p.setColor(color);
		return p;
	}
	public void setUnit(String str) {
		this.strUnit = str;
	}
	
//	public void clearView(){
//		canvas.drawColor(Color.TRANSPARENT,PorterDuff.Mode.CLEAR);
//		invalidate();
//	}
	public class Sprite {
		float x, y;
		float width, height;
		Paint paint;
		 Canvas canvas;
		int radius;// 半徑

		public Sprite(float x, float y, float width, float height, int radius,
				Canvas canvas, Paint paint) {
			super();
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			 this.canvas = canvas;
			this.paint = paint;
			this.radius = radius;
		}

		public void show() {
			RectF oval3 = new RectF(x, y, x + width, y + height);// 设置个新的长方形,left,top,right,bottom
			canvas.drawRoundRect(oval3, radius, radius, paint);// 第二个参数是x半径，第三个参数是y半径
		}

		public float getX() {
			return this.x;
		}

		public float getY() {
			return this.y;
		}

		public float getWidth() {
			return this.width;
		}

		public float getHeight() {
			return this.height;
		}

	}
	
	public void showView() {

		drawLayout.addView(this);
	}
	public void setTextPosition(TextView tv, float pX, float pY) {
		tv.setX(pX);
		tv.setY(pY);
	}

}
