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

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;

/**
 * 主菜单界面
 * 
 * @version 1.0
 * @author Shuyang Jiang, Yipeng Ma and Bo Liu
 *
 */
@SuppressLint({ "HandlerLeak", "HandlerLeak" })
public class StartActivity extends Activity {
	// 游戏设置参数
	/** 是否人机对战  */
	static public boolean isPVE = true;
	/** 是否练习模式  */
	static public boolean isPractice = true;
	/** 是否玩家先手  */
	static public boolean isFirst = true;
	/** 声音开关  */
	static public boolean soundOpen = true;
	/** 震动开关  */
	static public boolean vibrateOpen = true;
	/** 是否有棋谱  */
	static public boolean hasChessManual = false;

	/** 屏幕高度  */
	static float screenHeight;
	/** 屏幕宽度  */
	static float screenWidth;
	/** 是否进入主菜单界面  */
	static boolean flag = false;

	/** 启动设置模式活动的请求码  */
	final int CODE = 0x717;

	/** 开始游戏按钮  */
	private ImageButton startGameBtn;
	/** 模式设置按钮  */
	private ImageButton setModeBtn;
	/** 关于我们按钮  */
	private ImageButton aboutUsBtn;
	/** 退出游戏按钮  */
	private ImageButton exitBtn;
	/** SoundPool对象，用来播放按钮按下的声音  */
	private SoundPool soundpool;
	
	/** 是否退出  */
	private boolean isExit = false;
	
	/** 接受信息界面跳转  */
	Handler hd = new Handler() {
		@Override
		public void handleMessage(Message msg) {// 重写方法
			switch (msg.what) {
			case 0:
				gotoMainView(); // 主界面
				break;
			}
		}
	};

	/**
	 * 创建界面，做一些数据的初始化工作
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 设置为竖屏
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 设置全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		DisplayMetrics dm = new DisplayMetrics(); // 获取手机分辨率
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenHeight = dm.heightPixels;
		screenWidth = dm.widthPixels;

		readPreferences();

		if (flag == false) {
			gotoWelcomeView();
		} else {
			gotoMainView();
		}
	}

	/**
	 * 进入欢迎界面
	 */
	public void gotoWelcomeView() {
		WelcomeView mView = new WelcomeView(this);
		setContentView(mView);
	}

	/**
	 * 进入主界面
	 */	
	public void gotoMainView() {
		setContentView(R.layout.start);
		if (flag == false) {
			flag = true;
		}

		aboutUsBtn = (ImageButton) findViewById(R.id.aboutus);
		startGameBtn = (ImageButton) findViewById(R.id.startgame);
		setModeBtn = (ImageButton) findViewById(R.id.setmode);
		// loadBtn = (ImageButton) findViewById(R.id.openchessmanual);
		exitBtn = (ImageButton) findViewById(R.id.exit);
		soundpool = new SoundPool(1, AudioManager.STREAM_SYSTEM, 0);

		// 开始游戏按钮设置监听器
		startGameBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				playSound();

				Intent intent = new Intent(StartActivity.this,
						GameActivity.class);
				startActivity(intent);
			}
		});

		// 模式设置按钮设置监听器
		setModeBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				playSound();
				
				Intent intent = new Intent(StartActivity.this,
						SetModeActivity.class);
				startActivity(intent);
			}
		});

		// 关于我们按钮设置监听器
		aboutUsBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				playSound();
				
				Intent intent = new Intent(StartActivity.this,
						AboutUsActivity.class);
				startActivity(intent);
			}
		});

		// 退出按钮设置监听器
		exitBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				isExit = true;
				playSound();
			}
		});
	}

	/**
	 * 退出按钮重载
	 */	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (KeyEvent.KEYCODE_BACK == keyCode) {
			writePreferences();
			flag = false;
			finish();
			System.exit(0);
		}else if (KeyEvent.KEYCODE_VOLUME_DOWN == keyCode){
			AudioManager am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
			am.adjustStreamVolume (AudioManager.STREAM_RING, AudioManager.ADJUST_LOWER, 
					AudioManager.FLAG_SHOW_UI|AudioManager.FLAG_PLAY_SOUND|AudioManager.FLAG_ALLOW_RINGER_MODES);
		}else if (KeyEvent.KEYCODE_VOLUME_UP == keyCode){
			AudioManager am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
			am.adjustStreamVolume (AudioManager.STREAM_RING, AudioManager.ADJUST_RAISE, 
					AudioManager.FLAG_SHOW_UI|AudioManager.FLAG_PLAY_SOUND|AudioManager.FLAG_ALLOW_RINGER_MODES);
		}
		
		return true;
	}

	/**
	 * 读取游戏参数
	 */
	public void readPreferences() {
		SharedPreferences preferences = getSharedPreferences("Pref",
				MODE_PRIVATE);
		isPVE = preferences.getBoolean("PVE", true);
		isPractice = preferences.getBoolean("Practice", true);
		isFirst = preferences.getBoolean("First", true);
		soundOpen = preferences.getBoolean("sound", true);
		vibrateOpen = preferences.getBoolean("vibrate", true);
		hasChessManual = preferences.getBoolean("haschessmanual", false);
	}

	/**
	 * 保存游戏参数
	 */
	public void writePreferences() {
		SharedPreferences preferences = getSharedPreferences("Pref",
				MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean("PVE", isPVE);
		editor.putBoolean("Practice", isPractice);
		editor.putBoolean("First", isFirst);
		editor.putBoolean("sound", soundOpen);
		editor.putBoolean("vibrate", vibrateOpen);
		editor.putBoolean("haschessmanual", hasChessManual);
		editor.commit();
	}
	
	/**
	 * 退出游戏
	 */
	public void exitGame(){
		writePreferences();
		flag = false;
		finish();
		System.exit(0);
	}
	
	/**
	 * 播放声音
	 */
	public void playSound(){
		if (soundOpen) {
			final int sourceId = soundpool.load(StartActivity.this,
					R.raw.startbutton, 1);
			soundpool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
						public void onLoadComplete(SoundPool soundPool,
								int sampleId, int status) {
							soundPool.play(sourceId, 1, 1, 0,
									0, 1);
							if (isExit){
								exitGame();
							}
						}
					});
		}
	}
}
