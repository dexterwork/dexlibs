package com.opower.chart;

import studio.dextest.tools.MLog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 繪製折線圖
 * 
 * @author dexter
 *
 */
public class DrawLineView extends BaseView {
	Paint whitePaint, greenPaint, orangePaint, redPaint, grayPaint,
			darkGrayPaint;
	// 圖的高度及寬度
	float viewHeight, viewWidth;
	// 圖的起始座標
	float startX, startY;
	Sprite boxLeft, boxBottom, colorSprite[];
	int areaNum, bottomAreaNum;// 區分幾塊

	float bottomValue[];
	TextView tvBottom[], tvLeft[];

	TextView tvUnit;

	float total;
	float lineX[];

	public DrawLineView(Context context, ScreenSize screen,
			RelativeLayout drawLayout) {
		super(context, screen, drawLayout);
		init();
	}

	private void init() {
		setPaint();
		viewHeight = screenHeight * 0.3f;
		viewWidth = screenWidth * 0.8f;
		startX = (screenWidth - viewWidth) / 2;
		startY = 30;
		areaNum = 4;
		bottomAreaNum = 6;
		lineX = new float[bottomAreaNum];
	}

	public void setBottomValue(float value[]) {
		bottomValue = new float[bottomAreaNum];
		for (int i = 0; i < value.length; i++) {
			bottomValue[i] = value[i];
		}

		for (int i = 0; i < bottomValue.length; i++) {
			if (bottomValue[i] > total) {
				total = bottomValue[i];
				MLog.v(this, "total: "+total);
			}
		}
		if ((int)total % 5 != 0) {
			total = ((total / 5) + 1) * 5;
		}
	}

	private void setPaint() {
		whitePaint = getNewPaint(Color.WHITE);
		greenPaint = getNewPaint(Color.rgb(140, 198, 63));
		orangePaint = getNewPaint(Color.rgb(251, 176, 59));
		redPaint = getNewPaint(Color.rgb(255, 29, 37));
		grayPaint = getNewPaint(Color.rgb(179, 179, 179));
		darkGrayPaint = getNewPaint(Color.rgb(102, 102, 102));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		this.canvas = canvas;
		// 繪製 X,Y左及下的深灰色邊框
		drawBox();
		// 繪製中間四個顏色
		drawColor();
		// 顯示底下單位文字
		setUnitText();
		// 顯示底下數字文字
		setBottomText();
		// 顯示左邊數字文字
		setLeftText();
		// 繪製折線
		setLineChart();
	}

	private void setLineChart() {
		// TODO Auto-generated method stub
		float unit = boxBottom.getWidth() / bottomAreaNum;
		float mX[] = new float[bottomValue.length];

		for (int i = 0; i < mX.length; i++) {
			mX[i] = colorSprite[0].getX() + 10 + (unit * i) + 10;
		}

		float totalHeight = colorSprite[0].getHeight() * colorSprite.length;
		float mY[] = new float[bottomValue.length];
		for (int i = 0; i < mY.length; i++) {
			mY[i] = boxBottom.getY() - (bottomValue[i] / total * totalHeight);
			MLog.v(this, "mY:" + mY[i]);
		}
		whitePaint.setStrokeWidth(8);
		for (int i = 0; i < bottomValue.length - 1; i++) {
			canvas.drawLine(mX[i], mY[i], mX[i + 1], mY[i + 1], whitePaint);
		}

		// Handler handler = new Handler();
		// handler.post(new Runnable() {
		//
		// @Override
		// public void run() {
		// boolean flag = true;
		// while (flag) {
		// if (lineX[lineX.length - 1] == 0) {
		// try {
		// Thread.sleep(50);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		//
		// } else {
		// flag = false;
		// }
		//
		// }// while
		//
		// showLine();
		// }
		// });

	}

	private void showLine() {
		float totalHeight = colorSprite[0].getHeight() * colorSprite.length;
		float mY[] = new float[bottomValue.length];
		for (int i = 0; i < mY.length; i++) {
			mY[i] = boxBottom.getY() - (bottomValue[i] / total * totalHeight);
			MLog.v(this, "mY:" + mY[i]);
		}
		whitePaint.setStrokeWidth(8);
		for (int i = 0; i < bottomValue.length - 1; i++) {
			canvas.drawLine(lineX[i], mY[i], lineX[i + 1], mY[i + 1],
					whitePaint);
		}
	}

	private void setLeftText() {
		tvLeft = new TextView[colorSprite.length];

		for (int i = 0; i < tvLeft.length; i++) {
			tvLeft[i] = new TextView(context);
			tvLeft[i].setText(String.valueOf((int)total / areaNum * (i + 1)));
			tvLeft[i].setTextColor(Color.WHITE);
			tvLeft[i].setTextSize(12);
			setLeftTextView(tvLeft[i], i);
			drawLayout.addView(tvLeft[i]);
		}
	}

	private void setLeftTextView(final TextView tv, final int i) {
		tv.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
			public void onLayoutChange(View v, int left, int top, int right,
					int bottom, int oldLeft, int oldTop, int oldRight,
					int oldBottom) {
				float textViewWidth = right - left;
				// float textViewHeight = bottom - top;
				float mX = boxLeft.getX() - textViewWidth - 5;
				setTextPosition(tv, mX, colorSprite[i].getY());
			}
		});
	}

	private void setBottomText() {
		float mY = boxBottom.getY() + boxBottom.getHeight() + 5;
		float mX[] = new float[bottomAreaNum];
		float unit = boxBottom.getWidth() / bottomAreaNum;
		for (int i = 0; i < mX.length; i++) {
			mX[i] = colorSprite[0].getX() + 10 + (unit * i);
		}

		tvBottom = new TextView[bottomAreaNum];
		for (int i = 0; i < tvBottom.length; i++) {
			tvBottom[i] = new TextView(context);
			tvBottom[i].setText(String.valueOf((int)bottomValue[i]));
			tvBottom[i].setTextColor(Color.WHITE);
			tvBottom[i].setTextSize(12);
			setTextView(tvBottom[i], mX[i], mY, i);
			drawLayout.addView(tvBottom[i]);
		}
	}

	private void setTextView(final TextView tv, final float mX, final float mY,
			final int i) {
		tv.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
			public void onLayoutChange(View v, int left, int top, int right,
					int bottom, int oldLeft, int oldTop, int oldRight,
					int oldBottom) {
				float textViewWidth = right - left;
				// float textViewHeight = bottom - top;
				lineX[i] = (mX + textViewWidth) / 2;
				setTextPosition(tv, mX, mY);
			}
		});
	}

	private void setUnitText() {
		tvUnit = new TextView(context);
		tvUnit.setText(strUnit);
		tvUnit.setTextColor(Color.WHITE);
		tvUnit.setTextSize(12);
		tvUnit.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
			public void onLayoutChange(View v, int left, int top, int right,
					int bottom, int oldLeft, int oldTop, int oldRight,
					int oldBottom) {
				// float textViewWidth = right - left;
				// float textViewHeight = bottom - top;
				float mX = colorSprite[0].getX() + colorSprite[0].getWidth();
				float mY = boxBottom.getY() + boxBottom.getHeight() + 5;
				setTextPosition(tvUnit, mX, mY);
			}
		});

		drawLayout.addView(tvUnit);
	}

	private void drawColor() {
		float totalHeight = viewHeight - 30;
		float mX = startX + boxLeft.getWidth();
		float spriteWidth = viewWidth - boxLeft.getWidth();
		float unitHeight = totalHeight / areaNum;
		colorSprite = new Sprite[areaNum];
		float mY[] = new float[areaNum];
		for (int i = 0; i < mY.length; i++) {
			mY[i] = startY + viewHeight - boxBottom.getHeight()
					- (unitHeight * (i + 1));
		}
		colorSprite[0] = new Sprite(mX, mY[0], spriteWidth, unitHeight, 0,
				canvas, grayPaint);
		colorSprite[1] = new Sprite(mX, mY[1], spriteWidth, unitHeight, 0,
				canvas, greenPaint);
		colorSprite[2] = new Sprite(mX, mY[2], spriteWidth, unitHeight, 0,
				canvas, orangePaint);
		colorSprite[3] = new Sprite(mX, mY[3], spriteWidth, unitHeight, 0,
				canvas, redPaint);

		for (Sprite s : colorSprite) {
			s.show();
		}
	}

	private void drawBox() {
		boxLeft = new Sprite(startX, startY, 12, viewHeight, 0, canvas,
				darkGrayPaint);
		boxLeft.show();
		boxBottom = new Sprite(startX, boxLeft.getY() + boxLeft.getHeight()
				- 15, viewWidth, 15, 0, canvas, darkGrayPaint);
		boxBottom.show();
	}
}
