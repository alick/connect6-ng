package cn.edu.tsinghua.se2012.connect6;

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
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;

@SuppressLint({ "HandlerLeak", "HandlerLeak" })
public class StartActivity extends Activity {
	static public boolean isPVE = true;
	static public boolean isPractice = true;
	static public boolean isFirst = true;
	static float screenHeight; // 屏幕高度
	static float screenWidth; // 屏幕宽度
	static boolean flag = false;
	
	final int CODE = 0x717;			//启动设置模式活动的请求码

	ImageButton startGameBtn;
	ImageButton setModeBtn;
	ImageButton aboutUsBtn;
	ImageButton exitBtn;

	// 接受信息界面跳转
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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 设置为竖屏屏
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 设置全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		DisplayMetrics dm = new DisplayMetrics(); // 获取手机分辨率
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenHeight = dm.heightPixels;
		screenWidth = dm.widthPixels;

		if (flag == false) {
			gotoWelcomeView();
		} else {
			gotoMainView();
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == CODE && resultCode == CODE){
			Bundle bundle = data.getExtras();
			isPVE = bundle.getBoolean("isPVE");
			isPractice = bundle.getBoolean("isPractice");
		}
	}

	// 欢迎界面
	public void gotoWelcomeView() {
		WelcomeView mView = new WelcomeView(this);
		setContentView(mView);
	}

	// 进入主界面
	public void gotoMainView() {
		setContentView(R.layout.start);
		if (flag == false) {
			flag = true;
		}

		aboutUsBtn = (ImageButton) findViewById(R.id.aboutus);
		startGameBtn = (ImageButton) findViewById(R.id.startgame);
		setModeBtn = (ImageButton) findViewById(R.id.setmode);
		exitBtn = (ImageButton) findViewById(R.id.exit);

		// 开始游戏按钮
		startGameBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(StartActivity.this,
						GameActivity.class);
				Bundle bundle = new Bundle();
				bundle.putBoolean("isPVE", isPVE);
				bundle.putBoolean("isPractice", isPractice);
				intent.putExtras(bundle);
				startActivity(intent);
				finish();
			}
		});

		// 设置模式按钮
		setModeBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(StartActivity.this,
						SetModeActivity.class);
				startActivity(intent);
			}
		});

		// 关于我们按钮
		aboutUsBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(StartActivity.this,
						AboutUsActivity.class);
				startActivity(intent);
			}
		});
		
		//退出按钮
		exitBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				flag = false;
				finish();
				System.exit(0);
			}
		});
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event){
		if(KeyEvent.KEYCODE_BACK == keyCode){
			flag = false;
			finish();
			System.exit(0);
		}
		return true;
	}
}
