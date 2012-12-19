package cn.edu.tsinghua.se2012.connect6;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class MyLayout extends RelativeLayout{

	public MyLayout(Context context) {
		super(context);
	}
	
	public MyLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent e){
		return false;
	}

}
