package cn.edu.tsinghua.se2012.connect6;

import java.util.Vector;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.widget.ImageView;

@SuppressWarnings({ "deprecation", "rawtypes" })
public class ChessBoardView extends ImageView implements OnGestureListener {
	private int screenWidth;
	private int screenHeight;

	private Context context;
	private SoundPool soundpool;
	private GestureDetector mGestureDetector = new GestureDetector(this);

	private Vector data;
	private alg kernel;
	private int state;
	// 0 = 初始、游戏暂停状态
	// 1 = 用户还须下1子
	// 2 = 用户还须下2子
	private boolean pause;
	// 游戏是否处于暂停状态
	private int color;
	// 0 = 黑子
	// 1 = 白子
	// -1 = 空白
	private boolean computer;
	// 0 = 非人机对战
	// 1 = 人机对战

	private int mode;
	// 0 = 练习模式
	// 1 = 实战模式
	private boolean AIRunOnce = false; // 122012-MYP
	private boolean TellAIRunOnce = false;
	private boolean SoundOver = true;
	private boolean ComputerWin = false; // 122012-MYP

	private int[] cGridLen = { 10, 25, 30, 35, 40, 45 }; // 格子的长度
	private int[] cChessRadius = { 3, 8, 10, 12, 13, 15 };// 棋子的半径
	private int[] cSignRadius = { 2, 4, 5, 6, 7, 8 }; // 标志的半径
	private final int SIZE_COUNT = 5;
	private int X_MIN = 50, X_MAX = 590, Y_MIN = 90, Y_MAX = 630;
	private final int SIZE_X = 650, SIZE_Y = 680;
	private int CENTER_X = (X_MIN + X_MAX) / 2, CENTER_Y = (Y_MIN + Y_MAX) / 2;
	private int gridLen = 30; // the length of one grid;
	private int originX = X_MIN, originY = Y_MIN;
	private int boardSize = 19;
	private int chessRadius = 10; //
	private int signRadius = 3;
	private int currentSizeLevel = 1;

	// 构造函数
	public ChessBoardView(Context context) {
		super(context);
		this.context = context;
		soundpool = new SoundPool(1, AudioManager.STREAM_SYSTEM, 0);
	}

	public ChessBoardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		soundpool = new SoundPool(1, AudioManager.STREAM_SYSTEM, 0);
	}

	public ChessBoardView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		soundpool = new SoundPool(1, AudioManager.STREAM_SYSTEM, 0);
	}

	// 初始化
	public void init() {
		data = new Vector();
		kernel = new alg(data);
		state = 0;
		pause = false;
		computer = true;
	}

	// 棋谱初始化，computer是否人机对战
	public void init(Vector _Data, boolean _Computer) {
		data = _Data;
		kernel = new alg(data);
		state = 0;
		pause = false;
		computer = _Computer;
		if (data.size() != 0)
			pause = true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Paint(canvas);
		setWillNotDraw(false);
	}

	public void SetArea(int xmin, int xmax, int ymin, int ymax) {// 设置显示区域
		X_MIN = xmin;
		X_MAX = xmax;
		Y_MIN = ymin;
		Y_MAX = ymax;
		originX = X_MIN;
		originY = Y_MIN;
		CENTER_X = (X_MIN + X_MAX) / 2;
		CENTER_Y = (Y_MIN + Y_MAX) / 2;
		int minGrid = (X_MAX - X_MIN) * 10 / 186; // fix me for not follow the
													// boardSize
		for (int i = 0; i < 5; i++) {
			cGridLen[i] = i * minGrid / 2 + minGrid;
			cChessRadius[i] = cGridLen[i] * 3 / 10;
			cSignRadius[i] = cGridLen[i] / 5;
		}
	}

	// Some Helper Func
	private boolean CheckX(int x, int r) // 121812
	{
		return (x <= X_MAX + r) && (x >= X_MIN - r);
	}

	private boolean CheckY(int y, int r) {
		return (y <= Y_MAX + r) && (y >= Y_MIN - r);
	}

	private boolean CheckX(int x) {
		return (x <= X_MAX) && (x >= X_MIN);
	}

	private boolean CheckY(int y) {
		return (y <= Y_MAX) && (y >= Y_MIN);
	}

	private void DrawCircle(Canvas canvas, int x, int y, int r, int color) {
		Paint paint = new Paint();
		if (color == 0)
			paint.setColor(Color.BLACK);
		else
			paint.setColor(Color.WHITE);
		paint.setStyle(Style.FILL); // 填充
		canvas.drawCircle((float) x, (float) y, (float) r, paint);

	}

	private void DrawLine(Canvas canvas, int startX, int startY, int stopX,
			int stopY) {
		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		canvas.drawLine(startX, (float) startY, (float) stopX, (float) stopY,
				paint);
	}

	// plot the chess Sign loc
	private void PlotSign(int x, int y, Canvas canvas) {
		int newX = x * gridLen + originX;
		int newY = y * gridLen + originY;
		if (CheckX(newX, signRadius) && CheckY(newY, signRadius)) {
			// g.fillOval(newX - signRadius, newY - signRadius, ovalSize,
			// ovalSize);
			DrawCircle(canvas, newX, newY, signRadius, 0);
		}

	}

	private int Min(int a, int b) {
		return a > b ? b : a;
	}

	private int Max(int a, int b) {
		return a < b ? b : a;
	}

	private void VerifyOrigin() { // 121812
		int shiftLen = gridLen * 8 / 10;

		if (originX + gridLen * boardSize - gridLen <= X_MAX - shiftLen)
			originX = X_MAX - shiftLen + gridLen - gridLen * boardSize;
		if (originY + gridLen * boardSize - gridLen <= Y_MAX - shiftLen)
			originY = Y_MAX - shiftLen + gridLen - gridLen * boardSize;

		if (originX > X_MIN + shiftLen)
			originX = X_MIN + shiftLen;
		if (originY > Y_MIN + shiftLen)
			originY = Y_MIN + shiftLen;

		if (currentSizeLevel == 0) {
			originX = CENTER_X - gridLen * 9;
			originY = CENTER_Y - gridLen * 9;
		}
	}

	public void Paint(Canvas canvas) {
		// 绘制界面
		// 界面绘制
		// 122012-MYP-2
		if (ComputerWin) {
			// Fix me： The computer win, here need call a function to show the
			// msg" the computer win. "
			ComputerWin = false;
			displayGameResult(2);
		}
		if (AIRunOnce && SoundOver) {
			AIRunOnce = false;
			kernel.cal(1 - color);
			if (kernel.hadsix()) {
				ComputerWin = true;
				invalidate();
			} else {
				state = 2;
			}

		}
		if (TellAIRunOnce) {
			TellAIRunOnce = false;
			AIRunOnce = true;
			invalidate();
		}

		int i, Size;
		// MYP
		int newX, newY;
		int newXMin = Max(X_MIN, originX), newXMax = Min(X_MAX, originX
				+ gridLen * (boardSize - 1));
		int newYMin = Max(Y_MIN, originY), newYMax = Min(Y_MAX, originY
				+ gridLen * (boardSize - 1));

		mypoint p;
		for (i = 0; i < boardSize; i++) {
			newX = gridLen * i + originX;
			newY = gridLen * i + originY;
			if (CheckY(newY)) {
				DrawLine(canvas, newXMin, newY, newXMax, newY);
			}
			if (CheckX(newX)) {
				DrawLine(canvas, newX, newYMin, newX, newYMax);
			}
		}

		// 如果不是19x19 需要改变一下代码
		PlotSign(10 - 1, 10 - 1, canvas); // 绘制5个标志点
		PlotSign(4 - 1, 4 - 1, canvas);
		PlotSign(4 - 1, 16 - 1, canvas);
		PlotSign(16 - 1, 4 - 1, canvas);
		PlotSign(16 - 1, 16 - 1, canvas);

		Size = data.size(); // 绘制棋子
		for (i = 0; i < Size; i++) {
			p = (mypoint) data.elementAt(i);

			newX = gridLen * p.getx() + originX;
			newY = gridLen * p.gety() + originY;

			if (CheckX(newX, chessRadius) && CheckY(newY, chessRadius)) {
				DrawCircle(canvas, newX, newY, chessRadius, p.getcolor());
			}

		}

	}

	public void ZoomIn() {
		if (currentSizeLevel < SIZE_COUNT - 1) {
			currentSizeLevel++;
			originX = (originX - CENTER_X) * cGridLen[currentSizeLevel]
					/ gridLen + CENTER_X;
			originY = (originY - CENTER_Y) * cGridLen[currentSizeLevel]
					/ gridLen + CENTER_Y;

			gridLen = cGridLen[currentSizeLevel];
			signRadius = cSignRadius[currentSizeLevel];
			chessRadius = cChessRadius[currentSizeLevel];
			VerifyOrigin();
		}
	}

	public void ZoomOut() {
		if (currentSizeLevel > 0) {
			currentSizeLevel--;
			originX = (originX - CENTER_X) * cGridLen[currentSizeLevel]
					/ gridLen + CENTER_X;
			originY = (originY - CENTER_Y) * cGridLen[currentSizeLevel]
					/ gridLen + CENTER_Y;
			gridLen = cGridLen[currentSizeLevel];
			signRadius = cSignRadius[currentSizeLevel];
			chessRadius = cChessRadius[currentSizeLevel];
			VerifyOrigin();
		}
	}

	public void First() {// 先手
		data.clear();
		state = 1;
		color = 0;
		pause = false;
	}

	public void Last() {// 后手
		data.clear();
		data.add(new mypoint(9, 9, 0));
		state = 2;
		color = 1;
	}

	public void Restart() {// 重新开始
		int p;
		int ccolor;
		if (computer) {
			p = data.size();
			if (p % 2 != 0) {
				ccolor = 1 - ((mypoint) data.elementAt(p - 1)).getcolor();
			} else {
				ccolor = 1 - ((mypoint) data.elementAt(p - 2)).getcolor();
			}

			if (ccolor == 0) {
				data.clear();
				state = 1;
				color = 0;
			} else {
				data.clear();
				data.add(new mypoint(9, 9, 0));
				state = 2;
				color = 1;
			}
		} else {
			data.clear();
			state = 1;
			color = 0;
		}
	}

	public void Reset() {
		data.clear();
		state = 0;
		mode = 0;
		pause = false;
		computer = true;
	}

	public void Comp(boolean _computer) {// 切换人机对战
		state = 0;
		data.clear();
		computer = _computer;
	}

	public void Prac() {// 切换练习比赛模式
		data.clear();
		state = 0;
		pause = false;
		mode = 1 - mode;

	}

	public void Back() {// 悔棋
		int i, Size;
		Size = data.size();
		if (Size == 0) {
			return;
		}

		if (!computer) {
			data.remove(Size - 1);
			Size--;
			if (Size == 0) {
				color = 1 - color;
				state = 1;
			} else if (Size % 2 == 0) {
				state = 1;
				color = ((mypoint) data.elementAt(Size - 1)).getcolor();
			} else {
				state = 2;
				color = 1 - ((mypoint) data.elementAt(Size - 1)).getcolor();
			}
		} else {
			if (Size == 1) { 
				return;
			}
			if (Size % 2 == 0) {
				data.remove(Size - 1);
				state++;
			} else if (Size == 3) {
				data.clear();
				state = 1;
			} else {
				data.remove(Size - 1);
				data.remove(Size - 2);
				data.remove(Size - 3);
				data.remove(Size - 4);
				state = 2;
			}
		}
		/*
		 * if (data.size() <= 1) { pause = false; } else { pause = true; state =
		 * 0; }
		 */

	}

	public void Interrupt() {// 暂停游戏
		if (!pause) {
			state = 0;
			pause = true;
		} else {
			int Size;
			Size = data.size();
			if (Size % 2 == 0) {
				state = 1;
				color = ((mypoint) data.elementAt(Size - 1)).getcolor();
			} else {
				state = 2;
				color = 1 - ((mypoint) data.elementAt(Size - 1)).getcolor();
			}

			pause = false;
		}
	}

	public void MoveChessBoard(int x, int y) {
		if (currentSizeLevel == 0)
			return;
		originX = originX + x;
		originY = originY + y;
		VerifyOrigin();
	}

	private void PlotLastTwoChess(Canvas canvas) {
		int ovalSize = 2 * chessRadius + 1;
		for (int i = data.size() - 2; i < data.size(); i++) {
			mypoint p = (mypoint) data.elementAt(i);

			int newX = gridLen * p.getx() + originX;
			int newY = gridLen * p.gety() + originY;

			if (CheckX(newX) && CheckY(newY)) {
				DrawCircle(canvas, newX, newY, chessRadius, p.getcolor());
			}

		}

	}

	public void Open() {
		int Size;
		Size = data.size();

		if (Size % 2 == 0) {
			state = 1;
			color = ((mypoint) data.elementAt(Size - 1)).getcolor();
		} else {
			state = 2;
			color = 1 - ((mypoint) data.elementAt(Size - 1)).getcolor();
		}
        
		if (kernel.hadsix())
			state = 0;
		pause = false;

	}

	public int PlaceChess(int mouseX, int mouseY, Canvas canvas) {
		// / 0 --- do nothing
		// / 1 --- "恭喜你战胜了电脑！！！"
		// / 2 --- "电脑获得胜利！！！"
		// / 3 --- "黑方获胜！！！"
		// / 4 --- "白方获胜！！！"
		// / menu_restart.setEnabled(true);
		// / menu_int.setEnabled(true);
		// / if (mode == 0)
		// /{
		// / menu_back.setEnabled(true);
		// /}
		// if (!CheckX(mouseX) || !CheckY(mouseY))
		// return 0;
		if (state == 0 || pause)
			return 0;
		int result = 0;
		int x, y;
		// MYP 这里是粗糙的处理

		x = (mouseX - originX) * 30 / gridLen + 50;
		y = (mouseY - originY) * 30 / gridLen + 90;
		// MYP
		if (((x - 35) % 30 > 28) || ((x - 35) % 30 < 1) || ((y - 75) % 30 > 28)
				|| ((y - 75) % 30 < 1)) {
			return 0;
		}

		x = (x - 35) / 30;
		y = (y - 75) / 30;
		if ((x < 0) || (x > 18) || (y < 0) || (y > 18)) {
			return 0;
		}

		int i, Size;
		Size = data.size();
		for (i = 0; i < Size; i++)
			// 重复下子
			if ((((mypoint) data.elementAt(i)).getx() == x)
					&& (((mypoint) data.elementAt(i)).gety() == y)) {
				return 0;
			}

		// MYP
		// int ovalSize = 2 * chessRadius + 1;
		DrawCircle(canvas, originX + gridLen * x, originY + gridLen * y,
				chessRadius, color);
		// g.fillOval(originX + gridLen * x - chessRadius, originY + gridLen * y
		// - chessRadius, ovalSize, ovalSize);
		// MYP

		mypoint p;
		p = new mypoint(x, y, color);
		data.add(p);
		playSound();
		state--;
		// 122012-MYP
		// if (computer && state == 0){
		// invalidate();
		// //122012-MYP

		// return 0;
		// 122012-MYP
		// try {
		// Thread myThread = new Thread();
		// myThread.setPriority(Thread.MAX_PRIORITY);
		// myThread.sleep(1000);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// }
		if (computer) {
			if (kernel.hadsix()) {
				result = 1;
				state = 0;

			} else if (state == 0) {

				TellAIRunOnce = true; // 122012-MYP

			}
		} else {
			if (kernel.hadsix()) {
				if (color == 0)
					result = 3;
				else
					result = 4;
				state = 0;
			} else if (state == 0) {
				state = 2;
				color = 1 - color;
			}
		}

		return result;
	}

	public Vector getData() {
		return data;
	}

	public int getSIZE_X() {
		return SIZE_X;
	}

	public int getSIZE_Y() {
		return SIZE_Y;
	}

	public boolean getComputer() {
		return computer;
	}

	public int getMode() {
		return mode;
	}

	public boolean getPause() {
		return pause;
	}

	public int getState() {
		return state;
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public int getCurrentSizeLevel() {
		return currentSizeLevel;
	}

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}

	public boolean onTouchEvent(MotionEvent event) {
		mGestureDetector.onTouchEvent(event);
		/*
		 * int action = event.getAction(); int PosX = (int) event.getX(); int
		 * PosY = (int) event.getY(); switch(action){ case
		 * MotionEvent.ACTION_DOWN: start_x = PosX; start_y = PosY; down_x =
		 * PosX; down_y = PosY; break; case MotionEvent.ACTION_MOVE: delta_x =
		 * PosX - start_x; delta_y = PosY - start_y; start_x = PosX; start_y =
		 * PosY; MoveChessBoard(delta_x, delta_y); invalidate(); break; case
		 * MotionEvent.ACTION_UP: if(((down_x - PosX) * (down_x - PosX) <
		 * Zero)&& ((down_y - PosY) * (down_y - PosY) < Zero)){ Canvas canvas =
		 * new Canvas(); PlaceChess(PosX,PosY, canvas); invalidate(); } }
		 */
		return true;
	}

	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	public void onLongPress(MotionEvent arg0) {
		// TODO Auto-generated method stub

	}

	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		MoveChessBoard((int) (0 - arg2), (int) (0 - arg3));
		invalidate();
		return false;
	}

	public void onShowPress(MotionEvent arg0) {
		// TODO Auto-generated method stub

	}

	public boolean onSingleTapUp(MotionEvent arg0) {
		int result = PlaceChess((int) arg0.getX(), (int) arg0.getY(), new Canvas());
		if (result !=0){
			displayGameResult(result);
		}
		invalidate();
		if ((0 == data.size()) || ((1 == data.size()) && StartActivity.isPVE)) {
			GameActivity.undoGameBtn.setEnabled(false);
			GameActivity.newGameBtn.setEnabled(false);
			GameActivity.saveGameBtn.setEnabled(false);
			GameActivity.undoEnable = false;
		} else {
			GameActivity.undoGameBtn.setEnabled(true);
			GameActivity.newGameBtn.setEnabled(true);
			GameActivity.saveGameBtn.setEnabled(true);
			GameActivity.undoEnable = true;
		}
		if (!StartActivity.isPractice)
			GameActivity.undoGameBtn.setEnabled(false);
		return false;
	};

	// 播放下子声音
	public void playSound() {
	    SoundOver = false;
		if (StartActivity.soundOpen) {
			final int sourceId = soundpool.load(this.context, R.raw.chesssound,
					1);
			soundpool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
						public void onLoadComplete(SoundPool soundPool,
								int sampleId, int status) {
							soundPool.play(sourceId, 1, 1, 0, 0, 1);
							SoundOver = true;
							invalidate();
						}
					});
		}
	}
	
	public void displayGameResult(int result){
		Intent intent = new Intent(context, GameResultActivity.class);
		Bundle bundle = new Bundle();
		bundle.putInt("result", result);
		intent.putExtras(bundle);
		context.startActivity(intent);
	}
}
