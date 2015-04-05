/*
 * Copyright 2012 Shuyang Jiang, Yipeng Ma and Bo Liu
 * 
 * This file is part of Connect6.

   Connect6 is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   Connect6 is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with Connect6.  If not, see <http://www.gnu.org/licenses/>.
 */

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

/**
 * 棋盘界面
 * 
 * @version 1.0
 * @author Shuyang Jiang, Yipeng Ma and Bo Liu
 *
 */
@SuppressWarnings({ "deprecation", "rawtypes", "unchecked"})
public class ChessBoardView extends ImageView implements OnGestureListener {
	private int screenWidth;
	private int screenHeight;

	private Context context;
	private SoundPool soundpool;
	private GestureDetector mGestureDetector = new GestureDetector(this);

	private Vector data;
	private alg kernel;
	private int state;
	// 0 = ��ʼ����Ϸ��ͣ״̬
	// 1 = �û�������1��
	// 2 = �û�������2��
	private boolean pause;
	// ��Ϸ�Ƿ�����ͣ״̬
	private int color;
	// 0 = ����
	// 1 = ����
	// -1 = �հ�
	private boolean computer;
	// 0 = ���˻���ս
	// 1 = �˻���ս

	private int mode;
	// 0 = ��ϰģʽ
	// 1 = ʵսģʽ
	private boolean AIRunOnce = false; // 122012-MYP
	private boolean TellAIRunOnce = false;
	private boolean SoundOver = true;
	private boolean ComputerWin = false; // 122012-MYP

	private int[] cGridLen = { 10, 25, 30, 35, 40, 45 }; // ���ӵĳ���
	private int[] cChessRadius = { 3, 8, 10, 12, 13, 15 };// ���ӵİ뾶
	private int[] cSignRadius = { 2, 4, 5, 6, 7, 8 }; // ��־�İ뾶
	private final int SIZE_COUNT = 5;
	private int X_MIN = 50, X_MAX = 590, Y_MIN = 90, Y_MAX = 630;
	private final int SIZE_X = 650, SIZE_Y = 680;
	private int CENTER_X = (X_MIN + X_MAX) / 2, CENTER_Y = (Y_MIN + Y_MAX) / 2;
	private int gridLen = 30; // the length of one grid;
	private int originX = X_MIN, originY = Y_MIN;
	private int boardSize = 19;
	private int chessRadius = 10; //
	private int signRadius = 3;
	private int currentSizeLevel = 0;

	// ���캯��
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

	// ��ʼ��
	public void init() {
		data = new Vector();
		kernel = new alg(data);
		state = 0;
		pause = false;
		computer = true;
	}

	// ���׳�ʼ����computer�Ƿ��˻���ս
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

	public void SetArea(int xmin, int xmax, int ymin, int ymax) {// ������ʾ����
		X_MIN = xmin;
		X_MAX = xmax;
		Y_MIN = ymin;
		Y_MAX = ymax;
		originX = X_MIN;
		originY = Y_MIN;
		CENTER_X = (X_MIN + X_MAX) / 2;
		CENTER_Y = (Y_MIN + Y_MAX) / 2;
		// Fix ME: check 18.6 or 19.6, which is appropriate  back_up version is 186
		int minGrid = (X_MAX - X_MIN) * 10 / 190; 
		for (int i = 0; i < 5; i++) {
			cGridLen[i] = i * minGrid / 2 + minGrid;
			cChessRadius[i] = cGridLen[i] * 3 / 10;
			cSignRadius[i] = cGridLen[i] / 5;
		}
		gridLen = cGridLen[currentSizeLevel];
		VerifyOrigin();
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
		paint.setStyle(Style.FILL); // ���
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

	private void VerifyOrigin() { // to check the originX and originY is all legal
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
		// ���ƽ���
		if (ComputerWin) {
			ComputerWin = false;
			displayGameResult(2);  // disp the computer win msg
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

		// �������19x19 ��Ҫ�ı�һ�´���
		PlotSign(10 - 1, 10 - 1, canvas); // ����5����־��
		PlotSign(4 - 1, 4 - 1, canvas);
		PlotSign(4 - 1, 16 - 1, canvas);
		PlotSign(16 - 1, 4 - 1, canvas);
		PlotSign(16 - 1, 16 - 1, canvas);

		Size = data.size(); // ��������
		for (i = 0; i < Size; i++) {
			p = (mypoint) data.elementAt(i);

			newX = gridLen * p.getx() + originX;
			newY = gridLen * p.gety() + originY;

			if (CheckX(newX, chessRadius) && CheckY(newY, chessRadius)) {
				DrawCircle(canvas, newX, newY, chessRadius, p.getcolor());
			}

		}

	}

	public void ZoomIn() { // zoomIn the chessBoard
		if (currentSizeLevel < SIZE_COUNT - 1) { // Not the biggest chessBoard
			currentSizeLevel++;                  // increment sizeLevel by 1
			
			// calc the new (originX, originY) and guarantee the (CenterX, CenterY) won't change
			originX = (originX - CENTER_X) * cGridLen[currentSizeLevel]
					/ gridLen + CENTER_X; 
			originY = (originY - CENTER_Y) * cGridLen[currentSizeLevel]
					/ gridLen + CENTER_Y;
					
			// load the new gridLen, the Radius of five sign, the radius of chess
			gridLen = cGridLen[currentSizeLevel];
			signRadius = cSignRadius[currentSizeLevel];
			chessRadius = cChessRadius[currentSizeLevel];
			
			VerifyOrigin();    // Verify the (originX, originY) is legal
		}
	}

	public void ZoomOut() { // zoomOut the chessBoard
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

	public void First() {// ����
		data.clear();
		state = 1;
		color = 0;
		pause = false;
	}

	public void Last() {// ����
		data.clear();
		data.add(new mypoint(9, 9, 0));
		state = 2;
		color = 1;
	}

	public void Restart() {// ���¿�ʼ
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

	public void Comp(boolean _computer) {// �л��˻���ս
		state = 0;
		data.clear();
		computer = _computer;
	}

	public void Prac() {// �л���ϰ����ģʽ
		data.clear();
		state = 0;
		pause = false;
		mode = 1 - mode;

	}

	public void Back() {// ����
		int Size;
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
	}

	public void Interrupt() {// ��ͣ��Ϸ
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

	public void MoveChessBoard(int x, int y) {  // move the chess board according gesture
		if (currentSizeLevel == 0)
			return;
		originX = originX + x;
		originY = originY + y;
		VerifyOrigin();
	}

//	private void PlotLastTwoChess(Canvas canvas) {  //plot the ai two chess
//		int ovalSize = 2 * chessRadius + 1;
//		for (int i = data.size() - 2; i < data.size(); i++) {
//			mypoint p = (mypoint) data.elementAt(i);
//
//			int newX = gridLen * p.getx() + originX;
//			int newY = gridLen * p.gety() + originY;
//
//			if (CheckX(newX) && CheckY(newY)) {
//				DrawCircle(canvas, newX, newY, chessRadius, p.getcolor());
//			}
//
//		}
//
//	}

	public void Open() {   // adjust the state and color if open action occured
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
		// / 1 --- "��ϲ��սʤ�˵��ԣ�����"
		// / 2 --- "���Ի��ʤ��������"
		// / 3 --- "�ڷ���ʤ������"
		// / 4 --- "�׷���ʤ������"
		if (state == 0 || pause)
			return 0;
		int result = 0;
		int x, y;
		// resize as gridLen = 30, originX = 50, originY = 90
		x = (mouseX - originX) * 30 / gridLen + 50;
		y = (mouseY - originY) * 30 / gridLen + 90;
		// if the point to close to the mid of two line, skip it 
		if (((x - 35) % 30 > 28) || ((x - 35) % 30 < 1) || ((y - 75) % 30 > 28)
				|| ((y - 75) % 30 < 1)) {
			return 0;
		}
		x = (x - 35) / 30;
		y = (y - 75) / 30;
		if ((x < 0) || (x > 18) || (y < 0) || (y > 18)) {   // skip the illeagal chess
			return 0;
		}

		int i, Size;
		Size = data.size();
		for (i = 0; i < Size; i++)
			// �ظ�����
			if ((((mypoint) data.elementAt(i)).getx() == x)
					&& (((mypoint) data.elementAt(i)).gety() == y)) {
				return 0;
			}
        //Draw the chess
		DrawCircle(canvas, originX + gridLen * x, originY + gridLen * y,
				chessRadius, color);
		//Save the chess action
		mypoint p;
		p = new mypoint(x, y, color);
		data.add(p);
		playSound();
		state--;
		if (computer) {
			if (kernel.hadsix()) {
				result = 1;
				state = 0;
			} else if (state == 0) {
				TellAIRunOnce = true;  // inform the AI to run, after show the new chess board
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
    // some get and set function
	public Vector getData() {
		return data;
	}

	public int getSizeCount() {
	    return SIZE_COUNT;
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
		return true;
	}

	public boolean onDown(MotionEvent arg0) {
		return false;
	}

	public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		return false;
	}

	public void onLongPress(MotionEvent arg0) {
	}

	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		MoveChessBoard((int) (0 - arg2), (int) (0 - arg3));
		invalidate();
		return false;
	}

	public void onShowPress(MotionEvent arg0) {
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

	// ������������
	public void playSound() {
	    SoundOver = false;               // indicate cpu is load the sound
		if (StartActivity.soundOpen) {
			final int sourceId = soundpool.load(this.context, R.raw.chesssound,
					1);
			soundpool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
						public void onLoadComplete(SoundPool soundPool,
								int sampleId, int status) {
							soundPool.play(sourceId, 1, 1, 0, 0, 1);
							SoundOver = true;    // the sound is over, now we can run ai if need
							invalidate();
						}
					});
		}
	}
	
	public void displayGameResult(int result){    // show the game result
		Intent intent = new Intent(context, GameResultActivity.class);
		Bundle bundle = new Bundle();
		bundle.putInt("result", result);
		intent.putExtras(bundle);
		context.startActivity(intent);
	}
}
