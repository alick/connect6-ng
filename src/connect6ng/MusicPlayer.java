/**@brief MusicPlayer : 音效模块
 * @file __FILE__
 * @author 侯奇
 * @author 汪翔
 * @date 2013年12月16日
 * @version v0.1.0
 * 
 * 负责背景音乐的播放
 */
package connect6ng;
import java.applet.AudioClip;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @brief MusicPlayer : 音效模块
 * 
 *        负责背景音乐的播放
 */
public class MusicPlayer {
	/// 声音流
	public AudioClip sound;
	/**
	 * 
	 * @param state
	 * 			开关状态: play_loop 循环播放，play_once 播放一次，pause 暂停 ，其他状态 无效、不播放
	 * @param filename
	 * 			播放音乐文件名 *.wav
	 */
	public MusicPlayer(String state , String filename){
		URL file;
		try {
			file = new File("res/music/" + filename).toURI().toURL();
			sound = java.applet.Applet.newAudioClip(file); // 声音剪辑对象1
			System.out.println("音乐开始播放");
			System.out.println(state);
			if (state.equals("play_loop")) {
				sound.loop();
			} 
			else if(state.equals("play_once"))
			{
				sound.play();
			}
			else if(state.equals("pause")){
				sound.stop();
			}
			else
			{
				//其他情况音乐不播放
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("failed to play Music " + filename);
			
		}
	}
	/**
	 * 只适用于开关声音
	 * @param state 开关状态
	 * 
	 */
	public MusicPlayer(String state){
		if(sound != null)
		{
			if (state.equals("play_loop")) {
				sound.loop();
			} 
			else if(state.equals("play_once"))
			{
				sound.play();
			}
			else if(state.equals("pause")){
				sound.stop();
			}
			else
			{
				//其他情况音乐不播放
			}
		}
	}
}
