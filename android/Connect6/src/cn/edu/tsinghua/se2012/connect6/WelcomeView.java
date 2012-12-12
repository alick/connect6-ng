package cn.edu.tsinghua.se2012.connect6;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

//开始欢迎界面
public class WelcomeView extends SurfaceView implements SurfaceHolder.Callback {// 实现生命周期回调接口
	StartActivity activity;
	Paint paint;
	int currentAlpha = 0; // 当前的透明度
	int screenWidth = (int) StartActivity.screenWidth;
	int screenHeight = (int) StartActivity.screenHeight;
	int sleepSpan = 50;
	int pic;
	Bitmap logo; // 当前logo图片引用

	public WelcomeView(StartActivity activity) {
		super(activity);
		this.activity = activity;
		this.getHolder().addCallback(this);// 设置生命周期回调接口的实现者
		paint = new Paint(); // 创建画笔
		paint.setAntiAlias(true); // 打开抗锯齿
		pic = R.drawable.welcome;
		Bitmap temp = BitmapFactory.decodeResource(activity.getResources(), pic);
		logo = Bitmap.createScaledBitmap(temp, screenWidth, screenHeight, true);
	}

	@Override
	public void onDraw(Canvas canvas) {
		try {
			// 绘制黑色填充矩形清背景
			paint.setColor(Color.BLACK);
			paint.setAlpha(255);
			canvas.drawRect(0, 0, screenWidth, screenHeight, paint);

			// 进行平面贴图
			if (logo == null)
				return;
			paint.setAlpha(currentAlpha);
			canvas.drawBitmap(logo, 0, 0, paint);
			if (logo.isRecycled()){
				logo.recycle();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
	}

	public void surfaceCreated(SurfaceHolder arg0) { // 创建时被调用
		new Thread() {
			public void run() {				
				for (int i = 255; i > -10; i = i - 10) // 动态更改图片的透明度值并不断重绘
				{
					currentAlpha = i;
					if (currentAlpha < 0) {
						currentAlpha = 0;
					}
					SurfaceHolder myholder = WelcomeView.this.getHolder();
					Canvas canvas = myholder.lockCanvas();// 获取画布
					try {
						synchronized (myholder) {
							onDraw(canvas); // 绘制
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						if (canvas != null) {
							myholder.unlockCanvasAndPost(canvas);
						}
					}

					try {
						if (i == 255) {
							Thread.sleep(1000);
						}
						Thread.sleep(sleepSpan);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				activity.hd.sendEmptyMessage(0);
			}
		}.start();
	}

	public void surfaceDestroyed(SurfaceHolder arg0) { // 销毁时被调用
	}
}
