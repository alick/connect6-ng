package cn.edu.tsinghua.se2012.connect6;

import java.util.Vector;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import cn.edu.tsinghua.se2012.connect6.ChessBoardView;

@SuppressWarnings({"rawtypes", "unchecked"})
public class GameActivity extends Activity {	
	private static Vector data = new Vector();

	private ChessBoardView chessboard;
	static public Button newGameBtn;
	static public Button undoGameBtn;
	private Button gameSettingBtn;
	static public Button saveGameBtn;
	private Button zoomOutBtn;
	private Button zoomInBtn;
	private SoundPool soundpool;

	// 以下为所有的游戏状态变量的设置
	static public boolean undoEnable = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);

		chessboard = (ChessBoardView) findViewById(R.id.chessborad);
		newGameBtn = (Button) findViewById(R.id.newgame);
		undoGameBtn = (Button) findViewById(R.id.undogame);
		gameSettingBtn = (Button) findViewById(R.id.gamesetting);
		saveGameBtn = (Button) findViewById(R.id.save);
		zoomOutBtn = (Button) findViewById(R.id.zoomout);
		zoomInBtn = (Button) findViewById(R.id.zoomin);
		soundpool = new SoundPool(1, AudioManager.STREAM_SYSTEM, 0);
		zoomOutBtn.setEnabled(false);

		// 获取屏幕分辨率
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenWidth = dm.widthPixels;
		int screenHeight = dm.heightPixels;
		int barHeight = (int) (40.0f * dm.density + 0.5f);

		chessboard.SetArea(0, screenWidth, 0, screenHeight - barHeight * 2);

		// 画上棋盘线
		chessboard.ZoomOut();
		chessboard.invalidate(); // 重新绘制棋盘

		// Vector data = new Vector();
		chessboard.init(data, StartActivity.isPVE);
		if (StartActivity.isPVE && (!StartActivity.isFirst)) {
			chessboard.Last();
		} else {
			chessboard.First();
		}
		CheckUndo();

		// 悔棋
		undoGameBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				playSound();
				chessboard.Back();
				chessboard.invalidate();
				CheckUndo();
			}
		});

		// 游戏设置
		gameSettingBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				playSound();
				Intent intent = new Intent(GameActivity.this,
						GameSettingActivity.class);
				startActivity(intent);
			}
		});

		// 棋盘缩小
		zoomOutBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				playSound();
				chessboard.ZoomOut();
				chessboard.invalidate();

				if (0 == chessboard.getCurrentSizeLevel())
					zoomOutBtn.setEnabled(false);
				if (3 == chessboard.getCurrentSizeLevel())
					zoomInBtn.setEnabled(true);
			}
		});

		// 棋盘放大
		zoomInBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				playSound();
				chessboard.ZoomIn();
				chessboard.invalidate();

				if (4 == chessboard.getCurrentSizeLevel())
					zoomInBtn.setEnabled(false);
				if (1 == chessboard.getCurrentSizeLevel())
					zoomOutBtn.setEnabled(true);
			}

		});
	}

	// 开始新游戏
	public void NewgameClick(View v) {
		playSound();
		Dialog alertDialog = new AlertDialog.Builder(this).setTitle("是否保存棋谱？")
				.setMessage("是否保存棋谱？（如果不保存谱则当前进行的游戏将丢失，如果保存棋谱则之前保存的棋谱将被覆盖）")
				.setPositiveButton("保存", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						SaveChess();
						if (StartActivity.isPVE && (!StartActivity.isFirst)) {
							chessboard.Last();
						} else {
							chessboard.First();
						}
						chessboard.invalidate();
						CheckUndo();
					}
				})
				.setNeutralButton("不保存", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						if (StartActivity.isPVE && (!StartActivity.isFirst)) {
							chessboard.Last();
						} else {
							chessboard.First();
						}
						chessboard.invalidate();
						CheckUndo();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {

					}
				}).setCancelable(false).create();
		alertDialog.show();
	}

	// 保存棋谱
	public void SaveClick(View v) {
		playSound();
		Dialog alertDialog = new AlertDialog.Builder(this).setTitle("确定保存棋谱？")
				.setMessage("确定保存棋谱吗？（之前保存的棋谱将被覆盖）")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						SaveChess();						
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {

					}
				}).setCancelable(false).create();
		alertDialog.show();
	}

	// 载入棋谱
	
	public void LoadClick(View v) {
		playSound();
		if (StartActivity.hasChessManual == false){
			Toast.makeText(GameActivity.this, "当前没有棋谱", Toast.LENGTH_SHORT).show();
		}else{
			if (!undoEnable) {
				SharedPreferences preferences = getSharedPreferences("Data",
						MODE_PRIVATE);
				int Size = preferences.getInt("Size", 0);
				data.clear();
				for (int i = 0; i < Size; i++) {
					mypoint p = new mypoint(preferences.getInt("x" + i, 0),
							preferences.getInt("y" + i, 0), preferences.getInt(
									"color" + i, 0));
					data.add(p);
				}
				chessboard.init(data, StartActivity.isPVE);
				chessboard.Open();
				chessboard.invalidate();
				CheckUndo();
				return;
			}
			Dialog alertDialog = new AlertDialog.Builder(this)
					.setTitle("确定载入棋谱？")
					.setMessage("确定载入棋谱吗？（当前进行的游戏将丢失）")
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							SharedPreferences preferences = getSharedPreferences(
									"Data", MODE_PRIVATE);
							int Size = preferences.getInt("Size", 0);
							data.clear();
							for (int i = 0; i < Size; i++) {
								mypoint p = new mypoint(preferences.getInt("x" + i,
										0), preferences.getInt("y" + i, 0),
										preferences.getInt("color" + i, 0));
								data.add(p);
							}
							chessboard.init(data, StartActivity.isPVE);
							chessboard.Open();
							chessboard.invalidate();
							CheckUndo();
						}
					})
					.setNegativeButton("取消", new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {

						}
					}).setCancelable(false).create();
			alertDialog.show();
		}		
	}

	// 返回主菜单
	public void ReturnClick(View v) {
		playSound();
		if (!undoEnable) {
			data.clear();
			finish();
			return;
		}
		Dialog alertDialog = new AlertDialog.Builder(this).setTitle("是否保存棋谱？")
				.setMessage("是否保存棋谱？（如果不保存谱则当前进行的游戏将丢失，如果保存棋谱则之前保存的棋谱将被覆盖）")
				.setPositiveButton("保存", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						SaveChess();
						data.clear();
						finish();
					}
				})
				.setNeutralButton("不保存", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						data.clear();
						finish();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {

					}
				}).setCancelable(false).create();
		alertDialog.show();
	}

	public void SaveChess() {
		Vector tempdata = chessboard.getData();
		mypoint p;
		SharedPreferences preferences = getSharedPreferences("Data",
				MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		int Size = tempdata.size();
		editor.putInt("Size", Size);
		for (int i = 0; i < Size; i++) {
			p = (mypoint) tempdata.elementAt(i);
			editor.putInt("x" + i, p.getx());
			editor.putInt("y" + i, p.gety());
			editor.putInt("color" + i, p.getcolor());
		}
		boolean saveSuccess = editor.commit();
		if (saveSuccess){
			StartActivity.hasChessManual = true;
			Toast.makeText(this, "保存棋谱成功", Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(this, "保存棋谱失败", Toast.LENGTH_SHORT).show();
		}
	}

	public void CheckUndo() {
		int Size = chessboard.getData().size();
		if ((0 == Size)
				|| ((1 == Size) && StartActivity.isPVE && (!StartActivity.isFirst))) {
			undoGameBtn.setEnabled(false);
			newGameBtn.setEnabled(false);
			saveGameBtn.setEnabled(false);
			undoEnable = false;
		} else {
			undoGameBtn.setEnabled(true);
			newGameBtn.setEnabled(true);
			saveGameBtn.setEnabled(true);
			undoEnable = true;
		}
		if (!StartActivity.isPractice)
			undoGameBtn.setEnabled(false);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (!undoEnable) {
			data.clear();
			finish();
			return true;
		}
		Dialog alertDialog = new AlertDialog.Builder(this).setTitle("是否保存棋谱？")
				.setMessage("是否保存棋谱？（如果不保存谱则当前进行的游戏将丢失，如果保存棋谱则之前保存的棋谱将被覆盖）")
				.setPositiveButton("保存", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						SaveChess();
						data.clear();
						finish();
					}
				})
				.setNeutralButton("不保存", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						data.clear();
						finish();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {

					}
				}).setCancelable(false).create();
		alertDialog.show();
		return true;
	}
	
	public void playSound(){
		if (StartActivity.soundOpen) {
			final int sourceId = soundpool.load(GameActivity.this,
					R.raw.boardbutton, 1);
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
