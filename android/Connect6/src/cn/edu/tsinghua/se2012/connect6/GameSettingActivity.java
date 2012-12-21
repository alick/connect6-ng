package cn.edu.tsinghua.se2012.connect6;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class GameSettingActivity extends Activity {
	
	private String[] openArray = { "开启", "关闭" };

	private Button soundOpenBtn;
	private Button vibrateOpenBtn;
	private Button okBtn;
	private SoundPool soundpool;
	
	final int OPTION_BUTTON = 0;
	final int OK_BUTTON = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 设置为竖屏屏
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
	//振动效果按钮
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
