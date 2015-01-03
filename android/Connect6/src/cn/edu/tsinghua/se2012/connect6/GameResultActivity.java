package cn.edu.tsinghua.se2012.connect6;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class GameResultActivity extends Activity {
	//游戏结果
	public static int result;
	
	private final int SUCCESS = 0;
	private final int FAIL = 1;
	private final int GAMEOVER = 2;
	private final int vibrateTime = 2000;
	
	private ImageView gameSuccess;
	private ImageView gameFail;
	private RelativeLayout doubleGame;
	private ImageView blackLaugh;
	private ImageView blackCry;
	private ImageView whiteLaugh;
	private ImageView whiteCry;
	private Button okButton;
	private SoundPool soundpool;
	private Vibrator vibrator;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 设置为竖屏屏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.result);
				
		gameSuccess = (ImageView)findViewById(R.id.gamesuccess);
		gameFail = (ImageView)findViewById(R.id.gamefail);
		doubleGame = (RelativeLayout)findViewById(R.id.doublegame);
		blackLaugh = (ImageView)findViewById(R.id.black_laugh);
		blackCry = (ImageView)findViewById(R.id.black_cry);
		whiteLaugh = (ImageView)findViewById(R.id.white_laugh);
		whiteCry = (ImageView)findViewById(R.id.white_cry);
		okButton = (Button)findViewById(R.id.gameresultok);
		soundpool = new SoundPool(3, AudioManager.STREAM_SYSTEM, 0);
		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		result = bundle.getInt("result");
		
		switch(result){
		case 1:
			playSound(SUCCESS);
			if (StartActivity.vibrateOpen){
				vibrator.vibrate(vibrateTime);
			}			
			gameSuccess.setVisibility(View.VISIBLE);
			gameFail.setVisibility(View.GONE);
			doubleGame.setVisibility(View.GONE);
			break;
		case 2:
			playSound(FAIL);
			if (StartActivity.vibrateOpen){
				vibrator.vibrate(vibrateTime);
			}
			gameSuccess.setVisibility(View.GONE);
			gameFail.setVisibility(View.VISIBLE);
			doubleGame.setVisibility(View.GONE);
			break;
		case 3:
			playSound(GAMEOVER);
			if (StartActivity.vibrateOpen){
				vibrator.vibrate(vibrateTime);
			}
			gameSuccess.setVisibility(View.GONE);
			gameFail.setVisibility(View.GONE);
			doubleGame.setVisibility(View.VISIBLE);
			blackLaugh.setVisibility(View.VISIBLE);
			blackCry.setVisibility(View.GONE);
			whiteLaugh.setVisibility(View.GONE);
			whiteCry.setVisibility(View.VISIBLE);
			break;
		case 4:
			playSound(GAMEOVER);
			if (StartActivity.vibrateOpen){
				vibrator.vibrate(vibrateTime);
			}
			gameSuccess.setVisibility(View.GONE);
			gameFail.setVisibility(View.GONE);
			doubleGame.setVisibility(View.VISIBLE);
			blackLaugh.setVisibility(View.GONE);
			blackCry.setVisibility(View.VISIBLE);
			whiteLaugh.setVisibility(View.VISIBLE);
			whiteCry.setVisibility(View.GONE);
			break;
		}
		
		okButton.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				finish();			
			}
		});
	}
	
	public void playSound(int id){
		if (StartActivity.soundOpen) {
			final int sourceId;
			if (id == SUCCESS){
				sourceId = soundpool.load(GameResultActivity.this,
						R.raw.success, 1);
			}else if (id == FAIL){
				sourceId = soundpool.load(GameResultActivity.this,
						R.raw.fail, 1);
			}else{
				sourceId = soundpool.load(GameResultActivity.this,
						R.raw.gameover, 1);
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
