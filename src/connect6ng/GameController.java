/**
 * @file __FILE__
 * @brief MVC 架构的controller结构
 * @author 侯奇
 * @author 卢嘉勋
 * @author 刘菁菁
 * @date 2014-01-01
 * @version v 1.0.0
 * 
 * 作为MVC 架构的controller，用来处理和分发事件
 */
package connect6ng;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.logging.*;

import javax.swing.*;
/** @brief MVC 架构的controller结构
 * 
 * 用来管理程序的数据和视图
 */
@SuppressWarnings("serial")
class GameController extends JFrame {
	/// 配置文件的Model
	ConfigModel config_model;
	/// 配置文件的 View
	ConfigView config_view;
	/// 游戏数据的Model
	GameModel game_model;
	/// 游戏数据的View
	GameView game_view;

	/// 音乐播放模块
	MusicPlayer music_player;

	/// AI模块
	Alg kernel;

	// 菜单栏
	JMenuBar menu_JMenuBar = new JMenuBar();
	JMenu menu_game = new JMenu("游戏");
	JMenu menu_ngame = new JMenu("开始新游戏");
	JMenuItem menu_first = new JMenuItem("先手(玩家黑棋先行)");
	JMenuItem menu_last = new JMenuItem("后手(玩家白棋后行)");
	JMenuItem menu_music = new JMenuItem("音效开/关");
	JMenuItem menu_hist = new JMenuItem("历史记录");
	JCheckBoxMenuItem menu_comp = new JCheckBoxMenuItem("人机对战");
	JMenuItem menu_prac = new JMenuItem("切换为实战模式");
	JMenuItem menu_exit = new JMenuItem("退出");
	JMenuItem menu_back = new JMenuItem("悔棋");
	JMenu menu_file = new JMenu("游戏战绩");
	JMenuItem menu_open = new JMenuItem("打开棋局");
	JMenuItem menu_save = new JMenuItem("保存棋局");

	// 帮助：
	JMenu menu_help = new JMenu("帮助");
	JMenuItem menu_AboutSixChess = new JMenuItem("关于六子棋");
	JMenuItem menu_SeekHelp = new JMenuItem("查看帮助");
	JMenuItem menu_CheckUpdate = new JMenuItem("检查更新");

	/** @brief 构造函数
	 * 
	 * 构造函数中初始化菜单栏、游戏的显示区域栏等，以及音乐模块等
	 */
	GameController() {
		super("Connect 6 - 游戏尚未开始 - 练习模式");
		setSize(622, 665);
		setResizable(false);

		// 初始化数据
		game_model = new GameModel();

		kernel = new Alg(game_model.getChessmans());

		// 菜单栏及按钮监听
		setJMenuBar(menu_JMenuBar);
		menu_JMenuBar.add(menu_game);
		menu_game.add(menu_ngame);
		
		// first turn (black chess)
		menu_ngame.add(menu_first);
		menu_first.addActionListener(new ack_menu_first());
		menu_first.setAccelerator(KeyStroke.getKeyStroke('F',
				java.awt.Event.CTRL_MASK, false));

		// last turn (white chess)
		menu_ngame.add(menu_last);
		menu_last.addActionListener(new ack_menu_last());
		menu_last.setAccelerator(KeyStroke.getKeyStroke('L',
				java.awt.Event.CTRL_MASK, false));

		// rival : computer or person
		menu_game.add(menu_comp);
		menu_comp.addItemListener(new ack_menu_comp());
		menu_comp.setState(true);
		menu_comp.setAccelerator(KeyStroke.getKeyStroke('C',
				java.awt.Event.CTRL_MASK, false));
		
		// practice or play
		menu_game.add(menu_prac);
		menu_prac.addActionListener(new ack_menu_prac());
		menu_prac.setAccelerator(KeyStroke.getKeyStroke('P',
				java.awt.Event.CTRL_MASK, false));

		// back
		menu_game.add(menu_back);
		menu_back.addActionListener(new ack_menu_back());
//		menu_back.setEnabled(false);
		menu_back.setAccelerator(KeyStroke.getKeyStroke('B',
				java.awt.Event.CTRL_MASK, false));
		
		// music on/off
		menu_game.add(menu_music);
		menu_music.addActionListener(new ack_menu_music());
		menu_music.setAccelerator(KeyStroke.getKeyStroke('M',
				java.awt.Event.CTRL_MASK, false));
		
		// show history
		menu_game.add(menu_hist);
		menu_hist.addActionListener(new ack_menu_hist() );
		menu_hist.setAccelerator(KeyStroke.getKeyStroke('H',
				java.awt.Event.CTRL_MASK, false));
		
		// exit
		menu_game.add(menu_exit);
		menu_exit.addActionListener(new ack_menu_exit());
		menu_exit.setAccelerator(KeyStroke.getKeyStroke('E',
				java.awt.Event.CTRL_MASK, false));
		
		// file
		menu_JMenuBar.add(menu_file);
		
		// open 
		menu_file.add(menu_open);
		menu_open.addActionListener(new ack_menu_open());
		menu_open.setAccelerator(KeyStroke.getKeyStroke('O',
				java.awt.Event.CTRL_MASK, false));
		
		// save
		menu_file.add(menu_save);
		menu_save.addActionListener(new ack_menu_save());
		menu_save.setAccelerator(KeyStroke.getKeyStroke('S',
				java.awt.Event.CTRL_MASK, false));

		// 帮助：
		menu_JMenuBar.add(menu_help);

		menu_help.add(menu_AboutSixChess);
		menu_AboutSixChess.addActionListener(new ack_menu_AboutSixChess());
		menu_AboutSixChess.setAccelerator(KeyStroke.getKeyStroke('A',
				java.awt.Event.CTRL_MASK, false));

		menu_help.add(menu_SeekHelp);
		menu_SeekHelp.addActionListener(new ack_menu_SeekHelp());
		menu_SeekHelp.setAccelerator(KeyStroke.getKeyStroke('H',
				java.awt.Event.CTRL_MASK, false));

		menu_help.add(menu_CheckUpdate);
		menu_CheckUpdate.addActionListener(new ack_menu_CheckUpdate());
		menu_CheckUpdate.setAccelerator(KeyStroke.getKeyStroke('U',
				java.awt.Event.CTRL_MASK, false));

		// 窗口监听
		addWindowListener(new Wclose());
		// 监听鼠标
		addMouseListener(new amouse());

		Container cont = getContentPane();

		game_model = new GameModel();
		game_view = new GameView(game_model);
		game_model.addObserver(game_view);
		game_view.setVisible(true);

		cont.add(game_view);

		Vector<MyPoint> data = game_model.getChessmans();
		kernel.setData(data);

		repaint();
		setLocationRelativeTo(null);
		setVisible(true);

		config_model = new ConfigModel();

		// 背景音乐播放：
		String music_state = config_model.getMusicState();
		music_player = new MusicPlayer(music_state);
	}

	/** @brief 先手事件 的监听类
	 * 
	 * 内部类，用于处理“先手”事件
	 */
	class ack_menu_first implements ActionListener {
		// 新游戏->先手 事件响应
		public void actionPerformed(ActionEvent e) {
			game_model.setColor(0);
			game_model.newGame(0);
		}
	}

	/** @brief 后手事件 的监听类
	 * 
	 * 内部类，用于处理“后手”事件
	 */
	class ack_menu_last implements ActionListener {
		// 新游戏->后手 事件响应
		public void actionPerformed(ActionEvent e) {
			game_model.setColor(1);
			game_model.newGame(1);
		}
	}
	
	/** @brief 音乐播放 的监听类
	 * 
	 * 内部类，用于处理“音效开关”的事件
	 */
	public class ack_menu_music implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if( config_model.getMusicState().equals("on") ){
				config_model.setMusic("off");
				music_player.setState("off");
			}else{
				config_model.setMusic("on");
				music_player.setState("on");
			}
		}

	}
	
	/** @brief 历史记录 的监听类
	 * 
	 * 内部类，用于处理“历史记录”事件
	 */
	public class ack_menu_hist implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			HistoryView d = new HistoryView(GameController.this, true);
			d.setLocationRelativeTo(null);
			d.setVisible(true);
		}

	}

	/** @brief 人机对战/人人对战 的监听类
	 * 
	 * 内部类，用于处理“人机对战/人人对战”事件
	 */
	class ack_menu_comp implements ItemListener {
		// 选择或取消人机对战功能 事件响应
		// 选择或取消人机对战功能 事件响应
		public void itemStateChanged(ItemEvent e) {
			Boolean computer = menu_comp.getState();
			game_model.setComputer(computer);
			game_model.newGame();
		}
	}

	/** @brief 训练模式/实战模式 的监听类
	 * 
	 * 内部类，用于处理“训练模式/实战模式”事件
	 */
	class ack_menu_prac implements ActionListener {
		// 模式选择 事件响应
		public void actionPerformed(ActionEvent e) {
			game_model.setMode( 1 - game_model.getMode() );
			if( game_model.getMode() == 1 ){
				menu_prac.setText("切换为练习模式");
			}else{
				menu_prac.setText("切换为实战模式");
			}
			game_model.newGame();
			updateStatus();
		}
	}

	/** @brief 悔棋 的监听类
	 * 
	 * 内部类，用于处理“悔棋”事件
	 */
	class ack_menu_back implements ActionListener {
		// 悔棋 事件响应
		public void actionPerformed(ActionEvent e) {
			game_model.backStep();
			updateStatus();
		}
	}

	/** @brief 退出 的监听类
	 * 
	 * 内部类，用于处理“退出”事件
	 */
	class ack_menu_exit implements ActionListener {
		// 退出 事件响应
		public void actionPerformed(ActionEvent e) {
			// TODO
			// history
			System.exit(0);
		}
	}

	/** @brief 保存 的监听类
	 * 
	 * 内部类，用于处理“保存现有棋局”事件
	 */
	class ack_menu_save implements ActionListener {
		// 保存 事件响应
		public void actionPerformed(ActionEvent e) {
			JFileChooser myFileDialog = new JFileChooser();
			myFileDialog
					.setFileFilter(new javax.swing.filechooser.FileFilter() {

						@Override
						public boolean accept(File f) {
							if (f.getName().endsWith("c6db") || f.isDirectory())
								return true;
							return false;
						}

						@Override
						public String getDescription() {
							return "文件类型(*.c6db)";
						}

					});
			int result = myFileDialog.showDialog(GameController.this, "Save");
			if (result == JFileChooser.APPROVE_OPTION) { // 确认打开
				File selected = myFileDialog.getSelectedFile();

				if (!selected.getName().endsWith("c6db")) {
					String file_name = selected.getAbsolutePath() + ".c6db";
					selected = new File(file_name);
				}

				if (selected.exists()) {
					int reply = JOptionPane.showConfirmDialog(
							GameController.this, "文件已存在，是否覆盖？", "警告",
							JOptionPane.YES_NO_OPTION); // 提示框
					if (reply == JOptionPane.CANCEL_OPTION) {
						return;
					}
				}
				try {
					FileOutputStream file = new FileOutputStream(selected);
					ObjectOutputStream output = new ObjectOutputStream(file);
					try {
						output.writeObject(game_model);
					} finally {
						output.close();
					}
				} catch (IOException ex) {
					Flogger.getLogger().log(Level.WARNING,
							"Failed to perform output when saving.", ex);
					popupMessageBox("文件打开失败", "请确保有足够权限。");
				}
			} else if (result == JFileChooser.CANCEL_OPTION) {
				System.out.println("Save File cancelled");
			} else if (result == JFileChooser.ERROR_OPTION) {
				System.err.println("Error Occured");
			}
		}
	}

	/** @brief 打开已有棋局 的监听类
	 * 
	 * 内部类，用于处理“打开已有棋局”事件
	 */
	class ack_menu_open implements ActionListener {
		// 打开 事件响应
		public void actionPerformed(ActionEvent e) {
			JFileChooser myDialog = new JFileChooser();
			myDialog.setFileFilter(new javax.swing.filechooser.FileFilter(){

				@Override
				public boolean accept(File f) {
					if (f.getName().endsWith("c6db") || f.isDirectory())
						return true;
					return false;
				}

				@Override
				public String getDescription() {
					return "文件类型(*.c6db)";
				}
				
			});
			int result = myDialog.showDialog(GameController.this, "Open");
			if( result == JFileChooser.APPROVE_OPTION ){
				File selected = myDialog.getSelectedFile();
				
				GameModel model;
				
				try {
					FileInputStream istream = new FileInputStream(selected);
	                ObjectInputStream input = new ObjectInputStream(istream);
	                model = (GameModel) input.readObject();
	                game_model.setModel(model);
	                computer_turn();
	                input.close();
					
					updateStatus();
				} catch (IOException | ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					popupMessageBox("文件打开错误", "文件格式损坏！");
				}
			}
		}
	}
	
	/** @brief 电脑的turn
	 * 
	 * 电脑走棋；
	 * 如果人机对战，并且轮到电脑走棋，则进行处理
	 * 否则，不进行任何处理
	 */
	private void computer_turn(){
		if (game_model.getComputer() && !game_model.playerTurn()) {
			System.out.println("here computer turn");

			// 电脑turn
			kernel.placeTwoStones(game_model.getCurrentColor());

			game_model.setChessMan(kernel.getData());
			// TODO
			// draw hint

			if (kernel.hasSix()) {
				// TODO
				// play lose music
				playSound(1);
				popupMessageBox("你失败了，再接再厉！！！", "游戏失败");
				game_model.newGame();
			}
			setEnabled(true);
		}
	}

	/**
	 * @brief 关于六子棋
	 * 
	 * 弹出六子棋的“关于”界面
	 */
	class ack_menu_AboutSixChess implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {

			AboutDialog d = new AboutDialog(GameController.this, true);
			d.setLocationRelativeTo(null);
			d.setVisible(true);
		}
	}

	/**
	 * @brief 查看帮助界面的监听类
	 * 
	 * 帮助界面的监听 
	 */
	class ack_menu_SeekHelp implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			HelpDialog d = new HelpDialog(GameController.this, true);
			d.setLocationRelativeTo(null);
			d.setVisible(true);
		}
	}

	/**
	 * 软件更新的监听
	 * 
	 * 软件更新的监听 
	 */
	class ack_menu_CheckUpdate implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			UpdateDialog d = new UpdateDialog(GameController.this, true);
			d.setLocationRelativeTo(null);
			d.setVisible(true);
		}
	}

	/** @brief 更新状态
	 * 
	 * 更新游戏的状态：是否允许悔棋等
	 */
	void updateStatus() {
		if( game_model.getChessmans().isEmpty() || game_model.getMode() == 1 ){
			menu_back.setEnabled( false );
		}else{
			menu_back.setEnabled( true );
		}
	}

	/** brief 弹出错误消息的提示框
	 * 
	 * @param msg 错误消息内容
	 * @param title 错误消息的标题
	 */
	void popupMessageBox(String msg, String title) {
		final JDialog myDialog = new JDialog(this, title, true);
		myDialog.setSize(180, 100);
		myDialog.setLayout(new FlowLayout(FlowLayout.CENTER, 1000, 10));
		myDialog.add(new JLabel(msg));
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

	/** @brief 窗体关闭的监听
	 * 
	 */
	class Wclose extends WindowAdapter {
		// 窗口关闭
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	}

	/** @brief 鼠标事件的监听
	 * 
	 * 用于监听鼠标事件
	 */
	class amouse extends MouseAdapter {
		// 鼠标在棋盘上点击 事件响应
		public void mouseClicked(MouseEvent e) {

			// 分析无效操作，包括非法点击，点击不可靠等
			if (!game_model.playerTurn()) {
				// TODO
				// add error music here
				playSound(-1);
				System.out.println("Not your turn");
				return;
			}

			int x = e.getX(), y = e.getY();
			System.out.println("x,y " + x + "," + y);
			x = (int) (x - getInsets().left - getContentPane().getLocation()
					.getX());
			y = (int) (y - getInsets().top - getContentPane().getLocation()
					.getY());

			int x_start = 22;
			int y_start = 22;
			int chess_size = 30;

			x = x - x_start;
			y = y - y_start;
			if ((x % 30) > 25 || (y % 30) > 25) {
				// TODO
				playSound(-1);
				return;
			}

			if ((x < 0) || (x > 18 * chess_size) || (y < 0)
					|| (y > 18 * chess_size)) {
				// TODO
				playSound(-1);
				return;
			}

			x = x / chess_size;
			y = y / chess_size;

			// 玩家turn
			int color = game_model.getCurrentColor();
			int result = game_model.getClickedAt(x, y);
			if (result < 0) {
				playSound(-1);
				return;
			}

			playSound(3);
			setEnabled(false);

			kernel.setData(game_model.getChessmans());
			if (kernel.hasSix()) {
				playSound(0);
				if (game_model.getComputer()) {
					popupMessageBox("恭喜你战胜了电脑！！！", "游戏胜利");
					game_model.newGame();
				} else {
					String[] winner = { "黑子", "白子" };
					popupMessageBox(winner[color] + "获胜！！！", "游戏结束");
					game_model.newGame();
				}
			}

			computer_turn();
			
			setEnabled(true);

			updateStatus();
		}
	}

	/** @brief 播放音乐
	 * 
	 * @param type 播放音乐的类型
	 */
	private void playSound(int type) {
		if (config_model.getMusicState().equals("on")) {
			music_player.playSound(type);
		}
	}

}
