package cn.edu.tsinghua.se2012.connect6;

import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ZoomControls;
import cn.edu.tsinghua.se2012.connect6.ChessBoardView;;

public class GameActivity extends Activity{
	//棋盘背景图片的原始Bitmap对象
	private Bitmap originalChessBoard;
	//棋盘背景图片的调整后的并用于实际显示的Bitmap对象
	private Bitmap resizeChessBoard;
	
	//以下为所有的游戏状态变量的设置
	private static boolean soundOpen = true;		//声音是否开启
	private static boolean vibrateOpen = true;		//震动是否开启
    private int scaleSize = 3;							//当前所处于放大的倍数，分为1-5，默认为3，缩小后为1,2,放大后为4,5
    private int[] scaleArray = new int[5]; 				//存储棋盘图片的5种大小的尺寸
    
	final int CODE = 0x717;				//开启游戏设置界面请求码
	
	private ChessBoardView chessboard;
	private Button newGameBtn;
	private Button undoGameBtn;
	private Button gameSettingBtn;
	private Button saveGameBtn;
	private Button returnmenuBtn;
	private ZoomControls zoomControls;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		
		chessboard = (ChessBoardView)findViewById(R.id.chessborad);
		newGameBtn = (Button)findViewById(R.id.newgame);
		undoGameBtn = (Button)findViewById(R.id.undogame);
		gameSettingBtn = (Button)findViewById(R.id.gamesetting);
		saveGameBtn = (Button) findViewById(R.id.save);
		returnmenuBtn = (Button)findViewById(R.id.returnmenu);
		zoomControls = (ZoomControls)findViewById(R.id.zoomControls);
		if(!StartActivity.isPractice)
			undoGameBtn.setEnabled(false);
		
		//获取屏幕分辨率
		DisplayMetrics dm = new DisplayMetrics();   
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        
        //得到棋盘图片的5种大小的尺寸
        for (int i = 0; i < 5; i++){
        	scaleArray[i] = (int)((screenWidth-10) * Math.pow(1.25,i-2));
        }
        
        //布置好棋盘背景图片
        originalChessBoard = BitmapFactory.decodeResource(getResources(), R.drawable.chessboard); 
		resizeChessBoard = Bitmap.createScaledBitmap(originalChessBoard, scaleArray[2], scaleArray[2], true);
		chessboard.SetArea(5, screenWidth-5, 5, screenWidth-5);
		chessboard.setImageBitmap(resizeChessBoard);
        
		//画上棋盘线
		chessboard.ZoomOut();
        chessboard.invalidate(); //重新绘制棋盘
        
        Vector data = new Vector();
        chessboard.init(data, StartActivity.isPVE);
        if(StartActivity.isPVE && (!StartActivity.isFirst)){
        	chessboard.Last();
        }else{
        	chessboard.First();
        }
        
		//开始新游戏
		newGameBtn.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	if(StartActivity.isPVE && (!StartActivity.isFirst)){
		        	chessboard.Last();
		        }else{
		        	chessboard.First();
		        }
		    	chessboard.invalidate();
		    }
		});
		
		//悔棋
		undoGameBtn.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	chessboard.Back();
		    	chessboard.invalidate();
		    }
		});
		
		//游戏设置
		gameSettingBtn.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	Intent intent = new Intent(GameActivity.this,
						GameSettingActivity.class);
				startActivityForResult(intent, CODE);
		    }
		});
		
		//保存棋谱
				saveGameBtn.setOnClickListener(new View.OnClickListener() {
				    public void onClick(View v) {
				    	Vector data = chessboard.getData();
				    	int Size = data.size();
				    	for(int i = 0; i < Size; i++){
				    		
				    	}
				    }
				});
		  
		//返回主菜单
		returnmenuBtn.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	Intent intent = new Intent(GameActivity.this,
						StartActivity.class);
				startActivity(intent);
				finish();
		    }
		});
		
		//棋盘放大   
		zoomControls.setOnZoomInClickListener(new View.OnClickListener()   
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
            	chessboard.ZoomIn();
            	chessboard.invalidate();
            }   
        });
		
        //棋盘减小   
		zoomControls.setOnZoomOutClickListener(new View.OnClickListener()   
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
            	chessboard.ZoomOut();
            	chessboard.invalidate();
            }   
               
        }); 
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == CODE && resultCode == CODE){
			Bundle bundle = data.getExtras();
			soundOpen = bundle.getBoolean("soundOpen");
			vibrateOpen = bundle.getBoolean("vibrateOpen");
		}
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event){
		if(KeyEvent.KEYCODE_BACK == keyCode){
			Intent intent = new Intent(GameActivity.this,
					StartActivity.class);
			startActivity(intent);
			finish();
		}
		return true;
	}
}
