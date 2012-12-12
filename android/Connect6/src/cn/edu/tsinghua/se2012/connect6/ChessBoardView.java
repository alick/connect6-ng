package cn.edu.tsinghua.se2012.connect6;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

public class ChessBoardView extends ImageView{
	private int screenWidth;
	private int screenHeight;
	
	//构造函数
	public ChessBoardView(Context context){
		super(context);
	}	
	public ChessBoardView(Context context, AttributeSet attrs){
		super(context, attrs);
	}
	public ChessBoardView(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
	}
	
	@Override
	protected void onDraw(Canvas canvas){
		super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(2);
        //画出棋盘线
        canvas.drawLine(6, screenHeight/2, 6, screenHeight/2+10, paint);
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

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}
}
