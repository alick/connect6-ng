package connect6ng;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;

public class ConfigModel {
	private String musicState;
	
	public ConfigModel(){
		Properties pro = new Properties();
		try {
			pro.load(new FileInputStream("./config/configure"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		musicState = pro.getProperty("music");
		musicState = musicState.trim();
	}
	
	public void setMusic(String state){
		state = state.trim();
		if( state.equals("on") || state.equals("off") ){
			musicState = state;
		}else{
			Flogger.getLogger().log(Level.SEVERE , "音乐设置错误");
			return;
		}		

		Properties pro = new Properties();
		try {
			pro.load(new FileInputStream("./config/configure"));
			pro.store(new FileOutputStream("./config/configure"), "music");
			pro.setProperty("music", state);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getMusicState(){
		return musicState;
	}
}
