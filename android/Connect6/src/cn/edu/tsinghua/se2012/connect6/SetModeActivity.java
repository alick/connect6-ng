package cn.edu.tsinghua.se2012.connect6;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class SetModeActivity extends Activity{
	static private boolean isPVE = true;
	static private boolean isPractice = true;
	private String[] PVEArray = {"人机对战", "人人对战"};
	private String[] PracticeArray = {"练习模式", "实战模式"};
	
	private Button PVEmodeBtn;
	private Button operationalModeBtn;
	private Button okBtn;
	private Button cancelBtn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.setmode);
		
		PVEmodeBtn = (Button)findViewById(R.id.PVEmode);
		operationalModeBtn = (Button)findViewById(R.id.operationalmode);
		okBtn = (Button)findViewById(R.id.setmodeok);
		cancelBtn = (Button)findViewById(R.id.setmodecancel);
		
		if (isPVE){
			PVEmodeBtn.setText("人机对战");
		}else{
			PVEmodeBtn.setText("人人对战");
		}
		if (isPractice){
			operationalModeBtn.setText("练习模式");
		}else{
			operationalModeBtn.setText("实战模式");
		}
		
		// 对战模式设置
		PVEmodeBtn.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						isPVE = !isPVE;
						PVEmodeBtn.setText(PVEArray[(isPVE?0:1)]);		
					}
				});
		// 游戏模式按钮
		operationalModeBtn.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						isPractice = !isPractice;
						operationalModeBtn.setText(PracticeArray[(isPractice?0:1)]);		
					}
				});
		// 确定按钮
		okBtn.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Intent intent = getIntent();
						Bundle bundle = new Bundle();
						bundle.putBoolean("isPVE", isPVE);
						bundle.putBoolean("isPractice", isPractice);
						intent.putExtras(bundle);
						setResult(0x717, intent);
						finish();
					}
				});
		// 取消按钮
		cancelBtn.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						finish();
					}
				});
	}
}
