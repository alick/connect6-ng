package cn.edu.tsinghua.se2012.connect6;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class SetModeActivity extends Activity{
	private String[] PVEArray = {"人机对战", "人人对战"};
	private String[] PracticeArray = {"练习模式", "实战模式"};
	private String[] FirstArray = {"玩家先手", "电脑先手"};
	
	private Button PVEmodeBtn;
	private Button operationalModeBtn;
	private Button firstModeBtn;
	private Button okBtn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.setmode);
		
		PVEmodeBtn = (Button)findViewById(R.id.PVEmode);
		operationalModeBtn = (Button)findViewById(R.id.operationalmode);
		firstModeBtn = (Button) findViewById(R.id.firstmode);
		okBtn = (Button)findViewById(R.id.setmodeok);
		
		if (StartActivity.isPVE){
			PVEmodeBtn.setText("人机对战");
		}else{
			PVEmodeBtn.setText("人人对战");
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
						PVEmodeBtn.setText(PVEArray[(StartActivity.isPVE?0:1)]);		
					}
				});
		// 游戏模式按钮
		operationalModeBtn.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						StartActivity.isPractice = !StartActivity.isPractice;
						operationalModeBtn.setText(PracticeArray[(StartActivity.isPractice?0:1)]);		
					}
				});
		// 落子顺序设置
				firstModeBtn.setOnClickListener(new View.OnClickListener() {
							public void onClick(View v) {
								StartActivity.isFirst = !StartActivity.isFirst;
								firstModeBtn.setText(FirstArray[(StartActivity.isFirst?0:1)]);	
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
