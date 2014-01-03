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
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

/**
 * @brief MusicPlayer : 音效模块
 * 
 *        负责背景音乐的播放
 */
public class MusicPlayer {
	// / 声音流
	public AudioClip background;
	public AudioClip [] gameover;
	public AudioClip [] chess_down;
	public AudioClip error_tip;

	/**
	 * 
	 * @param state
	 *            播放音乐文件名 *.wav
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
			System.out.println(state);
			if (state.equals("off")) {
				background.loop();
			}else {
				background.stop();// 其他情况音乐不播放
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			pushErrorDialog("音乐资源加载错误");
		}
	}
	
	public void setState(String state){
		state = state.trim();
		
		if( state.equals("on") ){
			background.loop();
		}else{
			background.stop();
		}
	}
	
	/** 播放音乐
	 * 
	 * @param type
	 * 	negative for error tip
	 * 	0 for win
	 *  1 for lose
	 *  2-9 for chess down
	 */
	public void playSound(int type){
		if( type == -1 ){
			error_tip.play();
		}else if( type <= 1 ){
			gameover[type].play();
		}else if( type <= 9 ){
			chess_down[ type - 2 ].play();
		}else{
			System.out.println("错误的音乐播放类型");
		}
	}
	
	public void pushErrorDialog(String errMsg){
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
