package studio.dextest.dextest;

import com.opower.chart.DrawBarView;
import com.opower.chart.DrawLineView;
import com.opower.chart.ScreenSize;

import studio.dextest.tools.MLog;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

public class SampleActivity extends Activity {
	RelativeLayout drawBarLayout, drawLineLayout;
	DrawBarView drawBarView;
	DrawLineView drawLineView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

	}

	@Override
	protected void onResume() {
		super.onResume();
		drawBarLayout = (RelativeLayout) findViewById(R.id.drawBarLayout);
		drawLineLayout = (RelativeLayout) findViewById(R.id.drawLineLayout);
		drawLineLayout.setVisibility(View.INVISIBLE);
		showBarView();
		showLineView();

		// showBarView();
	}

	private void showBarView() {
		drawBarView = new DrawBarView(this, new ScreenSize(this), drawBarLayout);
		// 最左邊滿數值
//		drawBarView.setTotal(16);
		// 最上方字串
		drawBarView.setBarTitle(new String[] { "JAN", "FEB", "MAR", "APR",
				"MAY", "JUN" });
		// 最底下X軸數值
		drawBarView
				.setBarBottom(new float[] { 7.5f, 4.6f, 1.3f, 10.2f, 19f, 0f });
		// 最左邊要分割幾個區塊
		drawBarView.setLeftAreaNum(5);
		// 左下角單位字串
		drawBarView.setUnit("km");

		drawBarView.showView();
	}

	private void showLineView() {
		drawLineView = new DrawLineView(this, new ScreenSize(this),
				drawLineLayout);
		// 底下單位文字
		drawLineView.setUnit("min");
		// 底下數字文字
		drawLineView.setBottomValue(new float[] { 37, 29, 187, 26,98 });
		// 設定左邊最高數值
//		drawLineView.setTotalValue(147);

		drawLineView.showView();
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnBarView:
			drawBarLayout.setVisibility(View.VISIBLE);
			drawLineLayout.setVisibility(View.INVISIBLE);
			break;
		case R.id.btnLineView:
			drawBarLayout.setVisibility(View.INVISIBLE);
			drawLineLayout.setVisibility(View.VISIBLE);
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
