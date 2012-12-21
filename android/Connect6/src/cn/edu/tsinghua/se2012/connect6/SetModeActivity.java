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

public class SetModeActivity extends Activity{
	private String[] PVEArray = {"人机对战", "人人对战"};
	private String[] PracticeArray = {"练习模式", "实战模式"};
	private String[] FirstArray = {"玩家先手", "电脑先手"};
	
	final int OPTION_BUTTON = 0;
	final int OK_BUTTON = 1;
	
	private Button PVEmodeBtn;
	private Button operationalModeBtn;
	private Button firstModeBtn;
	private Button okBtn;
	private SoundPool soundpool;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 设置为竖屏屏
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
		// 游戏模式按钮
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
	
	public void playSound(int id){
		if (StartActivity.soundOpen) {			
			final int sourceId;
			if (id == OPTION_BUTTON){
				sourceId = soundpool.load(SetModeActivity.this,R.raw.optionbutton, 1);
			}else{
				sourceId = soundpool.load(SetModeActivity.this,R.raw.okbutton, 1);
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
