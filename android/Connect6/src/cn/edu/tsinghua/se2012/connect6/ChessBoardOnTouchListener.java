package cn.edu.tsinghua.se2012.connect6;

import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class ChessBoardOnTouchListener implements OnTouchListener {
	private PointF startPoint = new PointF();

	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			startPoint.set(event.getX(), event.getY());// 开始点
			break;

		case MotionEvent.ACTION_MOVE:// 移动事件
			// 图片拖动事件
			float dx = event.getX() - startPoint.x;// x轴移动距离
			float dy = event.getY() - startPoint.y;
			break;
		case MotionEvent.ACTION_UP:
			break;
		// 有手指离开屏幕，但屏幕还有触点(手指)
		case MotionEvent.ACTION_POINTER_UP:
			break;
		// 当屏幕上已经有触点（手指）,再有一个手指压下屏幕
		case MotionEvent.ACTION_POINTER_DOWN:
			break;
		}
		return true;
	}

}
