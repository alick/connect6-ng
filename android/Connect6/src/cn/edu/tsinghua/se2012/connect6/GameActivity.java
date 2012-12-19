package cn.edu.tsinghua.se2012.connect6;

import java.util.Vector;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ZoomControls;
import cn.edu.tsinghua.se2012.connect6.ChessBoardView;;

public class GameActivity extends Activity{
	//棋盘背景图片的原始Bitmap对象
//	private Bitmap originalChessBoard;
	//棋盘背景图片的调整后的并用于实际显示的Bitmap对象
//	private Bitmap resizeChessBoard;
	
	//以下为所有的游戏状态变量的设置
    private int scaleSize = 3;							//当前所处于放大的倍数，分为1-5，默认为3，缩小后为1,2,放大后为4,5
    private int[] scaleArray = new int[5]; 				//存储棋盘图片的5种大小的尺寸
    
	final int CODE = 0x717;				//开启游戏设置界面请求码
	
	private static Vector data = new Vector();
	
	private ChessBoardView chessboard;
	static public Button newGameBtn;
	static public Button undoGameBtn;
	private Button gameSettingBtn;
	static public Button saveGameBtn;
	private Button loadGameBtn;
	private Button returnmenuBtn;
	private Button zoomOut;
	private Button zoomIn;
	
	static public boolean undoEnable = true;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		
		chessboard = (ChessBoardView)findViewById(R.id.chessborad);
		newGameBtn = (Button)findViewById(R.id.newgame);
		undoGameBtn = (Button)findViewById(R.id.undogame);
		gameSettingBtn = (Button)findViewById(R.id.gamesetting);
		saveGameBtn = (Button) findViewById(R.id.save);
		loadGameBtn = (Button) findViewById(R.id.load);
		returnmenuBtn = (Button)findViewById(R.id.returnmenu);
		zoomOut = (Button) findViewById(R.id.zoomout);
		zoomIn = (Button) findViewById(R.id.zoomin);
		zoomOut.setEnabled(false);

		//获取屏幕分辨率
		DisplayMetrics dm = new DisplayMetrics();   
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        int barHeight = (int) (40.0f * dm.density + 0.5f);
        
        //得到棋盘图片的5种大小的尺寸
//        for (int i = 0; i < 5; i++){
//        	scaleArray[i] = (int)((screenWidth-10) * Math.pow(1.25,i-2));
//        }
        
        //布置好棋盘背景图片
//        originalChessBoard = BitmapFactory.decodeResource(getResources(), R.drawable.chessboard); 
//		resizeChessBoard = Bitmap.createScaledBitmap(originalChessBoard, scaleArray[2], scaleArray[2], true);
        //ChessBoardView c = (ChessboardView) FindViewById(R.id.chessborad);
		chessboard.SetArea(0, screenWidth, 0, screenHeight - barHeight * 2);
//		chessboard.setImageBitmap(resizeChessBoard);
        
		//画上棋盘线
		chessboard.ZoomOut();
        chessboard.invalidate(); //重新绘制棋盘
        
        //Vector data = new Vector();
        chessboard.init(data, StartActivity.isPVE);
        if(StartActivity.isPVE && (!StartActivity.isFirst)){
        	chessboard.Last();
        }else{
        	chessboard.First();
        }
        CheckUndo();
		
		//悔棋
		undoGameBtn.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	chessboard.Back();
		    	chessboard.invalidate();
		    	CheckUndo();
		    }
		});
		
		//游戏设置
		gameSettingBtn.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	Intent intent = new Intent(GameActivity.this,
						GameSettingActivity.class);
				startActivity(intent);
		    }
		});
		
		//棋盘缩小
		zoomOut.setOnClickListener(new View.OnClickListener()   
        {   
            public void onClick(View v)   
            {    
//            	scaleSize = scaleSize + 1;
//            	resizeChessBoard = Bitmap.createScaledBitmap(originalChessBoard, scaleArray[scaleSize-1], scaleArray[scaleSize-1], true); 
//                chessboard.setImageBitmap(resizeChessBoard);
//                               
//                zoomControls.setIsZoomOutEnabled(true);
//                
//                if (scaleSize == 5){
//                	zoomControls.setIsZoomInEnabled(false);
//                }
            	chessboard.ZoomOut();
            	chessboard.invalidate();
            	if(0 == chessboard.getCurrentSizeLevel())
            		zoomOut.setEnabled(false);
            	if(3 == chessboard.getCurrentSizeLevel())
            		zoomIn.setEnabled(true);
            }   
        });
		
        //棋盘放大
		zoomIn.setOnClickListener(new View.OnClickListener()   
        {   
            public void onClick(View v) {   
//            	scaleSize = scaleSize - 1;
//            	resizeChessBoard = Bitmap.createScaledBitmap(originalChessBoard, scaleArray[scaleSize-1], scaleArray[scaleSize-1], true); 
//                chessboard.setImageBitmap(resizeChessBoard);
//                                
//                zoomControls.setIsZoomInEnabled(true);
//                
//                if (scaleSize == 1){
//                	zoomControls.setIsZoomOutEnabled(false);
//                } 
            	chessboard.ZoomIn();
            	chessboard.invalidate();
            	if(4 == chessboard.getCurrentSizeLevel())
            		zoomIn.setEnabled(false);
            	if(1 == chessboard.getCurrentSizeLevel())
            		zoomOut.setEnabled(true);
            }   
               
        }); 
	}
	
	//开始新游戏
	public void NewgameClick(View v) {
		Dialog alertDialog = new AlertDialog.Builder(this).
				setTitle("是否保存棋谱？").
				setMessage("是否保存棋谱？（如果不保存谱则当前进行的游戏将丢失，如果保存棋谱则之前保存的棋谱将被覆盖）").
				setPositiveButton("保存", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						SaveChess();
						if(StartActivity.isPVE && (!StartActivity.isFirst)){
							chessboard.Last();
						}else{
							chessboard.First();
						}
						chessboard.invalidate();
						CheckUndo();
					}
				}).
				setNeutralButton("不保存", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						if(StartActivity.isPVE && (!StartActivity.isFirst)){
							chessboard.Last();
						}else{
							chessboard.First();
						}
						chessboard.invalidate();
						CheckUndo();
					}
				}).
				setNegativeButton("取消", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						
					}
				}).
				setCancelable(false).create();
		alertDialog.show();
	}
	
	//保存棋谱
	public void SaveClick(View v){
		Dialog alertDialog = new AlertDialog.Builder(this).
				setTitle("确定保存棋谱？").
				setMessage("确定保存棋谱吗？（之前保存的棋谱将被覆盖）").
				setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						SaveChess();
					}
				}).
				setNegativeButton("取消", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						
					}
				}).
				setCancelable(false).create();
		alertDialog.show();
	}
	
	//载入棋谱
	public void LoadClick(View v) {
		if(!undoEnable){
			SharedPreferences preferences = getSharedPreferences("Data", MODE_PRIVATE);
			int Size = preferences.getInt("Size", 0);
			data.clear();
			for(int i = 0; i < Size; i++){
				mypoint p = new mypoint(preferences.getInt("x" + i, 0), 
						preferences.getInt("y" + i, 0), 
						preferences.getInt("color" + i, 0));
				data.add(p);
			}
			chessboard.init(data, StartActivity.isPVE);
			chessboard.Open();
			chessboard.invalidate();
			CheckUndo();
			return;
		}
		Dialog alertDialog = new AlertDialog.Builder(this).
				setTitle("确定载入棋谱？").
				setMessage("确定载入棋谱吗？（当前进行的游戏将丢失）").
				setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						SharedPreferences preferences = getSharedPreferences("Data", MODE_PRIVATE);
						int Size = preferences.getInt("Size", 0);
						data.clear();
						for(int i = 0; i < Size; i++){
							mypoint p = new mypoint(preferences.getInt("x" + i, 0), 
									preferences.getInt("y" + i, 0), 
									preferences.getInt("color" + i, 0));
							data.add(p);
						}
						chessboard.init(data, StartActivity.isPVE);
						chessboard.Open();
						chessboard.invalidate();
						CheckUndo();
					}
				}).
				setNegativeButton("取消", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						
					}
				}).
				setCancelable(false).create();
		alertDialog.show();
	}
	
	//返回主菜单
	public void ReturnClick(View v) {
		if(!undoEnable){
			data.clear();
			finish();
			return;
		}
		Dialog alertDialog = new AlertDialog.Builder(this).
				setTitle("是否保存棋谱？").
				setMessage("是否保存棋谱？（如果不保存谱则当前进行的游戏将丢失，如果保存棋谱则之前保存的棋谱将被覆盖）").
				setPositiveButton("保存", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						SaveChess();
						data.clear();
						finish();
					}
				}).
				setNeutralButton("不保存", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						data.clear();
						finish();
					}
				}).
				setNegativeButton("取消", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						
					}
				}).
				setCancelable(false).create();
		alertDialog.show();
	}
	
	public void SaveChess(){
		Vector tempdata = chessboard.getData();
		mypoint p;
		SharedPreferences preferences = getSharedPreferences("Data", MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		int Size = tempdata.size();
		editor.putInt("Size", Size);
		for(int i = 0; i < Size; i++){
			p = (mypoint) tempdata.elementAt(i);
			editor.putInt("x" + i, p.getx());
			editor.putInt("y" + i, p.gety());
			editor.putInt("color" + i, p.getcolor());
		}
		editor.commit();
	}
	
	public void CheckUndo(){
		int Size = chessboard.getData().size();
		if((0 == Size) || ((1 == Size) && StartActivity.isPVE && (!StartActivity.isFirst))){
			undoGameBtn.setEnabled(false);
			newGameBtn.setEnabled(false);
			saveGameBtn.setEnabled(false);
			undoEnable = false;
		}else{
			undoGameBtn.setEnabled(true);
			newGameBtn.setEnabled(true);
			saveGameBtn.setEnabled(true);
			undoEnable = true;
		}
		if(!StartActivity.isPractice)
			undoGameBtn.setEnabled(false);
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event){
		if(!undoEnable){
			data.clear();
			finish();
			return true;
		}
		Dialog alertDialog = new AlertDialog.Builder(this).
				setTitle("是否保存棋谱？").
				setMessage("是否保存棋谱？（如果不保存谱则当前进行的游戏将丢失，如果保存棋谱则之前保存的棋谱将被覆盖）").
				setPositiveButton("保存", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						SaveChess();
						data.clear();
						finish();
					}
				}).
				setNeutralButton("不保存", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						data.clear();
						finish();
					}
				}).
				setNegativeButton("取消", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						
					}
				}).
				setCancelable(false).create();
		alertDialog.show();
		return true;
	}
	
}
