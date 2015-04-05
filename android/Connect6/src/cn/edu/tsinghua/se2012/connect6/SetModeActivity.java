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
 * 模式设置界面
 * 
 * @version 1.0
 * @author Shuyang Jiang, Yipeng Ma and Bo Liu
 *
 */

public class SetModeActivity extends Activity{
	/** 存储练习模式和实战模式的字符数组 */
	private String[] PracticeArray = {"练习模式", "实战模式"};
	/** 存储玩家先手和电脑先手的字符数组 */
	private String[] FirstArray = {"玩家先手", "电脑先手"};
	
	/** 按下对战模式按钮 ，游戏模式按钮和落子顺序按钮发出的声音的标记  */
	final int OPTION_BUTTON = 0;
	/** 按下确定按钮发出的声音的标记   */
	final int OK_BUTTON = 1;
	
	/** 对战模式按钮 */
	private Button PVEmodeBtn;
	/** 游戏模式按钮 */
	private Button operationalModeBtn;
	/** 落子顺序按钮 */
	private Button firstModeBtn;
	/** 确定按钮 */
	private Button okBtn;
	/** SoundPool对象，用来播放按钮按下的声音 */
	private SoundPool soundpool;

	/**
	 * 创建界面，做一些数据的初始化工作
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 设置为竖屏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.setmode);
		
		PVEmodeBtn = (Button)findViewById(R.id.PVEmode);
		operationalModeBtn = (Button)findViewById(R.id.operationalmode);
		firstModeBtn = (Button) findViewById(R.id.firstmode);
		okBtn = (Button)findViewById(R.id.setmodeok);
		soundpool = new SoundPool(2, AudioManager.STREAM_SYSTEM, 0);
		
		if (StartActivity.isPVE){
			PVEmodeBtn.setText("人机对战");
			firstModeBtn.setEnabled(true);
		}else{
			PVEmodeBtn.setText("人人对战");
			firstModeBtn.setEnabled(false);
		}
		if (StartActivity.isPractice){
			operationalModeBtn.setText("练习模式");
		}else{
			operationalModeBtn.setText("实战模式");
		}
		if (StartActivity.isFirst){
			firstModeBtn.setText("玩家先手");
		}else{
			firstModeBtn.setText("电脑先手");
		}
		
		// 对战模式设置
		PVEmodeBtn.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						StartActivity.isPVE = !StartActivity.isPVE;
						
						playSound(OPTION_BUTTON);
						
						if (StartActivity.isPVE){							
							PVEmodeBtn.setText("人机对战");
							firstModeBtn.setEnabled(true);
						}else{
							PVEmodeBtn.setText("人人对战");
							firstModeBtn.setEnabled(false);
						}
					}
				});
		// 游戏模式设置
		operationalModeBtn.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						playSound(OPTION_BUTTON);
						
						StartActivity.isPractice = !StartActivity.isPractice;
						operationalModeBtn.setText(PracticeArray[(StartActivity.isPractice?0:1)]);
					}
				});
		// 落子顺序设置
		firstModeBtn.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						playSound(OPTION_BUTTON);
						
						StartActivity.isFirst = !StartActivity.isFirst;
						firstModeBtn.setText(FirstArray[(StartActivity.isFirst?0:1)]);
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
	 * 播放按钮按下的声音
	 * 
	 * @param id 按钮按下声音的标记. OPTION_BUTTON:对战模式按钮 ，游戏模式按钮和落子顺序按钮	OK_BUTTON：确定按钮
	 */
	public void playSound(int id){
		if (StartActivity.soundOpen) {			
			final int sourceId;
			if (id == OPTION_BUTTON){
				sourceId = soundpool.load(SetModeActivity.this,R.raw.optionbutton, 1);
			}else if (id == OK_BUTTON){
				sourceId = soundpool.load(SetModeActivity.this,R.raw.okbutton, 1);
			}else{
				sourceId = 0;
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
