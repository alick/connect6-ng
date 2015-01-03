/** @file MusicPlayer.java 
 * @brief  音乐播放模块
 * @author 侯奇
 * @author 卢嘉勋
 * @author 刘菁菁
 * @date 2013-12-30
 * 
 */
package connect6ng;

import java.applet.AudioClip;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

/**
 * @brief 音效模块，负责背景音乐的播放
 * 
 * 对外提供接口，负责音乐的播放
 */
public class MusicPlayer {
	/// 背景音乐
	private AudioClip background;
	/// 结束提示音
	private AudioClip [] gameover;
	/// 落子提示音
	private AudioClip [] chess_down;
	/// 错误提示音
	private AudioClip error_tip;

	/**
	 * @brief 构造函数
	 * @param state 音效的开关状态
	 * 
	 * 	on	：	开启声音
	 * 	off	：	关闭声音
	 * 
	 * 创建一个音乐播放器，并初始化状态为state
	 */
	public MusicPlayer(String state) {
		URL file;
		try {
			file = new File("res/music/background.wav").toURI().toURL();
			background = java.applet.Applet.newAudioClip(file); 
			
			file = new File("res/music/error_tip.wav").toURI().toURL();
			error_tip = java.applet.Applet.newAudioClip(file);
			
			gameover = new AudioClip[2];
			file = new File("res/music/win_game.wav").toURI().toURL();
			gameover[0] = java.applet.Applet.newAudioClip(file); 
			file = new File("res/music/lose_game.wav").toURI().toURL();
			gameover[1] = java.applet.Applet.newAudioClip(file); 
			
			chess_down = new AudioClip[8];
			for(int i = 1 ; i <= 8 ; i++ ){
				String file_name = "res/music/00" + String.valueOf(i) + ".wav";
				file = new File(file_name).toURI().toURL();
				chess_down[i-1] = java.applet.Applet.newAudioClip(file);
			}
			// System.out.println("音乐开始播放");
			if (state.equals("on")) {
				background.loop();
			}else {
				background.stop();// 其他情况音乐不播放
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			pushErrorDialog("音乐资源加载错误");
		}
	}
	
	/**
	 * @brief 设置播放状态为state
	 * @param state	 音效的开关状态
	 * 
	 * 	on	：	开启声音
	 * 	off	：	关闭声音
	 */
	public void setState(String state){
		state = state.trim();
		
		if( state.equals("on") ){
			background.loop();
		}else{
			background.stop();
		}
	}
	
	/** @brief 播放音乐，type为音效类型
	 * 
	 * @param type 提示音的类型
	 * 
	 * 	<0 ： 错误提示音
	 * 	0 ： 胜利音
	 * 	1 ： 失败音
	 * 	2-9 ： 落子音
	 */
	public void playSound(int type){
		if( type < 0 ){
			error_tip.play();
		}else if( type <= 1 ){
			gameover[type].play();
		}else if( type <= 9 ){
			chess_down[ type - 2 ].play();
		}else{
//			System.out.println("错误的音乐播放类型" + type);
			Flogger.getLogger().log(Level.SEVERE, "错误的音乐播放类型" + type);
		}
	}
	
	/**
	 * @brief 输出错误提示
	 * @param errMsg 错误信息
	 */
	private void pushErrorDialog(String errMsg){
		final JDialog myDialog = new JDialog();
		myDialog.setTitle("错误提示");
		myDialog.setSize(180, 100);
		myDialog.setLayout(new FlowLayout(FlowLayout.CENTER, 1000, 10));
		myDialog.add(new JLabel(errMsg));
		JButton myDialogBotton = new JButton("确定");
		myDialog.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				myDialog.dispose();
			}
		});
		myDialog.add(myDialogBotton);
		myDialog.setLocationRelativeTo(null);
		myDialog.setResizable(false);
		myDialogBotton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myDialog.dispose();
			}
		});
		myDialog.setVisible(true);
	}

}
