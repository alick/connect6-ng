/*
 * Copyright 2012 Shuyang Jiang, Yipeng Ma and Bo Liu
 * 
 * This file is part of Connect6.

   Connect6 is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   Connect6 is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with Connect6.  If not, see <http://www.gnu.org/licenses/>.
 */

package cn.edu.tsinghua.se2012.connect6;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * 实现游戏软件进入时的欢迎界面
 * 
 * @version 1.0
 * @author Shuyang Jiang, Yipeng Ma and Bo Liu
 *
 */

public class WelcomeView extends SurfaceView implements SurfaceHolder.Callback {
	/* 实现生命周期回调接口 */
	/** 主菜单界面 */
	StartActivity activity;
	/** 画笔对象，包含我们要花的图形的颜色和类型 */
	Paint paint;
	/** 当前的透明度  */
	int currentAlpha = 0; 
	/** 界面宽度  */
	int screenWidth = (int) StartActivity.screenWidth;
	/** 界面高度  */
	int screenHeight = (int) StartActivity.screenHeight;
	/** 欢迎界面图片相邻透明度之间的切换时间间隔  */
	int sleepSpan = 50;
	/** 欢迎界面图片  */
	int pic;
	/** 当前logo图片引用  */
	Bitmap logo; 

	/**
	 * 构造函数，进行相关初始化的工作
	 */
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

	/**
	 * 绘制欢迎界面图片
	 */
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

	public void surfaceCreated(SurfaceHolder arg0) {
		/* 创建时被调用  */
		new Thread() {
			@SuppressLint("WrongCall") 
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
