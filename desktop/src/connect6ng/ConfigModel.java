/** @brief 管理配置文件类
 * @file ConfigModel.java
 * @author 侯奇
 * @author 卢嘉勋
 * @author 刘菁菁
 * @date 2014-01-01
 * 
 * 管理配置文件
 * 当前用来管理音效
 */
package connect6ng;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;

/** @brief 管理配置文件类
 */
public class ConfigModel {
	private String musicState;
	
	/** @brief 构造函数
	 */
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
	
	/** @brief 设置音效开关
	 * 
	 * @param state 音效的开关
	 */
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
			pro.setProperty("music", state);
			pro.store(new FileOutputStream("./config/configure"), "music");
			System.out.println("state : " + state);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** @brief 赶回音效的结果
	 * 
	 * @return 音效的开关
	 */
	public String getMusicState(){
		return musicState;
	}
}
