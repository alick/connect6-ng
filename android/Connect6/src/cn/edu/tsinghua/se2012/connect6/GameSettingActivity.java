package cn.edu.tsinghua.se2012.connect6;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class GameSettingActivity extends Activity {
	
	private String[] openArray = { "开启", "关闭" };

	private Button soundOpenBtn;
	private Button vibrateOpenBtn;
	private Button okBtn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 设置为竖屏屏
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 设置全屏
		setContentView(R.layout.gamesetting);
		
		soundOpenBtn = (Button)findViewById(R.id.soundOpen);
		vibrateOpenBtn = (Button)findViewById(R.id.vibrateOpen);
		okBtn = (Button)findViewById(R.id.gamesettingok);
		
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
					StartActivity.soundOpen = !StartActivity.soundOpen;
					soundOpenBtn.setText(openArray[(StartActivity.soundOpen?0:1)]);		
				}
			});
	//振动效果按钮
	vibrateOpenBtn.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					StartActivity.vibrateOpen = !StartActivity.vibrateOpen;
					vibrateOpenBtn.setText(openArray[(StartActivity.vibrateOpen?0:1)]);		
				}
			});
	// 确定按钮
	okBtn.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					finish();
				}
			});
	}
}
