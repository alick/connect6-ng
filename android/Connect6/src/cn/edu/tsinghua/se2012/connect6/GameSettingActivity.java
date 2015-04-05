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

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

/**
 * 游戏设置界面
 * 
 * @version 1.0
 * @author Shuyang Jiang, Yipeng Ma and Bo Liu
 *
 */

public class GameSettingActivity extends Activity {
	/** 开启,关闭字符数组  */
	private String[] openArray = { "开启", "关闭" };

	/** 声音效果按钮  */
	private Button soundOpenBtn;
	/** 振动效果按钮  */
	private Button vibrateOpenBtn;
	/** 确定按钮  */
	private Button okBtn;
	/** SoundPool对象，用来播放按钮按下的声音  */
	private SoundPool soundpool;
	
	/** 按下声音效果按钮 ，振动效果按钮发出的声音的标记  */
	final int OPTION_BUTTON = 0;
	/** 按下确定按钮发出的声音的标记  */
	final int OK_BUTTON = 1;

	/**
	 * 创建界面，做一些数据的初始化工作
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 设置为竖屏
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 设置无标题
		setContentView(R.layout.gamesetting);
		
		soundOpenBtn = (Button)findViewById(R.id.soundOpen);
		vibrateOpenBtn = (Button)findViewById(R.id.vibrateOpen);
		okBtn = (Button)findViewById(R.id.gamesettingok);
		soundpool = new SoundPool(2, AudioManager.STREAM_SYSTEM, 0);
		
		if (StartActivity.soundOpen){
			soundOpenBtn.setText("开启");
		}else{
			soundOpenBtn.setText("关闭");
		}
		if (StartActivity.vibrateOpen){
			vibrateOpenBtn.setText("开启");
		}else{
			vibrateOpenBtn.setText("关闭");
		}
	
	// 声音效果设置
	soundOpenBtn.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					playSound(OPTION_BUTTON);
					StartActivity.soundOpen = !StartActivity.soundOpen;
					soundOpenBtn.setText(openArray[(StartActivity.soundOpen?0:1)]);		
				}
			});
	// 振动效果按钮
	vibrateOpenBtn.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					playSound(OPTION_BUTTON);
					StartActivity.vibrateOpen = !StartActivity.vibrateOpen;
					vibrateOpenBtn.setText(openArray[(StartActivity.vibrateOpen?0:1)]);		
				}
			});
	// 确定按钮
	okBtn.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					playSound(OK_BUTTON);
					finish();
				}
			});
	}
	
	/**
	 * 播放声音
	 */
	public void playSound(int id){
		if (StartActivity.soundOpen) {			
			final int sourceId;
			if (id == OPTION_BUTTON){
				sourceId = soundpool.load(GameSettingActivity.this,R.raw.optionbutton, 1);
			}else{
				sourceId = soundpool.load(GameSettingActivity.this,R.raw.okbutton, 1);
			}
					
			soundpool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
						public void onLoadComplete(SoundPool soundPool,
								int sampleId, int status) {
							soundPool.play(sourceId, 1, 1, 0,
									0, 1);
						}
					});
		}
	}
}
