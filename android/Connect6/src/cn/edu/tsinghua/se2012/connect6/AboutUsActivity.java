package cn.edu.tsinghua.se2012.connect6;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class AboutUsActivity extends Activity{
	Button okBtn;
	
	@Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        //先去除应用程序标题栏  注意：一定要在setContentView之前  
        requestWindowFeature(Window.FEATURE_NO_TITLE);  
        //将我们定义的窗口设置为默认视图  
        setContentView(R.layout.aboutus);
        
        okBtn = (Button)findViewById(R.id.aboutusok);
        okBtn.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	finish();	 
		    }
		});
    }

	@Override
	protected void onDestroy() {
		super.onDestroy();
	} 
	
}
