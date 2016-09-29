package com.opower.chart;

import java.util.ArrayList;

import studio.dextest.tools.MLog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 繪製長條圖
 * 
 * @author dexter
 *
 */
public class DrawBarView extends BaseView {
	// Context context;
	// RelativeLayout drawLayout;
	Canvas canvas;
	int mGray;
	// float screenWidth;
	// float screenHeight;
	float gap;// 每個長條之間的間隙
	final int BAR_NAM = 6;// 長條圖數量
	float fatherX[];// 每個長條的X座標

	Paint barBackgroundPaint;
	Paint barPaint;
	Paint grayPaint;
	Paint linePaint;

	// 長條圖線的寬度
	float lineWidth, backgroundWidth;

	// 需要傳入的資料
	float total;
	String strBarTitle[];
	String strBarBottom[];
	float floBarBottom[];
	int leftAreaNum;// 最左邊分割區塊

	TextView tvBarTitle[];
	TextView tvBarBottom[];
	TextView tvUnit;
	TextView tvLeft[];
	BasicDrawLineSprite barBackgroundSprite[];
	Sprite grayLine;
	ArrayList<Float> lineY;

	public DrawBarView(Context context, ScreenSize screen,
			RelativeLayout drawLayout) {
		super(context, screen, drawLayout);
		init();
	}

	public void setShowBottomText(boolean show) {

		if (show) {
			for (TextView t : tvBarBottom) {
				t.setVisibility(View.VISIBLE);
			}
		} else {
			for (TextView t : tvBarBottom) {
				t.setVisibility(View.INVISIBLE);
			}
		}
	}

	public void setLeftAreaNum(int n) {
		this.leftAreaNum = n;
	}

	public void setTotal(float t) {
		this.total = t;
	}

	public void setBarTitle(String title[]) {
		this.strBarTitle = title;
	}

	public void setBarBottom(float flo[]) {
		this.floBarBottom = flo;
		strBarBottom = new String[floBarBottom.length];
		for (int i = 0; i < floBarBottom.length; i++) {
			strBarBottom[i] = String.valueOf((int) floBarBottom[i]);
		}

		for (int i = 0; i < floBarBottom.length; i++) {
			if (floBarBottom[i] > total) {
				total = floBarBottom[i];
			}
		}

		if ((int) total % 5 != 0) {
			total = (((int) total / 5) + 1) * 5;
		}

	}

	private void init() {
		mGray = Color.rgb(80, 80, 80);
		float startX = 150;// 第一個長條圖X起始座標
		float endX = 30;// 最右邊留的間隙
		gap = 16;
		backgroundWidth = (screenWidth - startX - endX - (gap * (BAR_NAM - 1)))
				/ BAR_NAM;
		// bar
		lineWidth = backgroundWidth * 0.6f;

		// gap = (screenWidth - (backgroundWidth * BAR_NAM) - startX - endX)
		// / (BAR_NAM - 1);

		fatherX = new float[BAR_NAM];
		for (int i = 0; i < fatherX.length; i++) {
			fatherX[i] = ((backgroundWidth + gap) * i) + startX;
		}

		tvBarTitle = new TextView[BAR_NAM];
		tvBarBottom = new TextView[BAR_NAM];

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		this.canvas = canvas;
		MLog.v(this, "canvas:" + canvas);
		// 畫筆
		barBackgroundPaint = getNewPaint(Color.argb(200, 51, 51, 51));
		barPaint = getNewPaint(Color.argb(255, 233, 86, 19));
		grayPaint = getNewPaint(mGray);

		// canvas.drawText("画圆角矩形:", 10, 260, paint);

		// 長條圖初始XY座標
		float fromX = 150;
		float fromY = 100;
		float toX = fromX + lineWidth;
		float toY = 300;

		// Sprite sprite = new Sprite(0, 0, 100, 100, 5, canvas, barPaint);
		// sprite.show();

		// bar background
		barBackgroundSprite = new BasicDrawLineSprite[BAR_NAM];
		for (int i = 0; i < BAR_NAM; i++) {
			barBackgroundSprite[i] = new BasicDrawLineSprite(fatherX[i], fromY,
					backgroundWidth, screenHeight * 0.3f, 0, canvas,
					barBackgroundPaint, i);
			barBackgroundSprite[i].show();
		}

		// gray line
		float lineWidth = backgroundWidth * 0.2f;
		grayLine = new Sprite(barBackgroundSprite[0].getX() - lineWidth * 1.8f,
				barBackgroundSprite[0].getY(), lineWidth,
				barBackgroundSprite[0].getHeight(), 0, canvas, grayPaint);
		grayLine.show();

		this.showLeftTextView();
		// 呼叫畫線
		// Handler handler = new Handler();
		// handler.post(new Runnable() {
		//
		// @Override
		// public void run() {
		// while (lineY.size() < tvLeft.length) {
		//
		// try {
		// Thread.sleep(50);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// }
		//
		// showLine();
		// invalidate();
		// }
		// });
		// this.showLine();
	}

	private void showLeftTextView() {
		// 左下角單位字串
		final TextView tvUnit = new TextView(context);
		tvUnit.setText(strUnit);
		tvUnit.setTextColor(Color.WHITE);
		tvUnit.setTextSize(12);
		tvUnit.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
			public void onLayoutChange(View v, int left, int top, int right,
					int bottom, int oldLeft, int oldTop, int oldRight,
					int oldBottom) {
				float textViewWidth = right - left;
				float textViewHeight = bottom - top;
				float mX = grayLine.getX() - textViewWidth
						- (grayLine.getWidth() * 0.5f);

				float mY = grayLine.getY() + grayLine.getHeight()
						- textViewHeight;
				setTextPosition(tvUnit, mX, mY);
			}
		});

		drawLayout.addView(tvUnit);

		// TODO
		// 左邊數字及橫線
		linePaint = new Paint();
		// linePaint.setStyle(Paint.Style.STROKE);// 設置非填充
		// linePaint.setStyle(Style.STROKE);//設置非填充
		// linePaint.setStrokeWidth(5);// 筆寬5圖元
		linePaint.setColor(Color.argb(255, 233, 86, 19));
		linePaint.setAntiAlias(true);// 鋸齒不顯示
		lineY = new ArrayList<Float>();

		float unitHeight = grayLine.getHeight() / leftAreaNum;
		tvLeft = new TextView[leftAreaNum - 1];
		for (int i = 0; i < tvLeft.length; i++) {
			MLog.v(this, String.valueOf(total / leftAreaNum * (i + 1)));
			tvLeft[i] = new TextView(context);
			tvLeft[i].setText(String.valueOf((int) total / leftAreaNum
					* (i + 1)));
			tvLeft[i].setTextColor(Color.WHITE);
			tvLeft[i].setTextSize(14);
			showLeftNum(tvLeft[i], unitHeight, i);
		}

	}

	private void showLine() {
		final float fromX = grayLine.getX() + grayLine.getWidth();
		final float toX = barBackgroundSprite[barBackgroundSprite.length - 1]
				.getX()
				+ barBackgroundSprite[barBackgroundSprite.length - 1]
						.getWidth();

		linePaint.setStrokeWidth(2);
		for (int i = 0; i < lineY.size(); i++) {
			canvas.drawLine(fromX, lineY.get(i), toX, lineY.get(i), linePaint);
		}
		lineY.clear();

	}

	private void showLeftNum(final TextView tv, final float unit, final int num) {
		tv.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
			public void onLayoutChange(View v, int left, int top, int right,
					int bottom, int oldLeft, int oldTop, int oldRight,
					int oldBottom) {
				float textViewWidth = right - left;
				float textViewHeight = bottom - top;

				float mX = grayLine.getX() - textViewWidth
						- (grayLine.getWidth() * 0.5f);
				float mY = grayLine.getY() + grayLine.getHeight()
						- (unit * (num + 1)) - textViewHeight;
				lineY.add(mY + (textViewHeight / 2));
				// MLog.v(this, "lineY.size: "+lineY.size());
				setTextPosition(tv, mX, mY);
				// MLog.v(this, "lineY:"+lineY);
			}
		});

		drawLayout.addView(tv);
	}

	/**
	 * 每個長條的基本元件, 含黑底及文字
	 * 
	 * @author dexter
	 *
	 */
	public class BasicDrawLineSprite extends Sprite {
		private int num;

		// Canvas mCanvas;

		public BasicDrawLineSprite(float x, float y, float width, float height,
				int radius, Canvas canvas, Paint paint, int num) {
			super(x, y, width, height, radius, canvas, paint);
			this.num = num;
			// this.mCanvas = canvas;
		}

		@Override
		public void show() {
			// TODO
			super.show();
			this.showBar();
			this.showTitle(16);
			this.showBottomText(14);

		}

		private void showBottomText(int textSize) {
			tvBarBottom[num] = new TextView(context);
			tvBarBottom[num].setText(strBarBottom[num]);
			tvBarBottom[num].setTextSize(textSize);
			tvBarBottom[num].setTextColor(mGray);

			tvBarBottom[num]
					.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
						public void onLayoutChange(View v, int left, int top,
								int right, int bottom, int oldLeft, int oldTop,
								int oldRight, int oldBottom) {
							float textViewWidth = right - left;
							float textViewHeight = bottom - top;
							float mX = BasicDrawLineSprite.this.getX()
									+ (BasicDrawLineSprite.this.getWidth() - textViewWidth)
									/ 2;
							float mY = BasicDrawLineSprite.this.getY()
									+ BasicDrawLineSprite.this.getHeight();
							setTextPosition(tvBarBottom[num], mX, mY);
						}
					});

			drawLayout.addView(tvBarBottom[num]);
		}

		private void showTitle(int textSize) {
			tvBarTitle[num] = new TextView(context);
			tvBarTitle[num].setText(strBarTitle[num]);
			tvBarTitle[num].setTextSize(textSize);
			tvBarTitle[num].setTextColor(Color.WHITE);

			tvBarTitle[num]
					.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
						public void onLayoutChange(View v, int left, int top,
								int right, int bottom, int oldLeft, int oldTop,
								int oldRight, int oldBottom) {
							float textViewWidth = right - left;
							float textViewHeight = bottom - top;
							float mX = BasicDrawLineSprite.this.getX()
									+ (BasicDrawLineSprite.this.getWidth() - textViewWidth)
									/ 2;
							float mY = BasicDrawLineSprite.this.getY()
									- textViewHeight;
							setTextPosition(tvBarTitle[num], mX, mY);
						}
					});

			drawLayout.addView(tvBarTitle[num]);
		}

		private void showBar() {
			float mX = this.getX() + ((backgroundWidth - lineWidth) / 2);
			float mY = this.getY()
					+ (this.getHeight() - (this.getHeight() * (floBarBottom[num] / total)));

			Sprite bar = new Sprite(mX, mY, lineWidth, this.getHeight()
					* floBarBottom[num] / total, 0, canvas, barPaint);
			bar.show();
		}

	}

}
